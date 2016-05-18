package com.github.pineasaurusrex.inference_engine;

import java.util.Set;

public class ForwardChainingAlgorithmResult implements SearchAlgorithmResult {
    private Set<PropositionalSymbol> inferredSymbols;

    public ForwardChainingAlgorithmResult(Set<PropositionalSymbol> inferredSymbols) {
        this.inferredSymbols = inferredSymbols;
    }

    public Set<PropositionalSymbol> getInferredSymbols() {
        return inferredSymbols;
    }
}
