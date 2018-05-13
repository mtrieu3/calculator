package parser;


import expression.Operator;

import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Map;


/**
 * Utility companion class for {@link Tokenizer}
 */
public class Tokens {
  private Tokens() {}

  private static Map<Character, Operator> operatorMapping = new HashMap<Character, Operator>() {{
    put('+', Operator.PLUS);
    put('-', Operator.MINUS);
    put('*', Operator.MULTIPLY);
    put('/', Operator.DIVIDE);
  }};

  static int precedenceOf(String operation) {
    switch (operation) {
      case "+": return 1;
      case "-": return 1;
      case "*" : return 2;
      case "/": return 2;
      default: return 0;
    }
  }

  static boolean isOperator(char token) {
    return operatorMapping.containsKey(token);
  }

  public static int toNumber(StringCharacterIterator itr) {
    int total = 0;
    for (char c = itr.current(); c != StringCharacterIterator.DONE; c = itr.next()) {
      if (!Character.isDigit(c)) {
        itr.previous();
        return total;
      }
      total *= 10;
      total += Character.getNumericValue(c);
    }
    return total;
  }
}
