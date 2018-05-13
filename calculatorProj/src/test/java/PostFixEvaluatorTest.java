import org.hamcrest.Matchers;
import org.junit.Test;
import parser.*;

import static org.hamcrest.MatcherAssert.assertThat;

public class PostFixEvaluatorTest {

  @Test
  public void simpleEvaluatorTest() {
    PostFixEvaluator evaluator = new PostFixEvaluator(Tokenizer.of("5+3*2=").tokenize());
    assertThat(evaluator.evaluate(), Matchers.equalTo(11.0));
  }

  @Test
  public void complexEvaluatorTest() {
    PostFixEvaluator evaluator = new PostFixEvaluator(Tokenizer.of("-(3*10)+10=").tokenize());
    assertThat(evaluator.evaluate(), Matchers.equalTo(-20.0));
  }
}
