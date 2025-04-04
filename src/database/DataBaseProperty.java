package database;

import java.lang.reflect.Method;

import database.errors.UnableDefinePropertyError;

public class DataBaseProperty {
  private final String name;
  private final Class<?> type;
  private final Method getter;
  private final Method setter;
  private final boolean readOnly;

  public DataBaseProperty(
    String name,
    Class<?> type,
    Method getter,
    Method setter
  ) {
    if (name == null)
      throw new UnableDefinePropertyError(
        "A 'DataBaseProperty' must have a 'name' defined."
      );

    if (type == null)
      throw new UnableDefinePropertyError(
        "A 'DataBaseProperty' must have a 'type' defined"
      );

    if (getter == null)
      throw new UnableDefinePropertyError(
        "A 'DataBaseProperty' must have a GETTER method to ensure the data can be retrieved."
      );

    this.name = name;
    this.type = type;
    this.getter = getter;
    this.setter = setter;
    this.readOnly = setter == null;
  }

  public String getName() {
    return name;
  }

  public Class<?> getType() {
    return type;
  }

  public Method getGetter() {
    return getter;
  }

  public Method getSetter() {
    return setter;
  }

  public boolean isReadOnly() {
    return readOnly;
  }
}
