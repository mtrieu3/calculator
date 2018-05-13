package expression;

/**
 * This is node holds the value of the most basic form
 * of {@link Expression}, the value of the individual numbers
 */
public class SimpleOperand implements Expression {
  private double operand;

  public SimpleOperand(double operand) {
    this.operand = operand;
  }

  @Override
  public double value() {
    return operand;
  }

  @Override
  public String toString() {
    return Double.toString(operand);
  }
}
