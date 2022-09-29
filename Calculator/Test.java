
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        NE ne = new NE();
        ne.push(0);
        ne.push(0);
        ne.push(0);
        ne.push(4);
        ne.setIsInteger(false);
        ne.push(0);
        ne.push(0);
        ne.push(4);
        System.out.println(ne);        
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
            throw new Error(String.format("Invalid argument '%d' for 'pushDigit' function", digit));
        
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

        byte tmp = 0;
        while (!wholeNumberPartIsEmpty() && (tmp = wholeNumberPart.getFirst()) == 0)
            wholeNumberPart.removeFirst();
        
        while (!decimalPartIsEmpty() && (tmp = decimalPart.getLast()) == 0)
            decimalPart.removeLast();

        return this;
    }

    @Override
    public String toString() {

        String res = "0";

        if (isZero()) return res;

        if (!wholeNumberPartIsEmpty()) {
            res = "";
            String[] tmp = new String[]{res};
            wholeNumberPart.forEach(x -> tmp[0] += String.valueOf(x));
            res = tmp[0];
        }

        if (!decimalPartIsEmpty()) {
            res += ".";
            String[] tmp = new String[]{res};
            decimalPart.forEach(x -> tmp[0] += String.valueOf(x));
            res = tmp[0];
        }

        return res;
    }

    public boolean isZero() {
        return wholeNumberPartIsEmpty() && decimalPartIsEmpty();
    }

    public int compareTo(NE another) {
        return NEOps.compare(this, another);
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
    }

    private static int modCompare(NE f, NE s) {

        HashMap<String, Object> fState = f.get

        return 0;
    }

    private static boolean differentSign(NE f, NE s) {
        return ((f.isPositive() && !s.isPositive()) || (!f.isPositive() && s.isPositive()));
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


