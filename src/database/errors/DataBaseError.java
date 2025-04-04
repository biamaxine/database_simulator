package database.errors;

public class DataBaseError extends RuntimeException {
  private DataBaseErrorCode errorCode;

  public DataBaseError(DataBaseErrorCode errorCode) {
    super(errorCode.toString());
    this.errorCode = errorCode;
  }

  public DataBaseError(DataBaseErrorCode errorCode, String message) {
    super(errorCode.toString() + (message == null ? "" : ": " + message));
    this.errorCode = errorCode;
  }

  public DataBaseError(
    DataBaseErrorCode errorCode,
    String message,
    Exception e
  ) {
    super(errorCode.toString() + (message == null ? "" : ": " + message), e);
    this.errorCode = errorCode;
  }

  public DataBaseErrorCode getErrorCode() {
    return errorCode;
  }

  @Override
  public String toString() {
    return errorCode.toString() + (
      this.getMessage().isEmpty() ? "" : ": " + this.getMessage()
    );
  }
}
