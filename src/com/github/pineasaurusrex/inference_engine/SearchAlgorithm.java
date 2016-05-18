package com.github.pineasaurusrex.inference_engine;

import java.util.Optional;

public abstract class SearchAlgorithm {
    protected KnowledgeBase knowledgeBase;

    public SearchAlgorithm(KnowledgeBase kb) {
        this.knowledgeBase = kb;
    }

    public abstract Optional<SearchAlgorithmResult> entails(PropositionalSymbol q) throws InvalidKnowledgeBaseException;

    public class InvalidKnowledgeBaseException extends Exception {
        public InvalidKnowledgeBaseException(String message) {
            super(message);
        }
    }
}
