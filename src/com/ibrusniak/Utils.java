package com.ibrusniak;

public class Utils {

    public static void println() { System.out.println(); }
    public static <T> void println(T t) { System.out.println(t); }
    public static <T> void println(T... args) {
        for (T x : args)
            System.out.println(x);
    }
    public static void printf(String pattern, Object... args) { System.out.printf(pattern, args); }
}