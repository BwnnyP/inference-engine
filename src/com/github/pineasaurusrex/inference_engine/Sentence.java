package com.github.pineasaurusrex.inference_engine;

public abstract class Sentence {
    public abstract Connective getConnective();
    public abstract int getNumberOfSentences();
    public abstract Sentence getOperand(int i);
    public abstract Sentence[] getOperands();

    public boolean isPropositionSymbol() {
        return getConnective() == null;
    }
}
