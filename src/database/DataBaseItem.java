package database;

import database.errors.UnableDefineTableException;
import database.errors.UniqueViolationException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Property {
  Class<?> type;
  Method getter;
  Method setter;
}

public interface DataBaseItem {
  static final Map<Class<?>, Method[]> METHOD_CACHE = new ConcurrentHashMap<>();
  static final Map<Class<?>, Map<String, DataBaseProperty>> UNIQUE_CACHE = new ConcurrentHashMap<>();
  static final Map<Class<?>, Map<String, DataBaseProperty>> INDEXED_CACHE = new ConcurrentHashMap<>();

  default Method[] getDeclaredMethods() {
    return METHOD_CACHE.computeIfAbsent(this.getClass(), Class::getDeclaredMethods);
  }

  default Map<String, DataBaseProperty> getProperties(boolean isUnique) {
    Class<?> clazz = this.getClass();
    Map<Class<?>, Map<String, DataBaseProperty>> cache =
      isUnique ? UNIQUE_CACHE : INDEXED_CACHE;

    return cache.computeIfAbsent(clazz, clz -> {
      Map<String, Property> props = new ConcurrentHashMap<>();

      for (Method method : this.getDeclaredMethods()) {
        processMethod(method, props, isUnique);
      }

      Map<String, DataBaseProperty> result = new ConcurrentHashMap<>();
      props.forEach((propName, prop) ->
        result.put(
          propName,
          new DataBaseProperty(
            propName,
            prop.type,
            prop.getter,
            prop.setter
          )
        )
      );

      return result;
    });
  }

  private void processMethod(
    Method method,
    Map<String, Property> props,
    boolean isUnique
  ) {
    if (method.isAnnotationPresent(DataBaseGetter.class))
      processGetter(method, props, isUnique);
    else if (method.isAnnotationPresent(DataBaseSetter.class))
      processSetter(method, props, isUnique);
  }

  private void processGetter(
    Method method,
    Map<String, Property> props,
    boolean isUnique
  ) {
    validateGetter(method);
    DataBaseGetter annotation = method.getAnnotation(DataBaseGetter.class);
    if (annotation.isUnique() != isUnique) return;

    Property prop = props.computeIfAbsent(
      annotation.propertyName(),
      k -> new Property()
    );

    if (prop.getter != null) throw new UniqueViolationException(
      "The method '" + prop.getter.getName() +
      "' is already defined as GETTER for property '" +
      annotation.propertyName() + "'."
    );

    validateTypeConsistency(prop, method.getReturnType(), prop.setter);
    prop.getter = method;
  }

  private void processSetter(
    Method method,
    Map<String, Property> props,
    boolean isUnique
  ) {
    validateSetter(method);
    DataBaseSetter annotation = method.getAnnotation(DataBaseSetter.class);
    if (annotation.isUnique() != isUnique) return;

    Property prop = props.computeIfAbsent(
      annotation.propertyName(),
      k -> new Property()
    );
    if (prop.setter != null) throw new UniqueViolationException(
      "The method '" + prop.setter.getName() +
      "' is already defined as SETTER for property '" +
      annotation.propertyName() + "'."
    );

    validateTypeConsistency(
      prop,
      method.getParameters()[0].getType(),
      prop.getter
    );
    prop.setter = method;
  }

  private void validateGetter(Method method) {
    if (method.getParameterCount() != 0)
      throw new UnableDefineTableException(
        "Invalid GETTER method '" + method.getName() +
        "'. GETTER methods should not receive parameters."
      );
  }

  private void validateSetter(Method method) {
    if (method.getParameterCount() != 1)
      throw new UnableDefineTableException(
        "Invalid SETTER method '" + method.getName() +
        "'. SETTER methods must only receive one parameter."
      );
  }

  private void validateTypeConsistency(
    Property prop,
    Class<?> newType,
    Method oppositeMethod
  ) {
    if (prop.type != null && prop.type != newType)
      throw new UnableDefineTableException(
        "The type of property is already set to '" + prop.type + "' by the '" +
        (oppositeMethod != null ? oppositeMethod.getName() : "undefined") +
        "' method and cannot be redefined as '" + newType + "'."
      );

    prop.type = newType;
  }
}
