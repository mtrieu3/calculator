package expression;

/**
 * This is a BinaryExpression node
 * this class holds a reference to the left and right {@link Expression}
 * and the {@link Operator} that will be applied to them when evaluated
 */
public class BinaryExpression implements Expression {
  private final Operator operator;
  private final Expression left;
  private final Expression right;

  public BinaryExpression(final Expression left, Operator operator, final Expression right) {
    this.operator = operator;
    this.left = left;
    this.right = right;
  }

  @Override
  public double value() {
    switch (operator) {
      case PLUS: return left.value() + right.value();
      case MINUS: return left.value() - right.value();
      case MULTIPLY: return left.value() * right.value();
      case DIVIDE: return left.value() / right.value();
      default: throw new IllegalArgumentException("");
    }
  }

  @Override
  public String toString() {
    return String.format("(%s %s %s)", left, operator, right);
  }
}
