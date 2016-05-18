package com.github.pineasaurusrex.inference_engine;
import java.io.*;
import java.nio.file.*;

import java.util.Optional;
import java.util.stream.Collectors;

public class Main {

    private static KnowledgeBase kb = new KnowledgeBase();
    private static PropositionalSymbol query;

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
        boolean result = false;

<<<<<<< HEAD
=======
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
        kb.tell(new ImplicationClause(new Literal(p3), new Literal(p2)));

        // TELL p3 => p1
        kb.tell(new ImplicationClause(new Literal(p1), new Literal(p3)));

        // TELL c => e
        kb.tell(new ImplicationClause(new Literal(e), new Literal(c)));

        // TELL b&e => f
        kb.tell(new ImplicationClause(new Literal(f), new Literal(b), new Literal(e)));

        // TELL f&g => h
        kb.tell(new ImplicationClause(new Literal(h), new Literal(f), new Literal(g)));

        // TELL p1 => d
        kb.tell(new ImplicationClause(new Literal(d), new Literal(p1)));

        // TELL p1&p3 => c
        kb.tell(new ImplicationClause(new Literal(c), new Literal(p1), new Literal(p3)));

        // TELL a
        kb.tell(new Literal(a));

        // TELL b
        kb.tell(new Literal(b));

        // TELL p2
        kb.tell(new Literal(p2));


>>>>>>> Redo the Forward Chaining algorithm and KB
        switch (args[0].toUpperCase()) {
            case "FC":
                //search = new ForwardChainingAlgorithm(kb);
                //result = search.entails(query);
                break;
            default:
                System.err.println("Method \"" + args[1] + "\" not implemented");
                System.exit(1);
                return;
        }

<<<<<<< HEAD
        //boolean result = search.entails();
        System.out.println(result ? "YES: " : "NO: ");
=======
        PropositionalSymbol query = d;
        try {
            // ASK d
            Optional<SearchAlgorithmResult> result = search.entails(query);
            System.out.println(result.isPresent() ? "YES: " : "NO: ");
            if (result.isPresent()) {
                System.out.println(.stream().map(PropositionalSymbol::toString).collect(Collectors.joining("; ")));
            }
        } catch(SearchAlgorithm.InvalidKnowledgeBaseException exception) {
            System.err.println(search.getClass().getSimpleName() + " does not support the knowledge-base provided: " + exception.getMessage());
            System.exit(1);
        }
>>>>>>> Redo the Forward Chaining algorithm and KB
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
            if (line != null) {
                if (line.equalsIgnoreCase("TELL")) {
                    //file valid so far, read next line as KB
                    line = reader.readLine();
                    sClauseArray = line.split(";");
                    for (int i = 0; i < sClauseArray.length; i++) {
                        //kb.Tell(ClauseParser.parseSingle(sClauseArray[i]));
                        System.out.println(ClauseParser.parseSingle(sClauseArray[i]));
                    }

                }

                if (line.equalsIgnoreCase("ASK")) {
                    // read next line and do something in Query
                    query = new PropositionalSymbol(reader.readLine());


                }
            }
        }
    }
}
