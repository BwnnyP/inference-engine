package com.github.pineasaurusrex.inference_engine;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A clause is a disjunction of literals
 */
public class Clause {
    public static Clause fromSentence(Sentence sentence) {
        Sentence cnfSentence = CNFConverter.convert(sentence);

        List<Literal> literals = collectLiterals(cnfSentence);

        return new Clause(literals, sentence);
    }

    private static List<Literal> collectLiterals(Sentence cnfSentence) {
        if (cnfSentence.isPropositionSymbol()) { // a non-negated propositional symbol
            return Collections.singletonList(new Literal((PropositionalSymbol) cnfSentence, false));
        } else if (cnfSentence.getConnective() == Connective.NOT) { // a negated propositional symbol
            assert(cnfSentence.getOperand(0).isPropositionSymbol());
            return Collections.singletonList(new Literal((PropositionalSymbol) cnfSentence.getOperand(0), true));
        } else if (cnfSentence.getConnective() == Connective.AND) {
            // todo: handle AND connective
            throw new IllegalArgumentException("TODO: AND connective not handled");
        } else if (cnfSentence.getConnective() == Connective.OR) {
            return Arrays.stream(cnfSentence.getOperands())
                    .flatMap(operand -> collectLiterals(operand).stream())
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Sentence not in CNF form");
        }
    }

    private List<Literal> literals;
    private Sentence originalSentence = null; // only used for debugging

    public Clause(List<Literal> literals, Sentence originalSentence) {
        this.literals = literals;
        this.originalSentence = originalSentence;
    }

    public List<Literal> getLiterals() {
        return literals;
    }

    public List<PropositionalSymbol> getSymbols() {
        return literals.stream()
                .map(Literal::getSymbol)
                .collect(Collectors.toList());
    }

    public List<PropositionalSymbol> getNegativeSymbols() {
        return literals.stream()
                .filter(Literal::isNegativeLiteral)
                .map(Literal::getSymbol)
                .collect(Collectors.toList());
    }

    public List<PropositionalSymbol> getPositiveSymbols() {
        return literals.stream()
                .filter(Literal::isPositiveLiteral)
                .map(Literal::getSymbol)
                .collect(Collectors.toList());
    }

    public String toString() {
        return literals.stream().map(Literal::toString).collect(Collectors.joining(" " + Connective.OR.getSymbol() + " "));
    }
}
