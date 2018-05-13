package expression;

/**
 * This class represent all operations supported by the calculator
 */
public enum Operator {
  PLUS,
  MINUS,
  MULTIPLY,
  DIVIDE;

  public static Operator from(String image) {
    switch (image) {
      case "+" : return PLUS;
      case "-" : return MINUS;
      case "*" : return MULTIPLY;
      case "/" : return DIVIDE;
      default: throw new IllegalArgumentException("not an operator");
    }
  }
}
