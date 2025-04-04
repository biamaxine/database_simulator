package database.errors;

import java.util.HashMap;
import java.util.Map;

public enum DataBaseErrorCode {
  // Classe 08 - Conexão
  CONNECTION_FAILURE("08.001", "Connection Failure"),

  // Classe 22 - Exceções de dados
  DIVISION_BY_ZERO("22.012", "Division By Zero"),

  // Classe 23 - Restrições de integridade
  UNIQUE_VIOLATION("23.000", "Unique Violation"),
  FOREIGN_KEY_VIOLATION("23.001", "Foreign Key Violation"),

  // Classe 42 - Sintaxe SQL
  SYNTAX_ERROR("42.000", "Syntax Error"),

  // Class XX - Personalizados
  UNABLE_DEFINE_PROPERTY("XX.001", "Unable Define Property"),
  UNKNOWN_ERROR("XX.999", "Unknown Error");

  private final String code;
  private final String name;

  private static final Map<String, DataBaseErrorCode> LOOKUP_MAP = new HashMap<>();

  static {
    for (DataBaseErrorCode dbError : values()) {
      LOOKUP_MAP.put(dbError.code, dbError);
    }
  }

  private DataBaseErrorCode(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public static DataBaseErrorCode fromCode(String code) {
    return LOOKUP_MAP.getOrDefault(code, UNKNOWN_ERROR);
  }

  @Override
  public String toString() {
    return "ERROR " + code + " [" + name + "]";
  }
}
