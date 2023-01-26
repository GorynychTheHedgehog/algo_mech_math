package ru.edu.calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReversePolishNotationTest {

    private static final double DELTA = 1e-10;

    @ParameterizedTest
    @MethodSource("provideInfixExpression")
    void RPN_test(String infixExpression, double answer) {
        //prepare
        var rpn = ReversePolishNotation.fromInfix(infixExpression);

        //act
        var result = rpn.evaluate().getValue();

        //assert
        System.out.println(result);
        assertTrue(Math.abs(answer - result) <= DELTA);
    }

    private static Stream<Arguments> provideInfixExpression() {
        return Stream.of(
            Arguments.of("6.63 + 56.62 + 16.8 - 60.53 - 3.61 - 14.91", 1),
            Arguments.of("6.63 + (56.62 + 16.8) - (60.53 + 3.61 + 14.91)", 1),
            Arguments.of("(6.63 + (56.62 + 16.8)) - (60.53 + 3.61) - 1 * 14.91", 1),
            Arguments.of("((6.63 + (56.62 + 16.8)) - (60.53 + 3.61) - 1 * 14.91)", 1),
            Arguments.of("((6.63 + (56.62 + 16.8)) - (60.53 + 3.61) ^ 1 - 1 * 14.91) ^ 2 ^ 0.5", 1),
            Arguments.arguments("5 + 2 / (3- 8) ^ 5 ^ 2 ", 5)
        );
    }

}
