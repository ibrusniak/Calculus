
import static utils.Console.delimiter;
import static utils.Console.print;
import static utils.Console.println;
import static utils.Console.emptyLine;
import static utils.ArrayUtils.arrayContainsValue;
    
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
            
                case "ArrayUtils":
                    testArrayUtils();
                    break;

                default:
                    println(String.format("%s (%s)\n%s", NO_SUCH_CLASS, args[0], USAGE));
                    break;
            }
        }
    }

    private static void testStackClass() {
        Stack<Integer> stack1 = new Stack<>();
        println(stack1);
        for (int i = 0; i < 7; i++) {
            stack1.push(i + 5);
        }
        println(stack1);
        println(stack1.makeACopy());
    }

    private static void testArrayUtils() {
        
        Integer[] array1 = new Integer[] { 2, 3, 4, 6 };
        println(arrayContainsValue(array1, 10));
        println(arrayContainsValue(array1, 2));
    }
}

