package com.github.pineasaurusrex.inference_engine;

public enum Connective {
    NOT("~", 10, 1),
    AND("&", 8, 2),
    OR("|", 6, 2),
    IMPLICATION("=>", 4, 2),
    BICONDITIONAL("<=>", 2, 2);

    private final String symbol;
    private final int precedence;
    private final int operands;

    Connective(String symbol, int precedence, int operands) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.operands = operands;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getPrecedence() {
        return precedence;
    }

    public int getOperands() {
        return operands;
    }
}
