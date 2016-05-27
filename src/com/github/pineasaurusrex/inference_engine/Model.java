package com.github.pineasaurusrex.inference_engine;

import java.util.HashMap;

/**
 * Partially or fully assigned model
 * A model represents a possible representation of the propositional symbol states in the KB
 */
public class Model {
    private HashMap<PropositionalSymbol, Boolean> symbolValues = new HashMap<>();

    public boolean holdsTrue(Sentence sentence) {
        if (sentence.isPropositionSymbol()) {
            return symbolValues.get(sentence);
        } else {
            switch(sentence.getConnective()) {
                case NOT:
                    return !holdsTrue(sentence.getOperand(0));
                case AND:
                    return holdsTrue(sentence.getOperand(0)) && holdsTrue(sentence.getOperand(1));
                case OR:
                    return holdsTrue(sentence.getOperand(0)) || holdsTrue(sentence.getOperand(1));
                case IMPLICATION:
                    return !holdsTrue(sentence.getOperand(0)) || holdsTrue(sentence.getOperand(1));
                case BICONDITIONAL:
                    return holdsTrue(sentence.getOperand(0)) == holdsTrue(sentence.getOperand(1));
            }
        }
        return false;
    }

    public boolean holdsTrue(KnowledgeBase kb) {
        return kb.getSentences().parallelStream()
                .map(this::holdsTrue)
                .allMatch(Boolean::booleanValue);
    }


    /**
     * Returns a new model, with the union of the results of the old model and the result passed in
     * @param symbol the symbol to merge in
     * @param b the value to set
     * @return a new Model object
     */
    public Model union(PropositionalSymbol symbol, boolean b) {
        Model m = new Model();
        m.symbolValues.putAll(this.symbolValues);
        m.symbolValues.put(symbol, b);
        return m;
    }
}
