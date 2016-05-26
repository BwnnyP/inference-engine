package com.github.pineasaurusrex.inference_engine;

import java.util.Set;

public class ChainingAlgorithmResult implements SearchAlgorithmResult {
    private Set<PropositionalSymbol> inferredSymbols;

    public ChainingAlgorithmResult(Set<PropositionalSymbol> inferredSymbols) {
        this.inferredSymbols = inferredSymbols;
    }

    public Set<PropositionalSymbol> getInferredSymbols() {
        return inferredSymbols;
    }
}
