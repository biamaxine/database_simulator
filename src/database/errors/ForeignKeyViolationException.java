package database.errors;

public class ForeignKeyViolationException extends DataBaseException {
  public ForeignKeyViolationException() {
    super(DataBaseExceptionCode.FOREIGN_KEY_VIOLATION);
  }

  public ForeignKeyViolationException(String message) {
    super(DataBaseExceptionCode.FOREIGN_KEY_VIOLATION, message);
  }

  public ForeignKeyViolationException(String message, Exception e) {
    super(DataBaseExceptionCode.FOREIGN_KEY_VIOLATION, message, e);
  }
}
