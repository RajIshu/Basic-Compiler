package com.Compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
	    tokenizer("(7+8)");

    }

    public static void tokenizer(String input){
        int cursor = 0;

        char[] inputChar = input.toCharArray();

        TreeMap<String, ArrayList<Character>> code = new TreeMap<>();

        ArrayList<Character> ch = new ArrayList<>();
        ArrayList<Character> intChar = new ArrayList<>();
        ArrayList<Character> alphaChar = new ArrayList<>();
        ArrayList<Character> opChar = new ArrayList<>();

        while(cursor < input.length()){
            Character c = inputChar[cursor];

            if(c.equals('(')){
                ch.add(c);
                code.put("paren", ch);
//                System.out.println(c);
                cursor++;
            }

            if(c.equals(')')){
                ch.add(c);
                code.put("paren", ch);
                cursor++;
            }

            Character[] numbers = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

            if((Arrays.stream(numbers).toList()).contains(c)){
//                System.out.println("Yes here it is = " + c);
                intChar.add(c);
                code.put("number", intChar);
                cursor++;
            }

            Character[] alphabets = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

            if((Arrays.stream(alphabets).toList()).contains(c)){
                alphaChar.add(c);
                code.put("variable", alphaChar);
                cursor++;
            }

            Character[] operators = new Character[]{'+', '-', '*', '/'};

            if((Arrays.stream(operators).toList()).contains(c)){
                opChar.add(c);
                code.put("operator", opChar);
                cursor++;
            }
        }

        System.out.println(code);

    }
}
