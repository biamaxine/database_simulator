package database.errors;

public class UnableDefineDocumentError extends DataBaseError {
  public UnableDefineDocumentError() {
    super(DataBaseErrorCode.UNABLE_DEFINE_DOCUMENT);
  }

  public UnableDefineDocumentError(String message) {
    super(DataBaseErrorCode.UNABLE_DEFINE_DOCUMENT, message);
  }

  public UnableDefineDocumentError(String message, Exception e) {
    super(DataBaseErrorCode.UNABLE_DEFINE_DOCUMENT, message, e);
  }
}
