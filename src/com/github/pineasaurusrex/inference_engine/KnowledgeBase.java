package com.github.pineasaurusrex.inference_engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KnowledgeBase {
    protected ArrayList<Sentence> sentences = new ArrayList<>();

    /**
     * Add the sentence to the Knowledge Base
     * @param s
     */
    public void tell(Sentence s) {
        // TODO: assert that sentence is true given current knowledge
        sentences.add(s);
    }

    public void tell(List<Sentence> s) {
        for (Sentence element : s) tell(element);
    }


    public List<Sentence> getSentences() {
        return sentences;
    }


    /**
     * @return the clauses in the Knowledge Base that are disjunct clauses
     */
    public List<Clause> getClauses() {
        return sentences.stream()
                .map(Clause::fromSentence)
                .collect(Collectors.toList());
    }

    /**
     * Return the symbols used in the Knowledge Base clauses
     * @return set of symbols present in KB
     */
    public List<PropositionalSymbol> getPropositionalSymbols() {
        return sentences.stream()
                .flatMap(Sentence::getPropositionalSymbols)
                .collect(Collectors.toList());
    }
}
