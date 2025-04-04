package database.errors;

public class UnableDefineCollectionError extends DataBaseError {
  public UnableDefineCollectionError() {
    super(DataBaseErrorCode.UNABLE_DEFINE_COLLECTION);
  }

  public UnableDefineCollectionError(String message) {
    super(DataBaseErrorCode.UNABLE_DEFINE_COLLECTION, message);
  }

  public UnableDefineCollectionError(String message, Exception e) {
    super(DataBaseErrorCode.UNABLE_DEFINE_COLLECTION, message, e);
  }
}
