package com.github.pineasaurusrex.inference_engine;

import java.util.ArrayDeque;
import java.util.HashSet;

public class FowardChainingKnowledgeBase extends KnowledgeBase {
    Action Ask(Query q) {
        // TODO: call entails and return an action
    }

    private boolean entails(Query q) {
        HashSet<Clause> inferred = new HashSet<>();
        ArrayDeque<PropositionalSymbol> agenda = new ArrayDeque<>(getPropositionalSymbolsKnownToBeTrue());

        while (!agenda.isEmpty()) {
            PropositionalSymbol premise = agenda.addLast();
            if (!inferred.contains(premise)) {
                inferred.add(premise);
                for (Clause c : clauses) {
                    if (!c.containsPremise(premise)) {
                        continue;
                    }

                }
            }
        }
        return false;
    }
}
