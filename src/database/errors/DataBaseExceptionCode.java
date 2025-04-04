package database.errors;

import java.util.HashMap;
import java.util.Map;

public enum DataBaseExceptionCode {
  // Classe 08 - Conexão
  CONNECTION_FAILURE("08.001", "Connection Failure"),

  // Classe 22 - Exceções de dados
  ERROR_IN_ASSIGNMENT("22.005", "Error In Assignment"),
  DIVISION_BY_ZERO("22.012", "Division By Zero"),

  // Classe 23 - Restrições de integridade
  UNIQUE_VIOLATION("23.000", "Unique Violation"),
  FOREIGN_KEY_VIOLATION("23.001", "Foreign Key Violation"),

  // Classe 42 - Sintaxe SQL
  SYNTAX_ERROR("42.000", "Syntax Error"),

  // Class XX - Personalizados
  UNABLE_DEFINE_PROPERTY("XX.001", "Unable Define Property"),
  UNABLE_DEFINE_DOCUMENT("XX.002", "Unable Define Document"),
  UNABLE_DEFINE_COLLECTION("XX.003", "Unable Define Collection"),
  UNKNOWN_ERROR("XX.999", "Unknown Error");

  private final String code;
  private final String name;

  private static final Map<String, DataBaseExceptionCode> LOOKUP_MAP = new HashMap<>();

  static {
    for (DataBaseExceptionCode dbError : values()) {
      LOOKUP_MAP.put(dbError.code, dbError);
    }
  }

  private DataBaseExceptionCode(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public static DataBaseExceptionCode fromCode(String code) {
    return LOOKUP_MAP.getOrDefault(code, UNKNOWN_ERROR);
  }

  @Override
  public String toString() {
    return "ERROR " + code + " [" + name + "]";
  }
}
