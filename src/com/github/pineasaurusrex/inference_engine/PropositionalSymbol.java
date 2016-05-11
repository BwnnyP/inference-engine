package com.github.pineasaurusrex.inference_engine;

public class PropositionalSymbol implements Clause {
    private String symbol;

    public PropositionalSymbol(String symbol) {
        this.symbol = symbol.toLowerCase();
    }

    public boolean equals(PropositionalSymbol obj) {
        return symbol.equals(obj.symbol);
    }
}
