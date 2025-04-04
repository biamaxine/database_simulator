package database.errors;

public class UnableDefineCollectionException extends DataBaseException {
  public UnableDefineCollectionException() {
    super(DataBaseExceptionCode.UNABLE_DEFINE_COLLECTION);
  }

  public UnableDefineCollectionException(String message) {
    super(DataBaseExceptionCode.UNABLE_DEFINE_COLLECTION, message);
  }

  public UnableDefineCollectionException(String message, Exception e) {
    super(DataBaseExceptionCode.UNABLE_DEFINE_COLLECTION, message, e);
  }
}
