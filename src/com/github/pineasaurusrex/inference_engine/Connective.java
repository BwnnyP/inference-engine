package com.github.pineasaurusrex.inference_engine;

//connective symbol, precedence, number of operands expected
public enum Connective {
    NOT("~", 10, 1),
    AND("&", 8, 2),
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

    //no need for a hashmap in our case, https://stackoverflow.com/questions/1080904/how-can-i-lookup-a-java-enum-from-its-string-value
    public static Connective getValueFromSymbol (String inputSymbol) {
        for (Connective c : values()) {
            if (c.getSymbol().equals(inputSymbol)) {
                return c;
            }
        }
        return null;
    }

    public static boolean isConnectiveFromSymbol (String inputSymbol) {
        if (getValueFromSymbol(inputSymbol) == null) {
            return true;
        } else {
            return false;
        }
    }
}
