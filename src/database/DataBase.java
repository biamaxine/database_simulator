package database;

import database.errors.ContractViolationException;
import database.errors.ErrorInAssignmentException;
import database.errors.RequiredParameterMissingException;
import database.errors.UnableDefineInstanceException;
import database.errors.UndefinedColumnException;
import database.errors.UniqueViolationException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class DataBase<T extends DataBaseItem> {
  private final Class<T> type;
  private final T instance;
  private final Map<UUID, T> data = new ConcurrentHashMap<>();
  private final Map<String, Map<Object, UUID>> unique_properties =
    new ConcurrentHashMap<>();
  private final Map<String, Map<Object, Set<UUID>>> indexed_properties =
    new ConcurrentHashMap<>();

  private String name;

  public DataBase(Class<T> type, Supplier<T> supplier) {
    this.type = type;
    this.name = type.getName();

    T instance;
    try { instance = supplier.get(); }
    catch (Exception e) {
      throw new UnableDefineInstanceException(
        "Unable to instantiate class '" + this.name + "' by supplier", e
      );
    }
    this.instance = instance;

    init(instance);
  }

  private void init(T instance) {
    if (instance == null)
      throw new UnableDefineInstanceException(
        "The supplier returned 'null' when trying to instantiate the '" +
        this.name + "' class."
      );

    Map<String, DataBaseProperty> uniqueProps =
      instance.getProperties(true);
    Map<String, DataBaseProperty> indexedProps =
      instance.getProperties(false);

    if (uniqueProps == null)
      throw new ContractViolationException(
        "Failed to get unique properties of instance of class '" + this.name +
        "'. The 'getProperties' method returned 'null'."
      );
    if (indexedProps == null)
      throw new ContractViolationException(
        "Failed to get indexed properties of instance of class '" + this.name +
        "'. The 'getProperties' method returned 'null'."
      );

    for (String propName : uniqueProps.keySet())
      this.unique_properties.put(propName, new ConcurrentHashMap<>());

    for (String propName : indexedProps.keySet())
      this.indexed_properties.put(propName, new ConcurrentHashMap<>());
  }

  // CREATE
  public UUID create(T item) {
    this.validateUniqueProps(item);
    UUID id = UUID.randomUUID();
    storeUniqueProps(id, item);
    storeIndexedProps(id, item);
    this.data.put(id, item);
    return id;
  }

  private void validateUniqueProps(T item) {
    Map<String, DataBaseProperty> uniqueProps =
      item.getProperties(true);
    for (Map.Entry<String, DataBaseProperty> entry : uniqueProps.entrySet()) {
      String propName = entry.getKey();
      Method getter = entry.getValue().getGetter();

      Object value;
      try { value = getter.invoke(item); }
      catch (Exception e) {
        throw new ErrorInAssignmentException(
          "Failed to invoke getter '" + getter.getName() +
          "' on instance of class '" + item.getClass().getName() + "'.", e
        );
      }

      if (this.unique_properties.get(propName).containsKey(value))
        throw new UniqueViolationException(
          "...message"
        );
    }
  }

  private void storeUniqueProps(UUID id, T item) {
    Map<String, DataBaseProperty> uniqueProps =
      item.getProperties(true);
    for (Map.Entry<String, DataBaseProperty> entry : uniqueProps.entrySet()) {
      String propName = entry.getKey();
      Method getter = entry.getValue().getGetter();

      Object value;
      try { value = getter.invoke(item); }
      catch (Exception e) {
        throw new ErrorInAssignmentException(
          "Failed to invoke getter '" + getter.getName() +
          "' on instance of class '" + item.getClass().getName() + "'.", e
        );
      }

      this.unique_properties.get(propName).put(value, id);
    }
  }

  private void storeIndexedProps(UUID id, T item) {
    Map<String, DataBaseProperty> indexedProps =
      item.getProperties(false);
    for (Map.Entry<String, DataBaseProperty> entry : indexedProps.entrySet()) {
      String propName = entry.getKey();
      Method getter = entry.getValue().getGetter();

      Object value;
      try { value = getter.invoke(item); }
      catch (Exception e) {
        throw new ErrorInAssignmentException(
          "Failed to invoke getter '" + getter.getName() +
          "' on instance of class '" + item.getClass().getName() + "'.", e
        );
      }

      this.indexed_properties
        .get(propName)
        .computeIfAbsent(value, k -> new HashSet<>())
        .add(id);
    }
  }

  // READ
  public T findUnique(UUID id) {
    if (id == null)
      throw new RequiredParameterMissingException(
        "The 'id' parameter is required to find an item in the database."
      );

    return this.data.get(id);
  }

  public T findUnique(String propName, Object value) {
    if (propName == null)
      throw new RequiredParameterMissingException(
        "The 'propName' parameter is required to find an item in the database."
      );

    if (value == null)
      throw new RequiredParameterMissingException(
        "The 'value' parameter is required to find an item in the database."
      );

    Map<Object, UUID> map = this.unique_properties.get(propName);
    if (map == null)
      throw new UndefinedColumnException(
        "The property '" + propName + "' is not defined as unique."
      );

    UUID id = map.get(value);
    return this.data.get(id);
  }

  public T[] findMany(FindManyParameter[] params) {
    if (params == null)
      throw new RequiredParameterMissingException(
        "The 'params' parameter is required to find items in the database."
      );

    if (params.length == 0)
      throw new RequiredParameterMissingException(
        "At least one parameter is required to find items in the database."
      );

    Set<UUID> result = new HashSet<>();

    for (FindManyParameter param : params) {
      String propName = param.getPropName();
      Object propValue = param.getPropValue();
      Map<Object, Set<UUID>> map = this.indexed_properties.get(propName);

      if (map == null)
        throw new UndefinedColumnException(
          "The property '" + propName +
          "' is not defined as indexed in the class '" + this.name + "'."
        );

      Set<UUID> ids;
      if (!param.isContainsValue() && !param.isInsensitiveMode())
        ids = map.get(propValue);
      else {
        ids = new HashSet<>();
        for (Map.Entry<Object, Set<UUID>> entry : map.entrySet()) {
          String propValueStr;
          String key;

          try { key = (String) entry.getKey(); }
          catch (Exception e) {
            Class<?> keyType = this.instance
              .getProperties(false)
              .get(propName)
              .getType();

            throw new ErrorInAssignmentException(
              "Properties of type '" + keyType.getName() +
              "' cannot be searched with 'containsValue' true nor 'insensitiveMode' true.",
              e
            );
          }

          try { propValueStr = (String) propValue; }
          catch (Exception e) {
            throw new ErrorInAssignmentException(
              "'FindManyProperties' with 'containsValue' true or 'insensitiveMode' true must be of type 'String'.", e
            );
          }

          if (param.isInsensitiveMode()) {
            key = key.toLowerCase();
            propValueStr = propValueStr.toLowerCase();
          }

          if (key.contains(propValueStr))
            ids.addAll(entry.getValue());
        }
      }

      if (ids != null) {
        if (result.isEmpty()) {
          result.addAll(ids);
        } else {
          result.retainAll(ids);
        }
      }
    }

    Collection<T> items = result
      .stream()
      .map(this.data::get)
      .toList();

    return DataBaseUtils.asArray(items, this.type);
  }

  // GETTERS & SETTERS
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Class<T> getType() {
    return type;
  }
}
