package com.github.pineasaurusrex.inference_engine;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class ForwardChainingAlgorithm extends SearchAlgorithm {

    public ForwardChainingAlgorithm(KnowledgeBase kb) {
        super(kb);
    }

    public boolean entails(PropositionalSymbol query) {
        Deque<PropositionalSymbol> agenda = new ConcurrentLinkedDeque<>(knowledgeBase.getPositiveLiterals());
        Map<Clause, AtomicInteger> count = initializeCountMap();
        HashSet<PropositionalSymbol> inferredSymbols = new HashSet<>();

        // Loop through the agenda
        while (!agenda.isEmpty()) {
            PropositionalSymbol p = agenda.removeFirst();

            // Abort if the query (goal clause) was found
            if (p.equals(query)) return true;

            // If the symbol has not been inferred yet
            if (!inferredSymbols.contains(p)) {
                inferredSymbols.add(p);

                // Decrement the count of remaining premises for all clauses that contain p
                // If all the premises of an implication are known, then we can add the conclusion to the agenda
                knowledgeBase.getClauses()
                        .parallelStream()
                        .filter(c -> c.getPremiseSymbols().contains(p))
                        .forEach(c -> {
                    int newCount = count.get(c).decrementAndGet();

                    // If all the premises of an implication are known, then we can add the conclusion to the agenda
                    if (newCount == 0) {
                        agenda.addLast(c.getConclusion());
                    }
                });
            }
        }

        // Could not prove the query
        return false;
    }

    /**
     * Generate a map of the clauses in the KnowledgeBase to the number of symbols
     * @return
     */
    private Map<Clause, AtomicInteger> initializeCountMap() {
        HashMap<Clause, AtomicInteger> count = new HashMap<>();
        for (Clause c : knowledgeBase.getClauses()) {
            count.put(c, new AtomicInteger(c.getNumberOfSymbolsInPremise()));
        }
        return count;
    }

}
