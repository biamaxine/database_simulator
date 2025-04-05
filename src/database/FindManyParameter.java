package database;

import database.errors.RequiredParameterMissingException;

public class FindManyParameter {
  private final String propName;
  private final Object propValue;
  private final boolean containsValue;
  private final boolean insensitiveMode;

  public FindManyParameter(String propName, Object propValue) {
    if (propName == null)
      throw new RequiredParameterMissingException(
        "The 'propName' parameter is required to find an item in the database."
      );

    if (propValue == null)
      throw new RequiredParameterMissingException(
        "The 'value' parameter is required to find an item in the database."
      );

    this.propName = propName;
    this.propValue = propValue;
    this.containsValue = false;
    this.insensitiveMode = false;
  }

  public FindManyParameter(
    String propName,
    Object propValue,
    boolean containsValue
  ) {
    if (propName == null)
      throw new RequiredParameterMissingException(
        "The 'propName' parameter is required to find an item in the database."
      );

    if (propValue == null)
      throw new RequiredParameterMissingException(
        "The 'propValue' parameter is required to find an item in the database."
      );

    this.propName = propName;
    this.propValue = propValue;
    this.containsValue = containsValue;
    this.insensitiveMode = false;
  }

  public FindManyParameter(
    String propName,
    Object propValue,
    boolean containsValue,
    boolean insensitiveMode
  ) {
    if (propName == null)
      throw new RequiredParameterMissingException(
        "The 'propName' parameter is required to find an item in the database."
      );

    if (propValue == null)
      throw new RequiredParameterMissingException(
        "The 'propValue' parameter is required to find an item in the database."
      );

    this.propName = propName;
    this.propValue = propValue;
    this.containsValue = containsValue;
    this.insensitiveMode = insensitiveMode;
  }

  public String getPropName() {
    return propName;
  }

  public Object getPropValue() {
    return propValue;
  }

  public boolean isContainsValue() {
    return containsValue;
  }

  public boolean isInsensitiveMode() {
    return insensitiveMode;
  }
}
