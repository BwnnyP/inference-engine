package com.github.pineasaurusrex.inference_engine;

import java.util.stream.Stream;

public abstract class Sentence {
    public abstract Connective getConnective();
    public abstract Sentence getOperand(int i);
    public abstract Sentence[] getOperands();
    public abstract Stream<PropositionalSymbol> getPropositionalSymbols();

    public boolean isPropositionSymbol() {
        return getConnective() == null;
    }
    public int getNumberOfOperands() {
        return (getConnective() == null) ? 1 : getConnective().getOperands();
    }
}
