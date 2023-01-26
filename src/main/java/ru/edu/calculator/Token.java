package ru.edu.calculator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

public abstract class Token {}

class OpenBracket extends Token {}

class CloseBracket extends Token {}

@AllArgsConstructor
@Getter
class Number extends Token {
    private double value;
}

@Getter
abstract class BinaryOperator extends Token implements Comparable<BinaryOperator> {
    private final int priority;
    private final boolean isLeftAssociative;

    protected BinaryOperator(int priority, boolean isLeftAssociative) {
        this.priority = priority;
        this.isLeftAssociative = isLeftAssociative;
    }

    abstract public Number evaluate(Number a, Number b);

    @Override
    public int compareTo(@NonNull BinaryOperator o) {
        if (isLeftAssociative) {
            return Integer.compare(priority, o.priority) - 1;
        }
        return 1;
    }
}

class BinaryPlus extends BinaryOperator {
    public BinaryPlus() {
        super(0, true);
    }

    @Override
    public Number evaluate(Number a, Number b) {
        return new Number(a.getValue() + b.getValue());
    }
}

class BinaryMinus extends BinaryOperator {
    public BinaryMinus() {
        super(0, true);
    }

    @Override
    public Number evaluate(Number a, Number b) {
        return new Number(a.getValue() - b.getValue());
    }
}

class BinaryMultiplication extends BinaryOperator {
    public BinaryMultiplication() {
        super(1, true);
    }

    @Override
    public Number evaluate(Number a, Number b) {
        return new Number(a.getValue() * b.getValue());
    }
}

class BinaryDivision extends BinaryOperator {
    public BinaryDivision() {
        super(1, true);
    }

    @Override
    public Number evaluate(Number a, Number b) {
        return new Number(a.getValue() / b.getValue());
    }
}

class BinaryPower extends BinaryOperator {
    public BinaryPower() {
        super(2, false);
    }

    @Override
    public Number evaluate(Number a, Number b) {
        return new Number(Math.pow(a.getValue(), b.getValue()));
    }
}