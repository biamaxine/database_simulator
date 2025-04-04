package database.errors;

public class UniqueViolationError extends DataBaseError {
  public UniqueViolationError() {
    super(DataBaseErrorCode.UNIQUE_VIOLATION);
  }

  public UniqueViolationError(String message) {
    super(DataBaseErrorCode.UNIQUE_VIOLATION, message);
  }

  public UniqueViolationError(String message, Exception e) {
    super(DataBaseErrorCode.UNIQUE_VIOLATION, message, e);
  }
}
