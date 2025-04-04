package database.errors;

public class UnableDefineItemError extends DataBaseError {
  public UnableDefineItemError() {
    super(DataBaseErrorCode.UNABLE_DEFINE_ITEM);
  }

  public UnableDefineItemError(String message) {
    super(DataBaseErrorCode.UNABLE_DEFINE_ITEM, message);
  }

  public UnableDefineItemError(String message, Exception e) {
    super(DataBaseErrorCode.UNABLE_DEFINE_ITEM, message, e);
  }
}
