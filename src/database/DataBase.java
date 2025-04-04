package database;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import database.errors.ErrorInAssignmentException;
import database.errors.UnableDefineCollectionException;
import database.errors.UniqueViolationException;

public class DataBase<T extends DataBaseItem> {
  private final Class<T> type;
  private final Map<UUID, T> data = new ConcurrentHashMap<>();
  private final Map<String, Map<Object, UUID>> unique_properties =
    new ConcurrentHashMap<>();
  private final Map<String, Map<Object, Set<UUID>>> indexed_properties =
    new ConcurrentHashMap<>();

  private String name;

  public DataBase(Class<T> type, Supplier<T> supplier) {
    this.type = type;
    this.name = type.getName();
    init(supplier);
  }

  private void init(Supplier<T> supplier) {
    T instance;
    try { instance = supplier.get(); }
    catch (Exception e) {
      throw new UnableDefineCollectionException(
        "Unable to instantiate class '" + this.name + "' by supplier", e
      );
    }

    if (instance == null)
      throw new UnableDefineCollectionException(
        "The supplier returned 'null' when trying to instantiate the '" +
        this.name + "' class."
      );

    Map<String, DataBaseProperty> uniqueProps =
      instance.getProperties(true);
    Map<String, DataBaseProperty> indexedProps =
      instance.getProperties(false);

    if (uniqueProps == null)
      throw new UnableDefineCollectionException(
        "Failed to get unique properties of instance of class '" + this.name +
        "'. The 'getProperties' method returned 'null'."
      );
    if (indexedProps == null)
      throw new UnableDefineCollectionException(
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
