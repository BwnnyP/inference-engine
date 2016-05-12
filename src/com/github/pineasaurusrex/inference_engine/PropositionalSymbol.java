package com.github.pineasaurusrex.inference_engine;

public class PropositionalSymbol {
    private String symbol;

    public PropositionalSymbol(String symbol) {
        this.symbol = symbol.toLowerCase();
    }

    public boolean equals(PropositionalSymbol obj) {
        return symbol.equals(obj.symbol);
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

    public boolean containsPremise(PropositionalSymbol premise) {
        return this.equals(premise);
    }

    public String toString() {
        return symbol;
    }
}
