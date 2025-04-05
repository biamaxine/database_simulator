package database.errors;

public class RequiredParameterMissingException extends DataBaseException {
  public RequiredParameterMissingException() {
    super(DataBaseExceptionCode.REQUIRED_PARAMETER_MISSING);
  }

  public RequiredParameterMissingException(String message) {
    super(DataBaseExceptionCode.REQUIRED_PARAMETER_MISSING, message);
  }

  public RequiredParameterMissingException(String message, Exception e) {
    super(DataBaseExceptionCode.REQUIRED_PARAMETER_MISSING, message, e);
  }
}
