
import java.util.ArrayDeque;
import java.util.Optional;

public class Test {

    public static void main(String[] args) {

        NumberEntity a = NumberEntity.from(10);
        NumberEntity b = NumberEntity.from(18);

        System.out.println(a);
        System.out.println(b);

        NumberEntity sum = NumberEntity.addModulo(a, b);
        System.out.println(sum);
    }
}

class NumberEntity implements Cloneable, Comparable<NumberEntity> {

    private ArrayDeque<Byte> intPart = new ArrayDeque<>();
    private ArrayDeque<Byte> decPart = new ArrayDeque<>();
    private boolean integer = true;
    private boolean positive = true;

    private NumberEntity() {}

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

    public boolean getIntegerFlag() {
        return integer;
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

    public String toStringWithPaddingZeroes() {

        return
            (positive ? "" : "-")
            + (intPartAsString().isEmpty() ? "0" : intPartAsString())
            + (decPartAsString().isEmpty() ? "" : "." + decPartAsString());
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

    public boolean isZero() {

        return (intPart.isEmpty() && decPart.isEmpty())
            || (intPart.stream().allMatch(x -> x.equals("0")) && decPart.stream().allMatch(x -> x.equals("0")))
            || (intPart.isEmpty() && decPart.stream().allMatch(x -> x.equals("0")))
            || (intPart.stream().allMatch(x -> x.equals("0")) && decPart.isEmpty());
    }

    public int compareTo(NumberEntity another) {

        int result = 0;
        if (positive != another.positive) {
            result = positive ? 1 : -1;
        } else if (positive && another.positive) {
            result = compareToModulo(this, another);
        } else {
            result = -1 * compareToModulo(this, another);
        }
        return result;
    }

    public static NumberEntity addModulo(final NumberEntity first, final NumberEntity second) {

        NumberEntity a = first.clone();
        NumberEntity b = second.clone();
        padWithZeroesToWidthPair(a, b);
        int intDigitCount = a.intPart.size();
        int decDigitCount = a.decPart.size();
        ArrayDeque<Byte> ds1 = a.getDigitSequence();
        ArrayDeque<Byte> ds2 = b.getDigitSequence();
        ArrayDeque<Byte> digitSequenceResult = sumDS(ds1, ds2);

        int resultSize = digitSequenceResult.size();
        NumberEntity result = new NumberEntity();
        for (int i = 0; i < resultSize; i++) {
            if (i == resultSize - decDigitCount) result.resetIntegerFlag();
            result.push(digitSequenceResult.pollFirst());
        }
        return result;
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

        if (s == "" || !s.matches("(^[-+]{0,1}\\d*\\.\\d*$)|(^[-+]{0,1}\\d*$)"))
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

    private NumberEntity padWithZeroesToWidth(int intPartWidth, int decPartWidth) throws IllegalArgumentException {

        if (intPartWidth < intPart.size())
            throw new IllegalArgumentException("Argument 'intPartWidth' is to small!");
        if (decPartWidth < decPart.size())
            throw new IllegalArgumentException("Argument 'decPartWidth' is to small!");
        for (int i = intPart.size(); i < intPartWidth; i++)
            intPart.addFirst((byte)0);
        for (int i = decPart.size(); i < decPartWidth; i++)
            decPart.addLast((byte)0);
        return this;
    }

    private int compareToModulo(NumberEntity first, NumberEntity second) {

        NumberEntity a = first.clone();
        NumberEntity b = second.clone();
        padWithZeroesToWidthPair(a, b);
        while (a.intPart.size() > 0) {
            byte b1 = a.intPart.pollFirst();
            byte b2 = b.intPart.pollFirst();
            if (b1 > b2) return 1;
            if (b1 < b2) return -1;
        }
        while (a.decPart.size() > 0) {
            byte b1 = a.decPart.pollFirst();
            byte b2 = b.decPart.pollFirst();
            if (b1 > b2) return 1;
            if (b1 < b2) return -1;
        }
        return 0;
    }

    private ArrayDeque<Byte> getDigitSequence() {

        ArrayDeque<Byte> ds = new ArrayDeque<>();
        for (Byte b : intPart)
            ds.addLast(b);
        for (Byte b : decPart)
            ds.addLast(b);
        return ds;
    }

    private static void padWithZeroesToWidthPair(NumberEntity a, NumberEntity b) {

        a.removeRedundandZeroes().padWithZeroesToWidth(Integer.max(a.intPart.size(), b.intPart.size()),
            Integer.max(a.decPart.size(), b.decPart.size()));
        b.removeRedundandZeroes().padWithZeroesToWidth(Integer.max(a.intPart.size(), b.intPart.size()),
            Integer.max(a.decPart.size(), b.decPart.size()));
    }

    private static ArrayDeque<Byte> sumDS(ArrayDeque<Byte> digitSequenceA, ArrayDeque<Byte> digitSequenceB) {

        ArrayDeque<Byte> digitSequenceACopy = copyDS(digitSequenceA);
        ArrayDeque<Byte> digitSequenceBCopy = copyDS(digitSequenceB);
        ArrayDeque<Byte> digitSequenceResult = new ArrayDeque<>();
        boolean overflow = false;
        while (digitSequenceACopy.size() > 0) {
            byte locResult = (byte)(digitSequenceACopy.pollLast() + digitSequenceBCopy.pollLast() + (overflow ? 1 : 0));
            if (locResult > 9) {
                locResult -= 10;
                overflow = true;
            } else {
                overflow = false;
            }
            digitSequenceResult.push(locResult);
        }
        if (overflow) digitSequenceResult.push((byte)1);
        return digitSequenceResult;
    }

    private static ArrayDeque<Byte> subDS(ArrayDeque<Byte> digitSequenceA, ArrayDeque<Byte> digitSequenceB)
                                            throws IllegalArgumentException {

        if ((digitSequenceA.size() < digitSequenceB.size())
                || ((digitSequenceA.size() == digitSequenceB.size())
                    && (digitSequenceA.getFirst() < digitSequenceB.getFirst())))
            throw new IllegalArgumentException();

        ArrayDeque<Byte> digitSequenceACopy = copyDS(digitSequenceA);
        ArrayDeque<Byte> digitSequenceBCopy = copyDS(digitSequenceB);
        
        ArrayDeque<Byte> digitSequenceResult = new ArrayDeque<>();
        return digitSequenceResult;
    }

    private static ArrayDeque<Byte> copyDS(ArrayDeque<Byte> original) {
        return new ArrayDeque<>(original);
    }
}

