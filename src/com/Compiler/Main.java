package com.Compiler;

import java.util.*;

public class Main {

    public static TreeMap<String, ArrayList<Character>> code = new TreeMap<>();


    public static void main(String[] args) {
        System.out.println("Tokenization:\n============\n");
        tokenizer("(7+8)");
	    tokenizer("(7+8)*9");
        tokenizer("10/(7+8)*9");
        System.out.println("==========================================================\n\n\n");

        System.out.println("Parsing:\n=======\n");
        parser("(7+8)");
        parser("+78"); // Gives ERROR
        parser("(7+8)*9");
        parser("10/(7+8)*9");

    }

    public static void parser(String input){
        try{

            char[] inputChar = input.toCharArray();

            Stack<String> stacks = new Stack<>();

            Character[] operators = new Character[]{'+', '-', '*', '/'};
            Character[] numbers = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

            ArrayList<Character> store = new ArrayList<>();
            int storeCursor = 0;

            int checker = 0;
            for(int i = 0; i < input.length(); i++){

                if((Arrays.stream(operators).toList()).contains(inputChar[i])){
                    store.add(inputChar[i]);
                    storeCursor++;
                }

                else if(inputChar[i] == '('){
                    stacks.push(Character.toString(inputChar[i]));
                    if (i == 0) {
                    }
                    else if ((Arrays.stream(operators).toList()).contains(inputChar[i-1])) {
                        checker++;
                    }

                }

                else if(inputChar[i] == ')'){
                    stacks.push(Character.toString(inputChar[i]));
                    if(i == inputChar.length - 1){
                    }
                    else if ((Arrays.stream(operators).toList()).contains(inputChar[i+1]) ){
                        checker++;
                    }

                    if(checker != 0 && checker % 2 == 0 && !store.isEmpty() ){
                        stacks.push(Character.toString(store.get(storeCursor-1)));
                        store.remove(storeCursor-1);
                        storeCursor--;
                        checker--;
                    }
                }
                else{
                    if(i == inputChar.length - 1){
                        stacks.push(Character.toString(inputChar[i]));
                    }
                    else if((Arrays.stream(numbers).toList()).contains(inputChar[i+1])){
                        while((Arrays.stream(numbers).toList()).contains(inputChar[i+1])) {
    //                        System.out.println("inputchar[i] = " + (10 * inputChar[i]));
    //                        System.out.println("inputchar[i+1] = " + (inputChar[i+1]));
                            String insert = inputChar[i] + Character.toString(inputChar[i+1]);
    //                        System.out.println(insert);
                            stacks.push(insert);
                            i++;
                        }
                        checker++;
                    }
                    else {
                        stacks.push(Character.toString(inputChar[i]));
                        checker++;
                    }


                    if(checker != 0 && checker % 2 == 0){
                        stacks.push(Character.toString(store.get(storeCursor-1)));
                        store.remove(storeCursor-1);
                        storeCursor--;
                        checker--;
                    }
                }

            }
            if(!store.isEmpty()){
                stacks.push(Character.toString(store.get(storeCursor-1)));
            }


            System.out.println("\nAbstract Syntax Tree for '" + input + "': ");
            System.out.println("{\n" +
                    "\ttype: 'Program',\n" +
                    "\tbody: [{\n");

            Iterator<String> stackIterator = stacks.iterator();

            String tab = "\t\t";

            while(stackIterator.hasNext()){
                String s = stackIterator.next();

                if(checkParam(s.charAt(0)).equals("number")){
                    System.out.println(tab + "type: '" + checkParam(s.charAt(0)) + "'" + "\n" + tab + "Value: '" + s + "'");
                }
                else if(checkParam(s.charAt(0)).equals("operator")){
                    System.out.print(tab + "type: '" + checkParam(s.charAt(0)) + "'" + "\n" + tab);
                    switch (s) {
                        case "+" -> s = "add";
                        case "-" -> s = "subtract";
                        case "*" -> s = "multiply";
                        case "/" -> s = "divide";
                    }
                    System.out.println("Operation: '" + s + "'");
                }
                else if(checkParam(s.charAt(0)).equals("paren") && s.charAt(0) == '('){
                    System.out.println(tab + "{\n");
                    tab = tab + "\t";
                }
                else if(checkParam(s.charAt(0)).equals("paren") && s.charAt(0) == ')'){
                    tab = tab.substring(1);
                    System.out.println(tab + "}\n");
                }

            }
            tab = tab.substring(1);
            System.out.println(tab + "}]\n\b}");

        }
        catch (Exception e){
            System.out.println("\n ------------------ ");
            System.out.println("| ERROR DETECTED!! |");
            System.out.println(" ------------------ \n");
        }
    }


    public static String checkParam(Character value){
        Character[] numbers = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        Character[] alphabets = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        Character[] operators = new Character[]{'+', '-', '*', '/'};

        if(value.equals('(') || value.equals(')')){
            return "paren";
        }
        else if((Arrays.stream(numbers).toList()).contains(value)){
            return "number";
        }
        else if((Arrays.stream(alphabets).toList()).contains(value)){
            return "literal";
        }
        else if((Arrays.stream(operators).toList()).contains(value)){
            return "operator";
        }
        else{
            return "other";
        }
    }

    public static void tokenizer(String input){
        int cursor = 0;

        char[] inputChar = input.toCharArray();

        ArrayList<Character> ch = new ArrayList<>();
        ArrayList<Character> intChar = new ArrayList<>();
        ArrayList<Character> alphaChar = new ArrayList<>();
        ArrayList<Character> opChar = new ArrayList<>();

        while(cursor < input.length()){
            Character c = inputChar[cursor];

            if(checkParam(c).equals("paren")){
                ch.add(c);
                code.put("paren", ch);
//                System.out.println(c);
                cursor++;
            }

            if(checkParam(c).equals("number")){
//                System.out.println("Yes here it is = " + c);
                intChar.add(c);
                code.put("number", intChar);
                cursor++;
            }

            if(checkParam(c).equals("literal")){
                alphaChar.add(c);
                code.put("variable", alphaChar);
                cursor++;
            }

            if(checkParam(c).equals("operator")){
                opChar.add(c);
                code.put("operator", opChar);
                cursor++;
            }

            if(checkParam(c).equals("other")){
                opChar.add(c);
                code.put("other", opChar);
                cursor++;
            }
        }
        System.out.println(code);

    }
}
