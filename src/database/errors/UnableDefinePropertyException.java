package database.errors;

public class UnableDefinePropertyException extends DataBaseException {
  public UnableDefinePropertyException() {
    super(DataBaseExceptionCode.UNABLE_DEFINE_PROPERTY);
  }

  public UnableDefinePropertyException(String message) {
    super(DataBaseExceptionCode.UNABLE_DEFINE_PROPERTY, message);
  }

  public UnableDefinePropertyException(String message, Exception e) {
    super(DataBaseExceptionCode.UNABLE_DEFINE_PROPERTY, message, e);
  }
}
