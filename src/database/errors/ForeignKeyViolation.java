package database.errors;

public class ForeignKeyViolation extends DataBaseError {
  public ForeignKeyViolation() {
    super(DataBaseErrorCode.FOREIGN_KEY_VIOLATION);
  }

  public ForeignKeyViolation(String message) {
    super(DataBaseErrorCode.FOREIGN_KEY_VIOLATION, message);
  }

  public ForeignKeyViolation(String message, Exception e) {
    super(DataBaseErrorCode.FOREIGN_KEY_VIOLATION, message, e);
  }
}
