package com.github.pineasaurusrex.inference_engine;

import java.util.Optional;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
//	    if (args.length < 2) {
//            System.err.println("Usage: iengine method filename");
//            System.exit(1);
//        }

        KnowledgeBase kb = new KnowledgeBase();
        SearchAlgorithm search;

        PropositionalSymbol p1 = new PropositionalSymbol("p1");
        PropositionalSymbol p2 = new PropositionalSymbol("p2");
        PropositionalSymbol p3 = new PropositionalSymbol("p3");
        PropositionalSymbol a = new PropositionalSymbol("a");
        PropositionalSymbol b = new PropositionalSymbol("b");
        PropositionalSymbol c = new PropositionalSymbol("c");
        PropositionalSymbol d = new PropositionalSymbol("d");
        PropositionalSymbol e = new PropositionalSymbol("e");
        PropositionalSymbol f = new PropositionalSymbol("f");
        PropositionalSymbol g = new PropositionalSymbol("g");
        PropositionalSymbol h = new PropositionalSymbol("h");

        // TELL p2 => p3
        kb.tell(new ComplexSentence(Connective.IMPLICATION, p2, p3));

        // TELL p3 => p1
        kb.tell(new ComplexSentence(Connective.IMPLICATION, p3, p1));

        // TELL c => e
        kb.tell(new ComplexSentence(Connective.IMPLICATION, c, e));

        // TELL b&e => f
        kb.tell(new ComplexSentence(Connective.IMPLICATION, new ComplexSentence(Connective.AND, b, e), f));

        // TELL f&g => h
        kb.tell(new ComplexSentence(Connective.IMPLICATION, new ComplexSentence(Connective.AND, f, g), h));

        // TELL p1 => d
        kb.tell(new ComplexSentence(Connective.IMPLICATION, p1, d));

        // TELL p1&p3 => c
        kb.tell(new ComplexSentence(Connective.IMPLICATION, new ComplexSentence(Connective.AND, p1, p3), c));

        // TELL a
        kb.tell(a);

        // TELL b
        kb.tell(b);

        // TELL p2
        kb.tell(p2);

        switch (args[0].toUpperCase()) {
            case "FC":
                search = new ForwardChainingAlgorithm(kb);
                break;
            default:
                System.err.println("Method \"" + args[1] + "\" not implemented");
                System.exit(1);
                return;
        }

        PropositionalSymbol query = d;
        try {
            // ASK d
            Optional<SearchAlgorithmResult> result = search.entails(query);
            System.out.println(result.isPresent() ? "YES: " : "NO: ");
            if (result.isPresent()) {
                System.out.println(result.get().getInferredSymbols().stream().map(PropositionalSymbol::toString).collect(Collectors.joining("; ")));
            }
        } catch(SearchAlgorithm.InvalidKnowledgeBaseException exception) {
            System.err.println(search.getClass().getSimpleName() + " does not support the knowledge-base provided: " + exception.getMessage());
            System.exit(1);
        }
    }
}
