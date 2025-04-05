package database;

import java.lang.reflect.Array;
import java.util.Collection;

public final class DataBaseUtils {
  public static <T> T[] asArray(Collection<T> collection, Class<T> type) {
    @SuppressWarnings("unchecked")
    T[] array = (T[]) Array.newInstance(type, collection.size());
    return collection.toArray(array);
  }
}
