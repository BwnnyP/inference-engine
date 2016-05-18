package com.github.pineasaurusrex.inference_engine;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class ForwardChainingAlgorithm extends SearchAlgorithm {

    public ForwardChainingAlgorithm(KnowledgeBase kb) {
        super(kb);
    }

<<<<<<< HEAD
    public boolean entails(PropositionalSymbol query) {
        return false;
    }
/*
    public boolean entails(PropositionalSymbol query) {
        Deque<PropositionalSymbol> agenda = new ConcurrentLinkedDeque<>(knowledgeBase.getPositiveLiterals());
=======
    /**
     * Check if the knowledge base entails the query
     * @param query the propositional symbol to check if the knowledge base can prove
     * @return if the
     */
    public Optional<SearchAlgorithmResult> entails(PropositionalSymbol query) throws InvalidKnowledgeBaseException {
        Deque<PropositionalSymbol> agenda = initializeAgenda();
>>>>>>> Redo the Forward Chaining algorithm and KB
        Map<Clause, AtomicInteger> count = initializeCountMap();
        HashSet<PropositionalSymbol> inferredSymbols = new HashSet<>();

        // Loop through the agenda
        while (!agenda.isEmpty()) {
            PropositionalSymbol p = agenda.removeFirst();
            System.out.println("Expanding " + p);

            // If the symbol has not been inferred yet
            if (!inferredSymbols.contains(p)) {
                inferredSymbols.add(p);

                // Abort if the query (goal clause) was found
                if (p.equals(query)) {
                    return Optional.of(new ForwardChainingAlgorithmResult(inferredSymbols));
                }

                // Decrement the count of remaining premises for all clauses that contain p
                // If all the premises of an implication are known, then we can add the conclusion to the agenda
                knowledgeBase.getClauses()
                        .parallelStream()
                        .filter(c -> c.getNegativeSymbols().contains(p))
                        .forEach(c -> {
                            System.out.println(p + " is a premise of " + c);
                            int newCount = count.get(c).decrementAndGet();

                            // If all the premises of an implication are known, then we can add the conclusion to the agenda
                            if (newCount == 0) {
                                PropositionalSymbol conclusion = c.getPositiveSymbols().get(0);
                                System.out.println("all premises of " + c + " have been found, adding " + conclusion + " to agenda");
                                agenda.addLast(conclusion);
                            }
                });
            }
        }

        // Could not prove the query
        return Optional.empty();
    }
*/
    /**
     * Initialize the agenda with all known facts
     * @return a thread-safe deque to be used as the initial agenda
     */
    private Deque<PropositionalSymbol> initializeAgenda() throws InvalidKnowledgeBaseException {
        Deque<PropositionalSymbol> agenda = new ConcurrentLinkedDeque<>();
        for (Clause clause : knowledgeBase.getClauses()) {
            // Horn-clauses can contain at most a single positive literal
            if (clause.getPositiveSymbols().size() > 1) {
                throw new InvalidKnowledgeBaseException("Knowledge base contains non-Horn clauses");
            }

            // If there are no negative literals, therefore the clause is known to be true
            if (clause.getPositiveSymbols().size() == 1 && clause.getNegativeSymbols().size() == 0) {
                agenda.addLast(clause.getPositiveSymbols().get(0));
            }
        }
        return agenda;
    }

    /**
     * Generate a map of the disjunction clauses in the KnowledgeBase to the number of negative literals in each clause
     * @return a map of DisjunctionClause to AtomicIntegers
     */
    private Map<Clause, AtomicInteger> initializeCountMap() {
        HashMap<Clause, AtomicInteger> count = new HashMap<>();
        for (Clause c : knowledgeBase.getClauses()) {
<<<<<<< HEAD
            //count.put(c, new AtomicInteger(c.getNumberOfSymbolsInPremise()));
=======
            count.put(c, new AtomicInteger(c.getNegativeSymbols().size()));
>>>>>>> Redo the Forward Chaining algorithm and KB
        }
        return count;
    }

}
