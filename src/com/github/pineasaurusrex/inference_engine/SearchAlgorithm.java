package com.github.pineasaurusrex.inference_engine;

import java.util.Optional;

public abstract class SearchAlgorithm {
    protected KnowledgeBase knowledgeBase;

    public SearchAlgorithm(KnowledgeBase kb) {
        this.knowledgeBase = kb;
    }

    public abstract Optional<SearchAlgorithmResult> entails(Sentence q) throws InvalidKnowledgeBaseException, InvalidQueryException;

    public class InvalidKnowledgeBaseException extends Exception {
        public InvalidKnowledgeBaseException(String message) {
            super(message);
        }
    }

    public class InvalidQueryException extends Exception {
        public InvalidQueryException(String message) {
            super(message);
        }
    }
}
