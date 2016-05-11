package com.github.pineasaurusrex.inference_engine;

public class BinaryOperator implements Clause {
    protected Clause firstOperand;
    protected Clause secondOperand;

    public BinaryOperator(Clause a, Clause b) {
        firstOperand = a;
        secondOperand = b;
    }

    public boolean containsPremise(PropositionalSymbol premise) {
        return firstOperand.containsPremise(premise) || secondOperand.containsPremise(premise);
    }
}
