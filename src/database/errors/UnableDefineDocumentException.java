package database.errors;

public class UnableDefineDocumentException extends DataBaseException {
  public UnableDefineDocumentException() {
    super(DataBaseExceptionCode.UNABLE_DEFINE_DOCUMENT);
  }

  public UnableDefineDocumentException(String message) {
    super(DataBaseExceptionCode.UNABLE_DEFINE_DOCUMENT, message);
  }

  public UnableDefineDocumentException(String message, Exception e) {
    super(DataBaseExceptionCode.UNABLE_DEFINE_DOCUMENT, message, e);
  }
}
