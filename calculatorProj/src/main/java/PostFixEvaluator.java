
import expression.*;
import parser.Token;

import java.util.*;

/**
 * This class will traverse the queue of {@link Token},
 * build a syntax tree, and evaluate the expression.
 *
 * The Queue of {@link Token} is already in postfix form
 */
public class PostFixEvaluator {

  private Stack<Expression> expressions;
  private final Queue<Token> tokens;

  public PostFixEvaluator(final Queue<Token> tokens) {
    expressions = new Stack<>();
    this.tokens = tokens;
  }

  private Stack<Expression> buildTree() {
    for (Token token : tokens) {
      switch (token.kind()) {
        case NUMERIC:
          expressions.push(new SimpleOperand(Double.parseDouble(token.image())));
          break;
        case OPERATOR:
          expressions.push(new BinaryExpression(expressions.pop(), Operator.from(token.image()), expressions.pop()));
          break;
        default: break;
      }
    }
    return expressions;
  }

  public double evaluate() {
    buildTree();
    return expressions.peek().value();
  }
}
