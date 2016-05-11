package com.github.pineasaurusrex.inference_engine;

public interface Clause {
    boolean containsPremise(PropositionalSymbol premise);
}
