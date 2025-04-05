package database.errors;

public class UnableCreateItemException extends DataBaseException {
  public UnableCreateItemException() {
    super(DataBaseExceptionCode.UNABLE_CREATE_ITEM);
  }

  public UnableCreateItemException(String message) {
    super(DataBaseExceptionCode.UNABLE_CREATE_ITEM, message);
  }

  public UnableCreateItemException(String message, Exception e) {
    super(DataBaseExceptionCode.UNABLE_CREATE_ITEM, message, e);
  }
}
