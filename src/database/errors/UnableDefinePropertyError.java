package database.errors;

public class UnableDefinePropertyError extends DataBaseError {
  public UnableDefinePropertyError() {
    super(DataBaseErrorCode.UNABLE_DEFINE_PROPERTY);
  }

  public UnableDefinePropertyError(String message) {
    super(DataBaseErrorCode.UNABLE_DEFINE_PROPERTY, message);
  }

  public UnableDefinePropertyError(String message, Exception e) {
    super(DataBaseErrorCode.UNABLE_DEFINE_PROPERTY, message, e);
  }
}
