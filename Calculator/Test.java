
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        BigRegister b0 = new BigRegister();

        b0.push(2).push(3).push(5);

        List<Integer> d = Arrays.asList(new Integer[] {2, 5, 6, 2, 4, 6, 7});
        d.forEach(x -> b0.push(x));
        
        System.out.println(b0);
        b0.removeRedundandZeroes();
        System.out.println(b0);
    }
}

class Calculator {

    public static enum Key {

        ZERO, ONE, TWO, THREE,
        FOUR, FIVE, SIX, SEVEN,
        EIGHT, NINE,

        ADDITION, SUBSTRATION, MULTIPLICATION, DIVISION,

        DECIMAL_DOT, EQUALS, NEGATIVE_NUMBER,
        CLEAR_ALL, OFF, BACKSPACE;
    }

    private static ArrayList<Key> digitKeys = new ArrayList<>();
    private static ArrayList<Key> operationKeys = new ArrayList<>();
    private static HashMap<Key, Character> KeyToCharMapping
        = new HashMap<>();
    
    private static enum State {

        INPUT,
        RESULT,
        ERROR
    }

    static {

        Key[] arr = Key.values();
        for (int i = 0; i <= 9; i++) {
            digitKeys.add(arr[i]);
        }

        Collections.addAll(operationKeys, Key.ADDITION,
                Key.SUBSTRATION, Key.MULTIPLICATION, Key.DIVISION);
        
        KeyToCharMapping.put(Key.ZERO, '0');
        KeyToCharMapping.put(Key.ONE, '1');
        KeyToCharMapping.put(Key.TWO, '2');
        KeyToCharMapping.put(Key.THREE, '3');
        KeyToCharMapping.put(Key.FOUR, '4');
        KeyToCharMapping.put(Key.FIVE, '5');
        KeyToCharMapping.put(Key.SIX, '6');
        KeyToCharMapping.put(Key.SEVEN, '7');
        KeyToCharMapping.put(Key.EIGHT, '8');
        KeyToCharMapping.put(Key.NINE, '9');
    }

    private Key operation = null;
    private Register screen;
    private Register additionalRegister;
    private State state = State.INPUT;

    public Calculator() {

        additionalRegister = new Register(12);
        screen = new Register(12);
    }

    public Calculator(int screenCapacity) {

        additionalRegister = new Register(screenCapacity);
        screen = new Register(screenCapacity);
    }

    public void keyPressed(Key key) {

        if (key == null) {
            return;
        }

        if (digitKeys.contains(key)) {
            digitKeyPressed(key);
        } else if (operationKeys.contains(key)) {
            operationKeyPressed(key);
        } else {

            if (key == Key.OFF) {
                System.exit(0);
            } else if (key == Key.CLEAR_ALL) {
                reset();
            } else if (key == Key.NEGATIVE_NUMBER) {
                screen.setResetNegative();
            } else if (key == Key.DECIMAL_DOT) {
                screen.addDigit('.');
            } else if (key == Key.EQUALS) {
                equalsPressed();
            } else if (key == Key.BACKSPACE) {
                screen.backSpace();
            }
        }
    }

    @Override
    public String toString() {

        return
            String.format(" Operation: %s\n state: %s\n additional register: %s\n screen: %s\n",
                operation, state, additionalRegister, screen);
    }

    private void reset() {

        operation = null;
        additionalRegister.reset();
        screen.reset();
        state = State.INPUT;
    }

    private void digitKeyPressed(Key key) {
        
        if (state == State.RESULT) {
            screen.reset();
            state = State.INPUT;
        }
        screen.addDigit(KeyToCharMapping.get(key));
    }

    private void operationKeyPressed(Key key) {

        if (state == State.INPUT) {
            if (operation == null) {
                screen.copyTo(additionalRegister);
            } else {
                applyOperation();
                screen.copyTo(additionalRegister);
            }
        } else {
            screen.copyTo(additionalRegister);
        }
        operation = key;
        state = State.RESULT;
    }
    
    private void equalsPressed() {
        
        if (operation != null && state != State.RESULT) {
            applyOperation();
            state = State.RESULT;
        }
    }

    private void applyOperation() {

        switch (operation) {

            case ADDITION:
                additionalRegister.add(screen).copyTo(screen);
                break;
                case SUBSTRATION:
                additionalRegister.minus(screen).copyTo(screen);
                break;
                case MULTIPLICATION:
                additionalRegister.multiply(screen).copyTo(screen);
                break;
                case DIVISION:
                additionalRegister.divide(screen).copyTo(screen);
                break;
            default:
                break;
        }
    }
}

