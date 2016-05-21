package com.github.pineasaurusrex.inference_engine;

/**
 * Convert Sentence to Conjunctive Normal Form
 */
public class CNFConverter {
    public static Sentence convert(Sentence sentence) {
        // http://stackoverflow.com/a/9533548
        // convert to NNF, then apply the distributive law
        return convertToNNF(sentence);
        // TODO: apply distributive law
    }

    /**
     * Convert a sentence to NNF form, where negation is only allowed directly on propositional symbols
     * @param sentence the sentence to convert
     * @return a sentence in NNF form
     */
    private static Sentence convertToNNF(Sentence sentence) {
        Sentence transformedSentence = eliminateBiconditional(sentence);
        transformedSentence = eliminateImplication(transformedSentence);
        transformedSentence = moveNotInwards(transformedSentence);
        return transformedSentence;
    }

    /**
     * Eliminate the implication connective
     * @param sentence the sentence to operate on
     * @return a sentence without implication connectives
     */
    private static Sentence eliminateImplication(Sentence sentence) {
        if (sentence.isPropositionSymbol()) {
            // return propositional symbols (atomic sentences) unchanged
            return sentence;
        } else if (sentence.getConnective() == Connective.IMPLICATION) {
            // replace a => b with ~a | b
            Sentence a = eliminateImplication(sentence.getOperand(0));
            Sentence b = eliminateImplication(sentence.getOperand(1));
            Sentence notA = new ComplexSentence(Connective.NOT, a);
            return new ComplexSentence(Connective.OR, notA, b);
        } else {
            // recursively eliminate implications
            Sentence[] operands = sentence.getOperands();
            for (int i = 0; i < operands.length; i++) {
                operands[i] = eliminateImplication(operands[i]);
            }
            return new ComplexSentence(sentence.getConnective(), operands);
        }
    }

    /**
     * Eliminate the biconditional connective
     * @param sentence the sentence to operate on
     * @return a sentence without biconditional connectives
     */
    private static Sentence eliminateBiconditional(Sentence sentence) {
        // TODO: implement biconditional elimination
        return sentence;
    }

    /**
     * Move the NOT operations inwards by recursively applying the double-negation and de morgan's laws
     * @param sentence the sentence to operate on
     * @return a sentence with all NOT operations applying only on atomic sentences
     */
    private static Sentence moveNotInwards(Sentence sentence) {
        if (sentence.isPropositionSymbol()) {
            // return propositional symbols (atomic sentences) unchanged
            return sentence;
        } else if (sentence.getConnective() == Connective.NOT) {
            Sentence negated = sentence.getOperand(0);
            if (negated.isPropositionSymbol()) {
                return sentence; // not fully moved in
            } else if (negated.getConnective() == Connective.NOT) {
                // apply double negation rule: ~(~a) == a
                return moveNotInwards(negated.getOperand(0));
            } else if (negated.getConnective() == Connective.AND) {
                // apply de morgan's law: ~(a & b) == (~a | ~b)
                Sentence a = negated.getOperand(0);
                Sentence b = negated.getOperand(1);
                Sentence notA = moveNotInwards(new ComplexSentence(Connective.NOT, a));
                Sentence notB = moveNotInwards(new ComplexSentence(Connective.NOT, b));
                return new ComplexSentence(Connective.OR, notA, notB);
            } else if (negated.getConnective() == Connective.OR) {
                // apply de morgan's law: ~(a | b) == (~a & ~b)
                Sentence a = negated.getOperand(0);
                Sentence b = negated.getOperand(1);
                Sentence notA = moveNotInwards(new ComplexSentence(Connective.NOT, a));
                Sentence notB = moveNotInwards(new ComplexSentence(Connective.NOT, b));
                return new ComplexSentence(Connective.AND, notA, notB);
            } else {
                throw new IllegalArgumentException("Cannot handle connective: " + negated.getConnective().toString());
            }
        } else {
            // recursively move not operations until they are beside the atomic sentences
            Sentence a = moveNotInwards(sentence.getOperand(0));
            Sentence b = moveNotInwards(sentence.getOperand(1));
            return new ComplexSentence(sentence.getConnective(), a, b);
        }
    }
}
