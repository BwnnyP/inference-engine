package com.github.pineasaurusrex.inference_engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KnowledgeBase {
    protected ArrayList<Sentence> sentences = new ArrayList<>();

    public void tell(Sentence s) {
        // TODO: assert that sentence is true given current knowledge
        sentences.add(s);
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

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
