package ru.edu.calculator;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    public static List<Token> tokenize(String expression) {
        var tokens = new ArrayList<Token>();
        StringBuilder tmpNumber = new StringBuilder();

        for (var c : expression.toCharArray()) {

            if (Character.isDigit(c) || c == '.') {
                tmpNumber.append(c);
                continue;
            } else if (tmpNumber.length() > 0){
                var number = new Number(Double.parseDouble(tmpNumber.toString()));
                tmpNumber = new StringBuilder();
                tokens.add(number);
            }

            switch (c) {
                case '(' -> tokens.add(new OpenBracket());
                case ')' -> tokens.add(new CloseBracket());
                case '+' -> tokens.add(new BinaryPlus());
                case '-' -> tokens.add(new BinaryMinus());
                case '*' -> tokens.add(new BinaryMultiplication());
                case '/' -> tokens.add(new BinaryDivision());
                case '^' -> tokens.add(new BinaryPower());
            }
        }

        if (!tmpNumber.isEmpty()) {
            var number = new Number(Double.parseDouble(tmpNumber.toString()));
            tokens.add(number);
        }

        return tokens;
    }
}
