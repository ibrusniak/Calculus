package com.ibrusniak.main;

import java.util.Scanner;

public class Application {

    private final static String EXITCOMMAND = "/q";

    static {
        greeting();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            String s = "";
            do {
                s = scanner.nextLine();
                if (s.length() > 1 & !s.equals(EXITCOMMAND)) {
                    System.out.println("Invalid input!");
                }
            } while (!s.equals(EXITCOMMAND));
        } catch (Exception e) {
            System.out.print("\n\n" + e.getMessage() + "\n\n");
        } finally {
            scanner.close();
        }
    }

    private static void greeting() {
        String g = """

                Calculator.
                Enter number (digit by digit), then "+", "-", "*", "/" and then "="

                Enter "/q" to exit

                """;
        System.out.println(g);
    }
}


