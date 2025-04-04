package database.errors;

public class UniqueViolationException extends DataBaseException {
  public UniqueViolationException() {
    super(DataBaseExceptionCode.UNIQUE_VIOLATION);
  }

  public UniqueViolationException(String message) {
    super(DataBaseExceptionCode.UNIQUE_VIOLATION, message);
  }

  public UniqueViolationException(String message, Exception e) {
    super(DataBaseExceptionCode.UNIQUE_VIOLATION, message, e);
  }
}
