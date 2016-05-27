package com.github.pineasaurusrex.inference_engine;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class TruthTableAlgorithm extends SearchAlgorithm {
    public TruthTableAlgorithm(KnowledgeBase kb) {
        super(kb);
    }

    @Override
    public Optional<SearchAlgorithmResult> entails(Sentence q) {
        // enumerate the models
        // check that q is true in all models in which the KB is true
        List<PropositionalSymbol> symbols = knowledgeBase.getPropositionalSymbols();
        AtomicInteger modelCounter = new AtomicInteger(0);
        boolean entailed = checkAll(q, symbols, new Model(), modelCounter).allMatch(Boolean::booleanValue);
        if (entailed) {
            return Optional.of(new TruthTableAlgorithmResult(modelCounter.get()));
        } else {
            return Optional.empty();
        }

    }

    private Stream<Boolean> checkAll(Sentence q, List<PropositionalSymbol> symbols, Model model, AtomicInteger modelCounter) {
        if (!symbols.isEmpty()) {
            PropositionalSymbol symbol = symbols.get(0);
            List<PropositionalSymbol> remainingSymbols = symbols.subList(1, symbols.size());

            // possible research: try using multi-threading and exploring it in parallel
            return Stream.of(false, true).flatMap(val -> checkAll(q, remainingSymbols, model.union(symbol, val), modelCounter));
        } else {
            if (model.holdsTrue(knowledgeBase)) {
                boolean entailsQuery = model.holdsTrue(q);
                if (entailsQuery) { modelCounter.incrementAndGet(); }
                return Stream.of(entailsQuery);
            } else {
                // when KB is false, return true
                return Stream.of(true);
            }
        }
    }
}
