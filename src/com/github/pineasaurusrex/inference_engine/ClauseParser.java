package com.github.pineasaurusrex.inference_engine;
import java.util.*;

public class ClauseParser {

    private String ask;

    /**
     * Convert String to a clause
     * @return Clause
     */
    public static String parseSingle (String inputString) {

        //using ArrayDeque here since it does everything and is flexible. Could do a linkedList though..?
        ArrayDeque inputQueue = new ArrayDeque<String>();; //input queue. not sure if i really need this
        ArrayList outputQueue = new ArrayList<Character>();//output queue
        ArrayDeque operatorStack = new ArrayDeque<Character>(); //operator stack

        //remove spaces
        inputString = inputString.replaceAll("\\s+", "");

        String inputArray[] = inputString.split("(?<==>|&|~|\\/|<=>)|(?==>|&|~|\\/|<=>)");
        for (String inputElement: inputArray) {
            inputQueue.add(inputElement);
        }

        /*
        //testing output
        for (Iterator itr = inputQueue.iterator(); itr.hasNext();) {
            System.out.println("Test operand: " + itr.next());
        }*/


        //convert to rpn form
        //don't worry about parentheses, do later as part of research
        while (!inputQueue.isEmpty()) {
            //check if element is a operator
            if (isOperator((String)inputQueue.peekFirst())) {

                //add operator to stack if empty
                if (operatorStack.isEmpty()) {
                    operatorStack.addLast(inputQueue.pollFirst());

                //while there is an operator on the operatorStack with greater precedence than the current pop that one
                } else if (compare((String)inputQueue.peekFirst(), (String)operatorStack.peekLast()) <= 0) {
                    outputQueue.add(operatorStack.pollLast());
                    continue; //need to restart loop since may be operatorStack.size() > 1
                } else {
                    operatorStack.addLast(inputQueue.pollFirst());
                }
            //operands
            } else {
                outputQueue.add(inputQueue.pollFirst());
            }
        }

        //once inputQueue is empty need to finish draining the operators
        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pollLast());
        }


        //TODO: maybe convert proposition symbol to Horn form


        //System.out.println("Test line: " + inputString);
        return ("RPN form clause: " + outputQueue.toString());
    }


/*
    public static Clause parseMany (String inputString, String delimiter) {
        String[] inputArray = inputString.split(delimiter);

        for (int i=0; inputArray.length; i++) {
            parseSingle(inputArray[i]);
        }

        return //some clause
    }
*/



    private static final Map<String, Integer> OPERATOR = new HashMap<String, Integer>();
    static {
        OPERATOR.put("~", 1);    //negation for research
        OPERATOR.put("&", 2);  //inclusive disjunction /\
        OPERATOR.put("\\/", 3);  //disjunction for research
        OPERATOR.put("=>", 4);   //implies
        OPERATOR.put("<=>", 5);  //biconditional for research

    }

    public static int compare(String o1, String o2) {
        return (OPERATOR.get(o1) - OPERATOR.get(o2));
    }

    /**
     * Check if token is an operator
     * @return boolean
     */
    public static boolean isOperator(String token) {
        return OPERATOR.containsKey(token);
    }

}
