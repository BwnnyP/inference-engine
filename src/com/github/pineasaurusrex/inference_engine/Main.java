package com.github.pineasaurusrex.inference_engine;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import java.util.Optional;
import java.util.stream.Collectors;

public class Main {

    private static KnowledgeBase kb = new KnowledgeBase();
    private static PropositionalSymbol query;

    public static void main(String[] args) {

        System.out.println(Connective.getValueFromSymbol("&"));

        if (args.length < 2) {
            System.err.println("Usage: iengine method filename");
            System.exit(1);
        }

        //read file
        try {
            readFile(args[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //evaluate
        SearchAlgorithm search;

/*
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

        //PropositionalSymbol query = d;
*/


        switch (args[0].toUpperCase()) {
            case "FC":
                search = new ForwardChainingAlgorithm(kb);
                break;
            default:
                System.err.println("Method \"" + args[1] + "\" not implemented");
                System.exit(1);
                return;
        }


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

    /**
     * Read horn form knowledge base and query to be evaluated
     * @param filePath relative file path to input text file
     */
    private static void readFile(String filePath) throws Exception {
        Path file = Paths.get(filePath);
        String[] sClauseArray;

        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String line = reader.readLine();
            if (line.equalsIgnoreCase("TELL")) {
                //file valid so far, read next line as KB
                line = reader.readLine();
                sClauseArray = line.split(";");
                for (int i = 0; i < sClauseArray.length; i++) {
                    //System.out.println(ClauseParser.parseSingle(sClauseArray[i]));
                    kb.tell(evaluateRPNtoSentence(ClauseParser.parseSingle(sClauseArray[i])));
                }
            }

            line = reader.readLine();
            if (line.equalsIgnoreCase("ASK")) {
                // read next line and do something in Query
                query = new PropositionalSymbol(reader.readLine());
            }
        }
    }

    private static List<Sentence> evaluateRPNtoSentence (ArrayDeque<String> rpnSentence) {

        ArrayDeque<Sentence> outputQueue = new ArrayDeque<>();

        //TODO this will create duplicate PropositionalSymbol objects, check if this is bad. YES this is bad
        while (!rpnSentence.isEmpty()){
            if (!ClauseParser.isOperator(rpnSentence.peekFirst())) {
                outputQueue.add(new PropositionalSymbol(rpnSentence.pollFirst()));
                continue;

            } else {
                switch (rpnSentence.pollFirst()) {
                    case "=>":
                        outputQueue.add(new ComplexSentence(Connective.IMPLICATION, outputQueue.pollFirst(), outputQueue.pollFirst()));
                        break;
                    case  "&":
                        outputQueue.add(new ComplexSentence(Connective.AND, outputQueue.pollFirst(), outputQueue.pollFirst()));
                        break;
                    case  "|":
                        outputQueue.add(new ComplexSentence(Connective.OR, outputQueue.pollFirst(), outputQueue.pollFirst()));
                        break;
                    case  "<=>":
                        outputQueue.add(new ComplexSentence(Connective.BICONDITIONAL, outputQueue.pollFirst(), outputQueue.pollFirst()));
                        break;
                    case  "~":
                        outputQueue.add(new ComplexSentence(Connective.NOT, outputQueue.pollFirst()));
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator found in KB");
                }
            }
        }
        return (new ArrayList<Sentence>(outputQueue));

    }


}
