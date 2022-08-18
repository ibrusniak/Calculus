
import static utils.Console.delimiter;
import static utils.Console.print;
import static utils.Console.println;
import static utils.Console.emptyLine;
    
/**
 * Tests. For testing other classes
 */
public class Tests {

    private static final String USAGE = """
            USAGE: java Tests.java classname,
                where \"classname\" is the name of cocrete class you
                want to test. 
            """;

    private static final String NO_SUCH_CLASS = "No such class test";

    public static void main(String[] args) {
        if (args.length == 0) {
            emptyLine();
            println(USAGE);
            emptyLine();
            return;
        } else {
            switch (args[0]) {
                case "Stack":
                    testStackClass();
                    break;
            
                default:
                    println(String.format("%s (%s)\n%s", NO_SUCH_CLASS, args[0], USAGE));
                    break;
            }
        }
    }

    private static void testStackClass() {
    }
}

