package ru.edu.calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class ReversePolishNotation {
    private final List<Token> expression;

    private ReversePolishNotation(List<Token> expression) {
        this.expression = expression;
    }

    public static ReversePolishNotation fromInfix(String infixExpression) {
        return fromInfix(Tokenizer.tokenize(infixExpression));
    }

    public static ReversePolishNotation fromInfix(List<Token> tokenizedInfix) {
        List<Token> output = new LinkedList<>();
        Deque<Token> stack = new ArrayDeque<>();

        for (var token : tokenizedInfix) {
            if (token instanceof BinaryOperator) {
                while (!stack.isEmpty() && stack.peek() instanceof BinaryOperator
                        && ((BinaryOperator) token).compareTo(((BinaryOperator) stack.peek())) < 0
                ) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else if (token instanceof OpenBracket) {
                stack.push(token);
            } else if (token instanceof CloseBracket) {
                while (!stack.isEmpty() && !(stack.peek() instanceof OpenBracket)) {
                    output.add(stack.pop());
                }
                stack.pop();
            } else {
                output.add(token);
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }

        return new ReversePolishNotation(output);
    }

    public Number evaluate() {
        var stack = new ArrayDeque<Token>();
        for (var token : expression) {
            if (token instanceof BinaryOperator) {
                var arg2 = stack.pop();
                var arg1 = stack.pop();
                stack.push(((BinaryOperator) token).evaluate((Number) arg1, (Number) arg2));
            } else {
                stack.push(token);
            }
        }
        return (Number) stack.pop();
    }
}


