package com.github.pineasaurusrex.inference_engine;
import java.io.*;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {
	    if (args.length < 2) {
            System.err.println("Usage: iengine method filename");
            System.exit(1);
        }

        KnowledgeBase kb = new KnowledgeBase();
        SearchAlgorithm search;

        switch (args[1].toUpperCase()) {
            case "FC":
                search = new ForwardChainingAlgorithm(kb);
                break;
            default:
                System.err.println("Method \"" + args[1] + "\" not implemented");
                System.exit(1);
        }

        boolean result = search.entails();
        System.out.println(result ? "YES: " : "NO: ");
    }

    private void readFile(String filePath) throws Exception {
        Path file = Paths.get(filePath);

        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String line = reader.readLine();
            if (line != null) {
                if (line.equals("TELL")) {
                    //file valid so far, read next line as KB
                    //read next line
                    line = reader.readLine();
                    String sClauseArray =  line.split(";");
                    for (int i = 0; i < sClauseArray.length(); i++) {
                        KnowledgeBase ClauseParser.parseSingle(sClauseArray[i]);
                    }

                }

                if (line.equals("ASK")) {
                    // read next line and do something in Query


                }
            }
        }
}
