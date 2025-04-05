package database.errors;

public class UndefinedColumnException extends DataBaseException {
  public UndefinedColumnException() {
    super(DataBaseExceptionCode.UNDEFINED_COLUMN);
  }

  public UndefinedColumnException(String message) {
    super(DataBaseExceptionCode.UNDEFINED_COLUMN, message);
  }

  public UndefinedColumnException(String message, Exception e) {
    super(DataBaseExceptionCode.UNDEFINED_COLUMN, message, e);
  }
}
