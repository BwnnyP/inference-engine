package com.github.pineasaurusrex.inference_engine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImplicationClause implements Clause {
    private List<Literal> premises;
    private Literal conclusion;


    public ImplicationClause(Literal conclusion, Literal... premises) {
        this(Arrays.asList(premises), conclusion);
    }

    /**
     * Creates a new ImplicationClause
     * @param premises a conjunction of literals making up the premise of the implication
     * @param conclusion the conclusion of the implication
     */
    public ImplicationClause(List<Literal> premises, Literal conclusion) {
        this.premises = premises;
        this.conclusion = conclusion;
    }

    public List<Literal> getLiterals() {
        //TODO;
        return null;
    }

    @Override
    public List<PropositionalSymbol> getNegativeSymbols() {
        List<PropositionalSymbol> negativeSymbols = premises.stream()
                .filter(Literal::isPositiveLiteral)
                .map(Literal::getSymbol)
                .collect(Collectors.toList());
        if (conclusion.isNegativeLiteral()) {
            negativeSymbols.add(conclusion.getSymbol());
        }
        return negativeSymbols;
    }

    @Override
    public List<PropositionalSymbol> getPositiveSymbols() {
        List<PropositionalSymbol> positiveSymbols = premises.stream()
                .filter(Literal::isNegativeLiteral)
                .map(Literal::getSymbol)
                .collect(Collectors.toList());
        if (conclusion.isPositiveLiteral()) {
            positiveSymbols.add(conclusion.getSymbol());
        }
        return positiveSymbols;
    }

    public String toString() {
        return premises.stream().map(Literal::toString).collect(Collectors.joining(" ∧ ")) + " → " + conclusion;
    }

}
