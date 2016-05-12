package com.github.pineasaurusrex.inference_engine;

import java.util.List;

public interface Clause {
    List<Literal> getLiterals();

    List<PropositionalSymbol> getNegativeSymbols();

    List<PropositionalSymbol> getPositiveSymbols();
}
