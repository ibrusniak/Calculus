package utils;

/**
 * Console. Static methods for work with std in/out
 */
public class Console {

    private static String DELIMITER = "";
    private static final int DELIMITER_WIDTH = 80;
    private static final char DELIMITER_SYMBOL = '*';

    static {
        for (int i = 0; i < DELIMITER_WIDTH; i++) {
            DELIMITER += String.valueOf(DELIMITER_SYMBOL);
        }
    }

    public static <T> void print(T arg){
        System.out.print(arg);
    }

    public static <T> void println(T arg){
        System.out.println(arg);
    }

    public static void emptyLine() {
        System.out.println();
    }

    public static void delimiter() {
        emptyLine();
        println(DELIMITER);
    }
}

