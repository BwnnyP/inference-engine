package com.github.pineasaurusrex.inference_engine;

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
}
