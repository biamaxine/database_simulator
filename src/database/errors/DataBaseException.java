package database.errors;

public class DataBaseException extends RuntimeException {
  private DataBaseExceptionCode errorCode;

  public DataBaseException(DataBaseExceptionCode errorCode) {
    super(errorCode.toString());
    this.errorCode = errorCode;
  }

  public DataBaseException(DataBaseExceptionCode errorCode, String message) {
    super(errorCode.toString() + (message == null ? "" : ": " + message));
    this.errorCode = errorCode;
  }

  public DataBaseException(
    DataBaseExceptionCode errorCode,
    String message,
    Exception e
  ) {
    super(errorCode.toString() + (message == null ? "" : ": " + message), e);
    this.errorCode = errorCode;
  }

  public DataBaseExceptionCode getErrorCode() {
    return errorCode;
  }

  @Override
  public String toString() {
    return errorCode.toString() + (
      this.getMessage().isEmpty() ? "" : ": " + this.getMessage()
    );
  }
}
