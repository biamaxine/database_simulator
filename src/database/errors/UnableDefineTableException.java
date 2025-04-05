package database.errors;

public class UnableDefineTableException extends DataBaseException {
  public UnableDefineTableException() {
    super(DataBaseExceptionCode.UNABLE_DEFINE_TABLE);
  }

  public UnableDefineTableException(String message) {
    super(DataBaseExceptionCode.UNABLE_DEFINE_TABLE, message);
  }

  public UnableDefineTableException(String message, Exception e) {
    super(DataBaseExceptionCode.UNABLE_DEFINE_TABLE, message, e);
  }
}
