package parser;

/**
 * This is a Token node.
 * Each token as an enum Kind(or type) and {@link String}image
 * The builder functions construct the different kinds of
 * tokens currently supported by the {@link Tokenizer}
 */
public class Token {
  public enum Kind {
    OPEN_PAREN,
    CLOSE_PAREN,
    NUMERIC,
    OPERATOR,
  }

  private final Kind tokenType;
  private final String image;

  private Token(Kind tokenType, String image) {
    this.tokenType = tokenType;
    this.image = image;
  }

  public Kind kind(){
    return tokenType;
  }
  public String image() {
    return image;
  }

  public boolean equals(Token token) {
    return tokenType == token.kind() && image.equals(token.image());
  }

  public static Token openParen() {
    return new Token(Kind.OPEN_PAREN, "(");
  }

  public static Token closeParen() {
    return new Token(Kind.CLOSE_PAREN, ")");
  }

  public static Token numeric(int number){
    return new Token(Kind.NUMERIC, Integer.toString(number));
  }

  public static Token operator(String operator) {
    return new Token(Kind.OPERATOR, operator);
  }
}
