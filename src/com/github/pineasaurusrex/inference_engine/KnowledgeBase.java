package com.github.pineasaurusrex.inference_engine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
}