class Register implements Comparable<Register> {
    
    private ArrayDeque<Character> elementData;
    private boolean negative = false;
    private boolean dotPresent = false;
    private int registerCapacity;

    private List<Character> allowedCharacters = Arrays.asList(new Character[] {
            '.', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
    });

    public Register() {

        registerCapacity = 12;
        elementData = new ArrayDeque<>(registerCapacity);
        reset();
    }

    public Register(int capacity) {

        registerCapacity = capacity < 2 ? 12 : capacity;
        elementData = new ArrayDeque<>(registerCapacity);
        reset();
    }

    public void reset() {

        elementData.clear();
        elementData.add('0');
        negative = false;
        dotPresent = false;
    }

    public void backSpace() {

        if (elementData.size() == 1) {
            elementData.clear();
            elementData.add('0');
            return;
        }

        elementData.removeLast();

        if (elementData.getLast() == '.') {
            elementData.removeLast();
        }
        if (!elementData.contains('.')) {
            dotPresent = false;
        }
    }

    public void addDigit(Character ch) {

        if (!allowedCharacters.contains(ch) || elementData.size() == registerCapacity) {
            return;
        }
        if (isBlank()) {

            switch (ch) {
                case '0':
                    return;
                case '.':
                    elementData.add(ch);
                    return;
                default:
                    elementData.clear();
                    elementData.add(ch);
                    return;
            }
        } else {
            
            if (ch.equals('.') & elementData.contains('.')) {
                return;
            }
            elementData.add(ch);
        }
        if (elementData.contains('.')) {
            dotPresent = true;
        }
    }

    /**
     * isBlank. 'Blank screen' means it has only one digit - '0'
     * @return - boolean
     */
    public boolean isBlank() {

        return
            elementData.size() == 1
                && elementData.getLast() == '0';
    }

    public void setResetNegative() {
        negative = !negative;
    }

    public void copyTo(Register target) {

        target.reset();

        if (negative) {
            target.setResetNegative();
        }
        for (Character ch : elementData) {
            target.addDigit(ch);
        }
    }

    public int getRegisterCapacity() {
        return registerCapacity;
    }

    public int getSize() {
        return elementData.size();
    }

    public Register add(Register to) {
        return doubleToRegister(toDouble() + to.toDouble());
    }
    
    public Register minus(Register what) {
        return doubleToRegister(toDouble() - what.toDouble());
    }

    public Register multiply(Register by) {
        return doubleToRegister(toDouble() * by.toDouble());
    }

    public Register divide(Register by) {
        return doubleToRegister(toDouble() / by.toDouble());
    }

    public boolean isDotPresent() {
        return dotPresent;
    }

    public boolean isNegative() {
        return negative;
    }

    @Override
    public String toString() {

        String s = "";
        for (Character c : elementData) {
            s += String.valueOf(c);
        }
        if (negative) {
            s = "-" + s;
        }
        return s;
    }

    @Override
    public int compareTo(Register another) {
        return Double.compare(toDouble(),
            another.toDouble());
    }

    private double toDouble() {

        String s = "";
        for (Character c : elementData) {
            s += String.valueOf(c);
        }
        double d = Double.parseDouble(s);
        if (isNegative()) {
            d *= -1;
        }
        return d;
    }

    private Register doubleToRegister(double d) {

        String s = doubleToPlainString(d);

        if ((d - (int)d) == 0) {
            s = s.substring(0, s.indexOf("."));
        }

        char[] charArray = s.toCharArray();

        Register result = new Register(getRegisterCapacity());

        for (int i = 0; i < charArray.length; i++) {
            result.addDigit(Character.valueOf(charArray[i]));
        }
        if (d < 0) {
            result.setResetNegative();
        }

        return result;
    }

    private String doubleToPlainString(double d) {

        String format
            = "%0" + getRegisterCapacity() * 2
                + "." + getRegisterCapacity() + "f";

        String str = String.format(format, d);

        System.out.printf("{%s}{%s}{%s}%n",
            d, format, str);



        return str;
    }

    // private String removeRedundantZeroes(String s) {

    //     String result = "";

    //     if (s.indexOf('.') != -1) {

    //     } else {

    //     }
    // }
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


