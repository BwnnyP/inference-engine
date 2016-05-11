package com.github.pineasaurusrex.inference_engine;

import java.util.ArrayList;

public abstract class KnowledgeBase {
    protected ArrayList<Sentence> clauses = new ArrayList<>();

    void Tell(Sentence s) {
        // TODO: assert that sentence is true given current knowledge
        clauses.add(s);
    }

    abstract Action Ask(Query q);
}
