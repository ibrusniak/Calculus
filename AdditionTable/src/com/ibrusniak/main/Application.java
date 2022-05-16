package com.ibrusniak.main;

public class Application {

    public static void main(String[] args) {

        System.out.println();

        for (int firstTerm = 1; firstTerm < 10; firstTerm++) {
            for (int secondTerm = 1; firstTerm + secondTerm <= 10; secondTerm++) {
                System.out.printf("%d + %d = %d\n", firstTerm, secondTerm, firstTerm + secondTerm);
            }
            System.out.println();
        }

        System.out.println();
    }
}

