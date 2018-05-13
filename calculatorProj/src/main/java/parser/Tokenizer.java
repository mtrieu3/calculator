package parser;

import java.text.StringCharacterIterator;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static parser.Tokens.precedenceOf;

/**
 * This class traverses the input {@link String} and creates a
 * {@link Collection<Token>}
 * The collection is then parsed into postfix form
 * The class also validates the input string
 */
public class Tokenizer {

  private static final char TERMINAL = '=';

  private final String expression;
  private Tokenizer(String expression) {
    checkValidExpression().accept(expression);
    this.expression = expression;
  }

  public static Tokenizer of(final String expression) {
    return new Tokenizer(expression);
  }

  public Queue<Token> tokenize(){
    tokenizeFunc();
    return toPosfix().apply(tokenizeFunc());
  }

  public Collection<Token> tokenizeFunc() {
      int parenCount = 0;
      List<Token> tokens = new LinkedList<>();

      if (expression.length() == 0) return tokens;

      StringCharacterIterator itr = new StringCharacterIterator(expression);

      for (char c = itr.first(); c != TERMINAL; c = itr.next()) {
        if (Tokens.isOperator(c)) {
          tokens.add(Token.operator(Character.toString(c)));
        }

        else if (c == '(') {
          tokens.add(Token.openParen());
          parenCount++;
        }

        else if (c == ')') {
          tokens.add(Token.closeParen());
          parenCount--;
        }

        else if (Character.isDigit(c)) {
          tokens.add(Token.numeric(Tokens.toNumber(itr)));
        }
      }

      if(parenCount != 0) throw new ParseError("Invalid amount of open or closed parentheses");

      return Collections.unmodifiableList(tokens);
  }


  public static Function<Collection<Token>, Queue<Token>> toPosfix() {
    return tokens -> {
      Queue<Token> postfix = new ArrayDeque<>();
      Stack<Token> operators = new Stack<>();
      Token prev = null;
      boolean negative = false;
      for (Token current : tokens) {
        switch (current.kind()) {
          case NUMERIC:
            postfix.add(current);
            prev = current;
            break;
          case CLOSE_PAREN:
            while (!operators.empty() && operators.peek().kind() != Token.Kind.OPEN_PAREN)
              postfix.add(operators.pop());
            if (!operators.empty() && operators.peek().kind() != Token.Kind.OPEN_PAREN) return null;
            else if (!operators.empty()) operators.pop();
            if(negative) {
              postfix.add(Token.numeric(-1));
              current = Token.operator("*");
            }
            prev = current;
            break;
          case OPEN_PAREN:
            operators.push(current);
            prev = current;
            break;
          case OPERATOR:
            if(current.image().equals("-") && (
                prev == null ||
                    !prev.kind().equals(Token.Kind.NUMERIC) ||
                    !prev.kind().equals(Token.Kind.CLOSE_PAREN))) {
              postfix.add(Token.numeric(-1));
              current = Token.operator("*");
              negative = true;
            }

            if (!operators.empty() && precedenceOf(current.image()) <= precedenceOf(operators.peek().image()))
              postfix.add(operators.pop());

            operators.push(current);
            prev = current;
            break;
        }
      }
      while (!operators.empty()) postfix.add(operators.pop());
      return postfix;
    };
  }

  private static Consumer<String> checkValidExpression() {
    return expression -> {
      requireNonNull(expression, "input cannot be null");
      switch (expression.charAt(0)) {
        case '+':
        case '/':
        case '*':
        case ')':
          throw new ParseError("expression cannot begin with an operator or closing paren");
        default: break;
      }

      switch (expression.charAt(expression.length() - 2)) {
        case '-':
        case '+':
        case '(':
        case '*':
        case '/':
          throw new ParseError("expression cannot end with an operator");
        default: break;
      }

      if(expression.charAt(expression.length() - 1) != TERMINAL)
        throw new ParseError("no terminal operator");
    };
  }

  public static class ParseError extends RuntimeException {
    ParseError(String msg) {
      super(msg);
    }
  }
}