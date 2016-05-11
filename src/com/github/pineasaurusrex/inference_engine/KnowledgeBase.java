package com.github.pineasaurusrex.inference_engine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KnowledgeBase {
    protected ArrayList<Clause> clauses = new ArrayList<>();

    /**
     * Add the clause to the Knowledge Base
     * @param c
     */
    public void Tell(Clause c) {
        // TODO: assert that sentence is true given current knowledge
        clauses.add(c);
    }

    /**
     * Return the clauses the Knowledge Base contains
     * @return list
     */
    public List<Clause> getClauses() {
        return clauses;
    }

    /**
     * Find the list of positive literals from the clauses in the Knowledge Base
     * @return a list containing only positive literals
     */
    public List<PropositionalSymbol> getPositiveLiterals() {
        return clauses.stream()
                .filter(c -> c instanceof PropositionalSymbol)
                .map(c -> (PropositionalSymbol) c)
                .collect(Collectors.toList();
    }
}
