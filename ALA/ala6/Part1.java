import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Part 1: evaluating postfix expressions with java.util.Stack
 */
class Part1 {

    /**
     * Repeatedly prompts the user for postfix expressions and evaluates them.
     */
    public static void run(Scanner input) {
        System.out.println("Evaluating postfix expressions");
        System.out.println("-------------------------------------------------------------");

        String again;
        do {
            System.out.println("Enter a postfix expression:");
            String line = input.nextLine().trim();

            try {
                int result = evaluate(line);
                System.out.println("Result = " + result);
            } catch (IllegalArgumentException e) {
                System.out.println("Malformed expression.");
            } catch (ArithmeticException e) {
                System.out.println("Error: division by zero.");
            }

            System.out.println("Do you want to evaluate another postfix expression? (yes/no):");
            again = input.nextLine().trim().toLowerCase();
        } while (again.equals("yes") || again.equals("y"));
    }

    /**
     * Evaluates a postfix expression whose tokens are separated by spaces.
     * @throws IllegalArgumentException if the expression is malformed
     * @throws ArithmeticException on division by zero
     */
    public static int evaluate(String expression) {
        Stack<Integer> postfixStack = new Stack<>();
        String[] tokens = expression.trim().split("\\s+");

        try {
            for (String token : tokens) {
                if (isOperator(token)) {
                    // the first value popped is the RIGHT operand
                    int right = postfixStack.pop();
                    int left = postfixStack.pop();
                    postfixStack.push(apply(token, left, right));
                } else {
                    postfixStack.push(Integer.parseInt(token));
                }
            }

            int result = postfixStack.pop();
            if (!postfixStack.isEmpty()) {
                throw new IllegalArgumentException("Too many operands");
            }
            return result;
        } catch (EmptyStackException e) {
            throw new IllegalArgumentException("Too many operators");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-")
            || token.equals("*") || token.equals("/");
    }

    private static int apply(String op, int left, int right) {
        switch (op) {
            case "+": return left + right;
            case "-": return left - right;
            case "*": return left * right;
            case "/": return left / right;   // throws ArithmeticException if right == 0
            default:  throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }
}
