package com.github.pineasaurusrex.inference_engine;

public class ConjunctionOperator extends BinaryOperator implements Clause {
    public ConjunctionOperator(Clause a, Clause b) {
        super(a, b);
    }
}
