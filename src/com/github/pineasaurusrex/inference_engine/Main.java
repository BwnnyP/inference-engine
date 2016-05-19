package com.github.pineasaurusrex.inference_engine;
import java.io.*;
import java.nio.file.*;

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

        switch (args[0].toUpperCase()) {
            case "FC":
                //search = new ForwardChainingAlgorithm(kb);
                //result = search.entails(query);
                break;
            default:
                System.err.println("Method \"" + args[1] + "\" not implemented");
                System.exit(1);
        }

        //boolean result = search.entails();
        System.out.println(result ? "YES: " : "NO: ");
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
