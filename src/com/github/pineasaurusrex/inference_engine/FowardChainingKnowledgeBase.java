package com.github.pineasaurusrex.inference_engine;

import java.util.ArrayDeque;
import java.util.HashSet;

public class FowardChainingKnowledgeBase extends KnowledgeBase {
    Action Ask(Query q) {
        // TODO: call entails and return an action
    }

    private boolean entails(Query q) {
        HashSet<Clause> inferred = new HashSet<>();
        ArrayDeque<Clause> agenda = new ArrayDeque<>();

        while (!agenda.isEmpty()) {
            Clause premise = agenda.addLast();
            if (!inferred.contains(premise)) {
                inferred.add(premise);

            }
        }
        // TODO: the thing
    }
}
