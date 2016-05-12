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
    public void tell(Clause c) {
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
     * @return the clauses in the Knowledge Base that are disjunct clauses
     */
    public List<DisjunctionClause> getDisjunctionClauses() {
        return clauses.stream()
<<<<<<< HEAD
                .filter(c -> c instanceof PropositionalSymbol)
                .map(c -> (PropositionalSymbol) c)
=======
                .filter(c -> c instanceof DisjunctionClause)
                .map(c -> (DisjunctionClause) c)
>>>>>>> Redo the Forward Chaining algorithm and KB
                .collect(Collectors.toList());
    }
}
