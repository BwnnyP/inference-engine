package com.github.pineasaurusrex.inference_engine;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A complex sentence is formed by a set of simpler sentences joined by a connective
 */
public class ComplexSentence extends Sentence {
    private Connective connective;
    private Sentence[] operands;

    public ComplexSentence(Connective connective, Sentence... operands) {
        assert(operands.length == connective.getOperands());
        this.connective = connective;
        this.operands = operands;
    }

    public ComplexSentence(Connective connective, List<Sentence> operands) {
        assert(operands.size() == connective.getOperands());
        this.connective = connective;
        this.operands = operands.toArray(new Sentence[operands.size()]);
    }

    @Override
    public Connective getConnective() {
        return connective;
    }

    @Override
    public Sentence getOperand(int i) {
        return operands[i];
    }

    @Override
    public Sentence[] getOperands() {
        return operands;
    }

    @Override
    public Stream<PropositionalSymbol> getPropositionalSymbols() {
        return Arrays.stream(operands).flatMap(Sentence::getPropositionalSymbols);
    }

    @Override
    public String toString() {
        if (connective.getOperands() == 1) {
            return connective.getSymbol() + operands[0].toString();
        } else {
            return "(" + Stream.of(operands).map(Sentence::toString).collect(Collectors.joining(connective.getSymbol())) + ")";
        }
    }
}
