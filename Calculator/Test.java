
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        BigRegister b0 = new BigRegister();

        b0.push(2).push(3).push(5);

        List<Integer> d = Arrays.asList(new Integer[] {2, 5, 6, 2, 4, 6, 7});
        d.forEach(x -> b0.push(x));
        
        b0.setResetIntegerFlag();
        d = Arrays.asList(new Integer[] {0, 0, 0, 2, 5, 6, 2, 4, 6, 7, 0, 0, 0, 0});
        d.forEach(x -> b0.push(x));
        System.out.println(b0);
        b0.removeRedundandZeroes();
        System.out.println(b0);
    }
}

class BigRegister implements Comparable<BigRegister> {

    private ArrayDeque<Byte> wholeNumberPart = new ArrayDeque<>();
    private ArrayDeque<Byte> decimalPart = new ArrayDeque<>();
    private boolean positive = true;
    private boolean integer = true;

    public BigRegister push(int digit) {

        if (digit < 0 || digit > 9)
            throw new Error(String.format("Invalid argument '%d' for 'pushDigit' function", digit));
        
        (integer ? wholeNumberPart : decimalPart).addLast((byte)digit);

        return this;
    }

    public void setResetIntegerFlag() {
        integer = !integer;
    }

    public void reset() {

        wholeNumberPart.clear();
        decimalPart.clear();
        positive = true;
        integer = true;
    }

    @Override
    public String toString() {

        String res = "0";

        if (isEmpty()) return res;

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

    public boolean isEmpty() {
        return wholeNumberPartIsEmpty() && decimalPartIsEmpty();
    }

    public int compareTo(BigRegister r) {
        
        if (isEmpty() && r.isEmpty()) return 0;
        if (!isEmpty() && r.isEmpty()) return 1;
        if (isEmpty() && !r.isEmpty()) return -1;

        // TODO: code here the case when both are not empty!
        return 0;
    }

    public void removeRedundandZeroes() {

        byte tmp = 0;
        while (!wholeNumberPartIsEmpty() && (tmp = wholeNumberPart.getFirst()) == 0)
            wholeNumberPart.removeFirst();
        
        while (!decimalPartIsEmpty() && (tmp = decimalPart.getLast()) == 0)
            decimalPart.removeLast();
    }

    private boolean wholeNumberPartIsEmpty() {
        return wholeNumberPart.size() == 0 || wholeNumberPart.stream().allMatch(x -> x == 0);
    }

    private boolean decimalPartIsEmpty() {
        return decimalPart.size() == 0 || decimalPart.stream().allMatch(x -> x == 0);
    }
}


