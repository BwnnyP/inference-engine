package com.github.pineasaurusrex.inference_engine;

public class NegationOperator implements Clause {
    private Clause clause;

    public NegationOperator(Clause clause) {
        this.clause = clause;
    }

    public boolean containsPremise(PropositionalSymbol premise) {
        return clause.containsPremise(premise);
    }
}
