
import java.util.ArrayDeque;

public class Test {

    public static void main(String[] args) {
        
        NumberEntity ne = new NumberEntity();

        ne.push(2)
            .push(2)
            .push(3);
        
        System.out.println(ne);
        ne.reset();
        System.out.println(ne);
        ne.push(0).push(0);
        System.out.println(ne);

    }
}

class NumberEntity {

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

    @Override
    public String toString() {

        return (intPart.size() == 0 ? "0" : intPart.stream()
            .map(x -> String.valueOf(x)).reduce((x, y) -> x + y).get())
            + "." 
            + (decPart.size() == 0 ? "0" : decPart.stream()
                .map(x -> String.valueOf(x)).reduce((x, y) -> x + y).get());
    }
}

