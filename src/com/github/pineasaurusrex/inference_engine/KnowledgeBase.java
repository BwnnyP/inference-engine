package com.github.pineasaurusrex.inference_engine;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class KnowledgeBase {
    protected ArrayList<Clause> clauses = new ArrayList<>();

    public void Tell(Clause s) {
        // TODO: assert that sentence is true given current knowledge
        clauses.add(s);
    }

    public abstract Action Ask(Query q);

    protected ArrayList<PropositionalSymbol> getPropositionalSymbolsKnownToBeTrue() {
        return clauses.stream()
                .filter(s -> s instanceof PropositionalSymbol)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
