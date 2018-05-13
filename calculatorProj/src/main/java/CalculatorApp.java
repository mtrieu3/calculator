import parser.Tokenizer;

import java.util.Scanner;

public class CalculatorApp {
  public static void main(String[] args) {
    System.out.println(
        "This is an expression calculator:\n\n" +
            "This calculator can handle the following operations: " +
            "\n|\t()\t|\t+\t|\t-\t|\t*\t|\t/\t|\n" +
            "Terminate expression with |\t=\t|" +
            "Terminate program with |\tQ\t|");

    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter expression: ");
    while (scanner.hasNext()){
      String expression = scanner.next();
      if (expression.equalsIgnoreCase("Q")) break;
      try {
        System.out.println(new PostFixEvaluator(Tokenizer.of(expression).tokenize()).evaluate());
        System.out.println("another expression? (Q to quit)");
      } catch (Tokenizer.ParseError parseError) {
        System.out.println("Invalid expression, try again");
      }
    }

    System.out.println("\nThanks for using the calculator!");
  }
}
