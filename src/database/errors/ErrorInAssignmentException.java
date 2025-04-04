package database.errors;

public class ErrorInAssignmentException extends DataBaseException {
  public ErrorInAssignmentException() {
    super(DataBaseExceptionCode.ERROR_IN_ASSIGNMENT);
  }

  public ErrorInAssignmentException(String message) {
    super(DataBaseExceptionCode.ERROR_IN_ASSIGNMENT, message);
  }

  public ErrorInAssignmentException(String message, Exception e) {
    super(DataBaseExceptionCode.ERROR_IN_ASSIGNMENT, message, e);
  }
}
