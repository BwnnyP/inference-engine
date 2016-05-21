package com.github.pineasaurusrex.inference_engine;

import java.util.stream.Stream;

/**
 * This class represents a single propositional symbol, which can be chained together to form a logical sentence
 */
public class PropositionalSymbol extends Sentence {
    private String symbol;

    public PropositionalSymbol(String symbol) {
        this.symbol = symbol.toLowerCase();
    }

    @Override
    public Connective getConnective() {
        return null;
    }

    @Override
    public Sentence getOperand(int i) {
        if (i != 0) throw new IllegalArgumentException();
        return this;
    }

    @Override
    public Sentence[] getOperands() {
        Sentence[] sentences = { this };
        return sentences;
    }

    @Override
    public Stream<PropositionalSymbol> getPropositionalSymbols() {
        return Stream.of(this);
    }

    public boolean equals(PropositionalSymbol obj) {
        return symbol.equals(obj.symbol);
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

    public String toString() {
        return symbol;
    }
}
