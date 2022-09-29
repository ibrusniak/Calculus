
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        System.out.println(NE.valueOf(-2));
        System.out.println(NE.valueOf(+2));
        System.out.println(NE.valueOf(2));
        System.out.println(NE.valueOf(0));
        System.out.println(NE.valueOf(-0));
        System.out.println(NE.valueOf(+0));
        System.out.println(NE.valueOf(259999));

        System.out.println("***");

        System.out.println(NE.valueOf(-2d));
        System.out.println(NE.valueOf(+2.0));
        System.out.println(NE.valueOf(2.000));
        System.out.println(NE.valueOf(0d));
        System.out.println(NE.valueOf(-0d));
        System.out.println(NE.valueOf(+0d));
        System.out.println(NE.valueOf(259999.33434));
        System.out.println(NE.valueOf(-259999.33434));
        System.out.println(NE.valueOf(-233e-3));
    }
}

/**
 * NE - number entity - represents number entity with all its digits.
 */
class NE implements Comparable<NE> {

    private ArrayDeque<Integer> wholeNumberPart = new ArrayDeque<>();
    private ArrayDeque<Integer> decimalPart = new ArrayDeque<>();
    private boolean isPositive = true;
    private boolean isInteger = true;

    public NE push(int digit) {

        if (digit < 0 || digit > 9)
            throw new Error(String.format("Invalid argument '%d'", digit));
        
        (isInteger ? wholeNumberPart : decimalPart).addLast(digit);

        return this;
    }

    public NE backSpace() {
        
        if (!decimalPartIsEmpty()) {
            decimalPart.removeLast();
            if (decimalPartIsEmpty()) setIsInteger(true);
        } else {
            wholeNumberPart.removeLast();
        }
        
        return this;
    }

    public NE setIsInteger(boolean newValue) {
        
        isInteger = newValue;
        return this;
    }

    public boolean isInteger() {
        return isInteger;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public ArrayDeque<Integer> getWholeNumberPart() {
        return wholeNumberPart;
    }

    public ArrayDeque<Integer> getDecimalPart() {
        return decimalPart;
    }

    public NE setIsPositive(boolean newValue) {

        isPositive = newValue;
        return this;
    }

    public NE reset() {

        wholeNumberPart.clear();
        decimalPart.clear();
        isPositive = true;
        isInteger = true;
        return this;
    }

    public NE removeRedundandZeroes() {

        int tmp = 0;
        while (!wholeNumberPartIsEmpty() && (tmp = wholeNumberPart.getFirst()) == 0)
            wholeNumberPart.removeFirst();
        
        while (!decimalPartIsEmpty() && (tmp = decimalPart.getLast()) == 0)
            decimalPart.removeLast();

        return this;
    }

    @Override
    public String toString() {

        String w = wholeNumberPart.size() == 0 ? "" :
            wholeNumberPart.stream().map(x -> x.toString()).reduce((a, b) -> a + b).get();

        String d = decimalPart.size() == 0 ? "" :
            decimalPart.stream().map(x -> x.toString()).reduce((a, b) -> a + b).get();

        return String.format("{%s;%s;%s;%s;%s}",
            super.toString(),
            isPositive ? "positive" : "negative",
            isInteger ? "integer" : "floating point", w, d);
    }

    public boolean isZero() {
        return wholeNumberPartIsEmpty() && decimalPartIsEmpty();
    }

    public int compareTo(NE another) {
        return NEOps.compare(this, another);
    }

    public static NE valueOf(int i) {

        NE ne = new NE();

        ne.setIsPositive(i > 0);
        if (i != 0) {
            i *= i < 0 ? -1 : 1;
            for (String digit : Integer.valueOf(i).toString().split(""))
                ne.push(Integer.valueOf(digit));
        }
        return ne;
    }

    public static NE valueOf(double d) {

        NE ne = new NE();

        if (d - (int)d == 0) {
            ne = valueOf((int)d);
        } else if (d != 0d) {
            
        }

        return ne;
    }

    private boolean wholeNumberPartIsEmpty() {
        return wholeNumberPart.size() == 0;
    }

    private boolean decimalPartIsEmpty() {
        return decimalPart.size() == 0;
    }

    private boolean wholeNumberPartContainsOnlyZeroes() {
        return wholeNumberPart.stream().allMatch(x -> x == 0);
    }

    private boolean decimalPartContainsOnlyZeroes() {
        return decimalPart.stream().allMatch(x -> x == 0);
    }
}

class NEOps {

    public static int compare(NE f, NE s) {

        if (bothZeroes(f, s)) return 0;

        if (bothPositive(f, s)) {

        } else if (bothNegative(f, s)) {

        } else {

        }

        return 0;
    }

    private static int modCompare(NE f, NE s) {

        

        return 0;
    }

    private static boolean differentSign(NE f, NE s) {
        return ((f.isPositive() && !s.isPositive()) || (!f.isPositive() && s.isPositive()));
    }

    private static boolean sametSign(NE f, NE s) {
        return ((f.isPositive() && s.isPositive()) || (!f.isPositive() && !s.isPositive()));
    }

    private static boolean bothPositive(NE f, NE s) {
        return ((f.isPositive() && s.isPositive()));
    }

    private static boolean bothNegative(NE f, NE s) {
        return ((!f.isPositive() && !s.isPositive()));
    }

    private static boolean bothZeroes(NE f, NE s) {
        return ((f.isZero() && s.isZero()));
    }
}


