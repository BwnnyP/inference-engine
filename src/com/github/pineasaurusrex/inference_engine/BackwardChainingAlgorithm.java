package com.github.pineasaurusrex.inference_engine;

import java.util.*;

public class BackwardChainingAlgorithm extends SearchAlgorithm {

    public BackwardChainingAlgorithm(KnowledgeBase kb) {
        super(kb);
    }

    /**
     * Check if the knowledge base entails the query
     * @param query the propositional symbol to check if the knowledge base can prove
     * @return if the
     */
    public Optional<SearchAlgorithmResult> entails(Sentence query) throws InvalidKnowledgeBaseException, InvalidQueryException {
        if (!query.isPropositionSymbol()) {
            throw new InvalidQueryException("Query must be a propositional symbol");
        }

        List<Clause> knowledgeBaseClauses = knowledgeBase.getClauses();
        List<PropositionalSymbol> facts = filterFacts(knowledgeBaseClauses);
        List<PropositionalSymbol> agenda = new ArrayList<>(Collections.singletonList((PropositionalSymbol) query));
        HashSet<PropositionalSymbol> entailed = new HashSet<>();

        // Loop through the agenda
        while (!agenda.isEmpty()) {
            PropositionalSymbol p = agenda.remove(0);
            entailed.add(p);
            System.err.println("Expanding " + p);

            if (facts.contains(p)) {
                System.err.println(p + " is known as a fact");
            } else {
                Optional<Clause> rule = knowledgeBaseClauses.stream()
                        .filter(clause -> clause.getPositiveSymbols().contains(p))
                        .findAny();
                // todo: handle if multiple rules are found
                if (rule.isPresent()) {
                    System.err.println("can prove " + p + " if " + rule.get());
                    rule.get().getNegativeSymbols().stream().filter(s -> !agenda.contains(s)).forEach(agenda::add);
                } else {
                    System.err.println("No way to prove " + p + " - ASSUMING NO/UNKNOWN");
                    return Optional.empty();
                }
            }
        }

        // No more symbols left to prove (agenda empty),
        // therefore the query must have been found to be true
        return Optional.of(new ChainingAlgorithmResult(entailed));
    }

    /**
     * Return all known facts from the clauses in the knowledgebase
     * @return
     */
    private List<PropositionalSymbol> filterFacts(List<Clause> knowledgeBaseClauses) throws InvalidKnowledgeBaseException {
        List<PropositionalSymbol> facts = new ArrayList<>();
        for (Clause clause : knowledgeBaseClauses) {
            // Horn-clauses can contain at most a single positive literal
            if (clause.getPositiveSymbols().size() > 1) {
                throw new InvalidKnowledgeBaseException("Knowledge base contains non-Horn clauses");
            }

            // If there are no negative literals, the clause is known to be true and add the conclusion to the agenda
            if (clause.getPositiveSymbols().size() == 1 && clause.getNegativeSymbols().size() == 0) {
                facts.add(clause.getPositiveSymbols().get(0));
            }
        }
        return facts;
    }

}
