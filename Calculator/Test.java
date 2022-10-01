
import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Iterator;

public class Test {

    public static void main(String[] args) {

        NumberEntity ne = NumberEntity.from("156.255");

        System.out.println(ne);
        System.out.println(ne.toDebugString());
    }
}

class NumberEntity implements Cloneable {

    private ArrayDeque<Byte> intPart = new ArrayDeque<>();
    private ArrayDeque<Byte> decPart = new ArrayDeque<>();
    private boolean integer = true;
    private boolean positive = true;

    public NumberEntity push(int digit) {

        if (digit > 9 || digit < 0)
            throw new Error(String.format("Illegal argument '%d' for 'pushDigit' function!", digit));
        (integer ? intPart : decPart).addLast((byte)digit);
        return this;
    }

    public NumberEntity backSpace() {
        
        (integer ? intPart : decPart).removeLast();
        return this;
    }

    public NumberEntity reset() {
        
        intPart.clear();
        decPart.clear();
        integer = true;
        positive = true;
        return this;
    }

    public NumberEntity setIntegerFlag() {

        integer = true;
        decPart.clear();
        return this;
    }

    public NumberEntity resetIntegerFlag() {

        integer = false;
        return this;
    }

    public NumberEntity setPositiveFlag() {
        
        positive = true;
        return this;
    }

    public NumberEntity resetPositiveFlag() {
        
        positive = false;
        return this;
    }

    @Override
    public String toString() {

        NumberEntity clone = clone();
        clone.removeRedundandZeroes();
        return
            (positive ? "" : "-")
            + (clone.intPartAsString().isEmpty() ? "0" : clone.intPartAsString())
            + (clone.decPartAsString().isEmpty() ? "" : "." + clone.decPartAsString());
    }

    public String toDebugString() {

        return String.format("{%s;%s;%s}", positive ? "+" : "-",
            intPartAsString(),
            decPartAsString());
    }

    @Override
    public NumberEntity clone() {
        
        NumberEntity ne = new NumberEntity();
        ne.positive = positive;
        for (byte b : intPart)
            ne.push(b);
        if (!integer) {
            ne.integer = integer;
            for (byte b : decPart)
                ne.push(b);
        }
        return ne;
    }
    
    public NumberEntity normalize() {
        return removeRedundandZeroes();
    }

    public static NumberEntity from(int i) throws IllegalArgumentException {
        return from(String.valueOf(i));
    }

    public static NumberEntity from(double i) throws IllegalArgumentException {
        
        if (i == Double.NaN || i == Double.POSITIVE_INFINITY || i == Double.NEGATIVE_INFINITY)
            throw new IllegalArgumentException(String.valueOf(i));
        return from(String.format("%01254.632f", i));
    }

    public static NumberEntity from(String s) throws IllegalArgumentException {

        if (!s.matches("(^[-+]{0,1}\\d*\\.\\d*$)|(^[-+]{0,1}\\d*$)") || s.matches("(^0+\\.0+$)|(^0+$)"))
            throw new IllegalArgumentException(s);
        NumberEntity ne = new NumberEntity();
        if (s.contains("-")) ne.resetPositiveFlag();
        s = s.replaceAll("[+-]", "");
        if (s.contains(".")) {
            for (String st : s.split("\\.")[0].split(""))
                ne.push(Byte.valueOf(st));
            ne.resetIntegerFlag();
            for (String st : s.split("\\.")[1].split(""))
                ne.push(Byte.valueOf(st));
        } else {
            for (String st : s.split(""))
                ne.push(Byte.valueOf(st));
        }
        return ne.removeRedundandZeroes();
    }

    private NumberEntity removeRedundandZeroes() {

        while (intPart.size() > 0 && intPart.getFirst() == 0)
            intPart.removeFirst();
        while (decPart.size() > 0 && decPart.getLast() == 0)
            decPart.removeLast();
        if (decPart.isEmpty())
            integer = true;
        return this;
    }

    private String intPartAsString() {

        Optional<String> o = intPart.stream().map(x -> String.valueOf(x)).reduce((x, y) -> x + y);
        return o.isEmpty() ? "" : o.get();
    }

    private String decPartAsString() {

        Optional<String> o = decPart.stream().map(x -> String.valueOf(x)).reduce((x, y) -> x + y);
        return o.isEmpty() ? "" : o.get();
    }
}

