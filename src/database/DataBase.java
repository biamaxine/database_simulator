package database;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import database.errors.UnableDefineCollectionError;

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
      throw new UnableDefineCollectionError(
        "Unable to instantiate class '" + this.name + "' by supplier", e
      );
    }

    if (instance == null)
      throw new UnableDefineCollectionError(
        "The supplier returned 'null' when trying to instantiate the '" +
        this.name + "' class."
      );

    Map<String, DataBaseProperty> uniqueProps =
      instance.getProperties(true);
    Map<String, DataBaseProperty> indexedProps =
      instance.getProperties(false);

    if (uniqueProps == null)
      throw new UnableDefineCollectionError(
        "Failed to get unique properties of instance of class '" + this.name +
        "'. The 'getProperties' method returned 'null'."
      );
    if (indexedProps == null)
      throw new UnableDefineCollectionError(
        "Failed to get indexed properties of instance of class '" + this.name +
        "'. The 'getProperties' method returned 'null'."
      );

    for (String propName : uniqueProps.keySet())
      this.unique_properties.put(propName, new ConcurrentHashMap<>());

    for (String propName : indexedProps.keySet())
      this.indexed_properties.put(propName, new ConcurrentHashMap<>());
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
