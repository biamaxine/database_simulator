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
  UNDEFINED_COLUMN("42.703", "Undefined Column"),

  // Class XX - Personalizados
    // DataBase Definitions
    UNABLE_DEFINE_TABLE("DB.000", "Unable Define Table"),
    UNABLE_DEFINE_PROPERTY("DB.001", "Unable Define Property"),

    // SYS Definitios
    UNABLE_DEFINE_INSTANCE("SYS.001", "Unable Create Instance"),

    // SYS Operations
    REQUIRED_PARAMETER_MISSING("SYS.100", "Required Parameter Missing"),
    CONTRACT_VIOLATION("SYS.101", "Contract Violation"),

    // CRUD Operations
      // 000
    UNABLE_CREATE_ITEM("CRUD.000", "Unable Create Item"),
    UNABLE_CREATE_MANY_ITEMS("CRUD.001", "Unable Create Many Items"),
      // 010
    UNABLE_READ_FULL_ITEMS("CRUD.010", "Unable Read Full Items"),
    UNABLE_READ_UNIQUE_ITEM("CRUD.011", "Unable Read Unique Item"),
    UNABLE_READ_INDEXED_ITEMS("CRUD.012", "Unable Read Indexed Items"),
      // 020
    UNABLE_UPDATE_ITEM("CRUD.020", "Unable Update Item"),
    UNABLE_UPDATE_MANY_ITEMS("CRUD.021", "Unable Update Many Items"),
      // 030
    UNABLE_DELETE_ITEM("CRUD.030", "Unable Delete Item"),
    UNABLE_DELETE_MANY_ITEMS("CRUD.031", "Unable Delete Many Items"),

    // 999: Unknown
    UNKNOWN_ERROR("XX.999", "Unknown Error");

  private final String code;
  private final String label;

  private static final Map<String, DataBaseExceptionCode> LOOKUP_MAP = new HashMap<>();

  static {
    for (DataBaseExceptionCode dbError : values()) {
      LOOKUP_MAP.put(dbError.code, dbError);
    }
  }

  private DataBaseExceptionCode(String code, String label) {
    this.code = code;
    this.label = label;
  }

  public String getCode() {
    return code;
  }

  public String getLabel() {
    return label;
  }

  public static DataBaseExceptionCode fromCode(String code) {
    return LOOKUP_MAP.getOrDefault(code, UNKNOWN_ERROR);
  }

  @Override
  public String toString() {
    return "ERROR " + code + " [" + label + "]";
  }
}
