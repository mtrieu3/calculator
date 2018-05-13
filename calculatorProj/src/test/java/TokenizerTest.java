import org.hamcrest.Matchers;
import org.junit.Test;
import parser.Token;
import parser.Tokenizer;
import parser.Tokens;

import java.text.StringCharacterIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import static org.hamcrest.MatcherAssert.assertThat;

public class TokenizerTest {
  @Test
  public void tokenizerTest() {
    Collection<Token> testData = new LinkedList<>();
    testData.add(Token.numeric(45));
    testData.add(Token.operator("+"));
    testData.add(Token.numeric(3));

    Collection<Token> tester = Tokenizer.of("45+3=").tokenizeFunc();

    Iterator<Token> testDataIter = testData.iterator();
    Iterator<Token> testerIter = tester.iterator();

    while (testDataIter.hasNext() && testerIter.hasNext()) {
      assertThat(testDataIter.next().equals(testerIter.next()), Matchers.equalTo(true));
    }
  }

  @Test
  public void testLastElementInputTest() {
    try {
      Tokenizer.of("5+4-=").tokenizeFunc();
    } catch (Exception e) {
      assert e instanceof Tokenizer.ParseError;
    }
  }

  @Test
  public void firstElementInputTest() {
    try {
      Tokenizer.of("+5+4=").tokenizeFunc();
    } catch (Exception e) {
      assert e instanceof Tokenizer.ParseError;
    }

    try {
      Tokenizer.of(")234+=").tokenizeFunc();
    } catch (Exception e) {
      assert e instanceof Tokenizer.ParseError;
    }
  }

  @Test
  public void validParenthesesTest() {
    try {
      Tokenizer.of("(((4+)=").tokenize();
    } catch (Exception e) {
      assert e instanceof Tokenizer.ParseError;
    }
  }

  @Test
  public void toNumberTest() {
    assertThat(Tokens.toNumber(new StringCharacterIterator("545")), Matchers.equalTo(545));
  }

  @Test
  public void toPostFixTest() {
    Collection<Token> tokens = Tokenizer.of("(5+4)+(3*7)=").tokenizeFunc();
    Queue<Token> postFix = Tokenizer.toPosfix().apply(tokens);
    String expected = "54+37*+";
    StringBuilder actual = new StringBuilder();
    postFix.forEach(token -> actual.append(token.image()));
    assertThat(actual.toString(), Matchers.equalTo(expected));
  }
}
