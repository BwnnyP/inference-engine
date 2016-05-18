package com.github.pineasaurusrex.inference_engine;

import java.util.Set;

public interface SearchAlgorithmResult {
    Set<PropositionalSymbol> getInferredSymbols();
}
