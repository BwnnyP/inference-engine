package com.github.pineasaurusrex.inference_engine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A disjunction of literals
 */
public class DisjunctionClause implements Clause {
    private List<Literal> literals;

    public DisjunctionClause(Literal... literals) {
        this(Arrays.asList(literals));
    }

    public DisjunctionClause(List<Literal> literals) {
        this.literals = literals;
    }

    public List<Literal> getLiterals() {
        return literals;
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
        return literals.stream().map(Literal::toString).collect(Collectors.joining(" ∨ "));
    }
}
