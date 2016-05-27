package com.github.pineasaurusrex.inference_engine;
import java.util.*;

public class ClauseParser {

    private String ask;

    /**
     * Convert String to a clause
     * Currently only working for ImplicationClause!!!
     * @return Clause
     */
    public static ArrayDeque<String> parseSingle (String inputString) {

        //using ArrayDeque here since it does everything and is flexible. Could do a linkedList though..?
        ArrayDeque inputQueue = new ArrayDeque<String>();; //input queue. not sure if i really need this
        ArrayDeque outputQueue = new ArrayDeque<String>();//output queue
        ArrayDeque operatorStack = new ArrayDeque<String>(); //operator stack

        //remove spaces
        inputString = inputString.replaceAll("\\s+", "");

        String inputArray[] = inputString.split("(?<==>|&|~|\\/|<=>)|(?==>|&|~|\\/|<=>)"); //TODO build this regex dynamically from Connective enum
        for (String inputElement: inputArray) {
            inputQueue.add(inputElement);
        }

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

        return (outputQueue);
    }

    public static PropositionalSymbol clauseToPropositionalSymbol (String inputClause) {
        return (new PropositionalSymbol(inputClause));
    }

    public static List<PropositionalSymbol> clauseToPropositionalSymbol (String[] inputClause) {
        List<PropositionalSymbol> sentence = new LinkedList<PropositionalSymbol>();
        for (String element:inputClause) {
            sentence.add(new PropositionalSymbol(element));
        }
        return sentence;
    }

    public static int compare(String o1, String o2) {
        return (Connective.getValueFromSymbol(o1).getPrecedence() - Connective.getValueFromSymbol(o2).getPrecedence());
    }

    /**
     * Check if token is an operator
     * @return boolean
     */
    public static boolean isOperator(String token) {
        if (Connective.getValueFromSymbol(token) == null) {
            return false;
        } else {
            return true;
        }
    }

}
