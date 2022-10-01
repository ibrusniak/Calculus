
import java.util.ArrayDeque;
import java.util.Optional;

public class Test {

    public static void main(String[] args) {
        
        NumberEntity ne = new NumberEntity();

        ne
            .push(0)
            .push(0)
            .push(0)
            .push(0)
            .push(0)
            .push(2)
            .push(0)
            .push(0)
            .push(2)
            .push(0)
            .push(0)
            .push(0)
            .push(3)
            .push(0)
            .push(0)
            .push(0)
            .push(0);
        
        System.out.println(ne);

        ne.resetIntegerFlag();

        ne
            .push(0)
            .push(0)
            .push(0)
            .push(0)
            .push(0)
            .push(0)
            .push(3)
            .push(0)
            .push(0)
            .push(0)
            .push(0)
            .push(0)
            .push(0)
            .push(0)
            .push(0)
            .push(4)
            .push(0)
            .push(0)
            .push(0)
            .push(0)
            .push(0)
            .push(0)
            .push(0);

        System.out.println(ne);
        System.out.println(ne.toNormalizedString());
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

        Optional o1 = intPart.stream().map(x -> String.valueOf(x)).reduce((x, y) -> x + y);
        Optional o2 = decPart.stream().map(x -> String.valueOf(x)).reduce((x, y) -> x + y);
        return String.format("{%s;%s;%s}", positive ? "+" : "-",
            o1.isEmpty() ? "" : o1.get(),
            o2.isEmpty() ? "" : o2.get());
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

    public String toNormalizedString() {
        return clone().removeRedundandZeroes().toString();
    }

    public NumberEntity removeRedundandZeroes() {

        while (intPart.size() > 1 && intPart.getFirst() == 0)
            intPart.removeFirst();
        while (decPart.size() > 1 && decPart.getLast() == 0)
            decPart.removeLast();
        return this;
    }
}

