package database.errors;

public class UnableDefineInstanceException extends DataBaseException {
  public UnableDefineInstanceException() {
    super(DataBaseExceptionCode.UNABLE_DEFINE_INSTANCE);
  }

  public UnableDefineInstanceException(String message) {
    super(DataBaseExceptionCode.UNABLE_DEFINE_INSTANCE, message);
  }

  public UnableDefineInstanceException(String message, Exception e) {
    super(DataBaseExceptionCode.UNABLE_DEFINE_INSTANCE, message, e);
  }
}
