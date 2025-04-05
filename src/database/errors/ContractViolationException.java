package database.errors;

public class ContractViolationException extends DataBaseException {
  public ContractViolationException() {
    super(DataBaseExceptionCode.CONTRACT_VIOLATION);
  }

  public ContractViolationException(String message) {
    super(DataBaseExceptionCode.CONTRACT_VIOLATION, message);
  }

  public ContractViolationException(String message, Exception e) {
    super(DataBaseExceptionCode.CONTRACT_VIOLATION, message, e);
  }
}
