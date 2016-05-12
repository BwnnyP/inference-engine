package com.github.pineasaurusrex.inference_engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A literal consists of a single propositional symbol or it's negation
 */
public class Literal implements Clause {
    private PropositionalSymbol symbol;
    private boolean negated;

    public Literal(String symbol) {
        this(new PropositionalSymbol(symbol), false);
    }

    public Literal(PropositionalSymbol symbol) {
        this(symbol, false);
    }

    public Literal(String symbol, boolean negated) {
        this(new PropositionalSymbol(symbol), negated);
    }

    public Literal(PropositionalSymbol symbol, boolean negated) {
        this.symbol = symbol;
        this.negated = negated;
    }

    public PropositionalSymbol getSymbol() {
        return symbol;
    }

    public boolean isNegativeLiteral() {
        return negated;
    }

    public boolean isPositiveLiteral() {
        return !negated;
    }

    public boolean equals(Literal literal) {
        return this.symbol.equals(literal.symbol) && this.negated == literal.negated;
    }

    @Override
    public int hashCode() {
        return this.symbol.toString().hashCode() + ((this.negated) ? 1 : 0);
    }

    public String toString() {
        if (negated) {
            return "¬ " + symbol.toString();
        } else {
            return symbol.toString();
        }
    }

    @Override
    public List<Literal> getLiterals() {
        return Collections.singletonList(this);
    }

    @Override
    public List<PropositionalSymbol> getNegativeSymbols() {
        if (negated) {
            return Collections.singletonList(this.getSymbol());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<PropositionalSymbol> getPositiveSymbols() {
        if (!negated) {
            return Collections.singletonList(this.getSymbol());
        } else {
            return new ArrayList<>();
        }
    }
}
