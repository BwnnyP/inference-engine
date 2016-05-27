package com.github.pineasaurusrex.inference_engine;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import java.util.Optional;

import java.util.Optional;
import java.util.stream.Collectors;

public class Main {

    private static KnowledgeBase kb = new KnowledgeBase();
    private static Sentence query;

    public static void main(String[] args) {
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

        switch (args[0].toUpperCase()) {
            case "TT":
                search = new TruthTableAlgorithm(kb);
                break;
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
                System.out.println(result.get());
            }
        } catch(SearchAlgorithm.InvalidKnowledgeBaseException exception) {
            System.err.println(search.getClass().getSimpleName() + " does not support the knowledge-base provided: " + exception.getMessage());
            System.exit(1);
        } catch(SearchAlgorithm.InvalidQueryException exception) {
            System.err.println(search.getClass().getSimpleName() + " does not support the query provided: " + exception.getMessage());
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
                    kb.tell(evaluateRPNtoSentence(ClauseParser.parseSingle(sClauseArray[i])));
                }
            }

            line = reader.readLine();
            if (line.equalsIgnoreCase("ASK")) {
                // read next line and do something in Query
                query = PropositionalSymbol.addStringToPropositionalSymbol(reader.readLine().trim());
            }
        }
    }

    private static List<Sentence> evaluateRPNtoSentence (ArrayDeque<String> rpnSentence) {

        ArrayDeque<Sentence> outputQueue = new ArrayDeque<>();

        while (!rpnSentence.isEmpty()){
            if (!ClauseParser.isOperator(rpnSentence.peekFirst())) {
                outputQueue.add(PropositionalSymbol.addStringToPropositionalSymbol(rpnSentence.pollFirst()));
                continue;

            } else {
                switch (rpnSentence.pollFirst()) {
                    case "=>" :
                        outputQueue.add(new ComplexSentence(Connective.IMPLICATION, outputQueue.pollFirst(), outputQueue.pollFirst()));
                        break;
                    case  "&":
                        outputQueue.add(new ComplexSentence(Connective.AND, outputQueue.pollFirst(), outputQueue.pollFirst()));
                        break;
                    case  "\\/":
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
