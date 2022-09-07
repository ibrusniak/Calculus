
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        Logger logger = Logger.getAnonymousLogger();
        logger.setLevel(Level.ALL);

        logger.info("Started...");

        Calculator calculator = new Calculator();

        HashMap<Integer, Calculator.Key> keyMapping = getKeysMapping();

        JFrame applicationWindow = new JFrame("Calculator class tester (ESC - to exit)");

        applicationWindow.setSize(640, 200);
        applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea calculatorToString = new JTextArea();
        calculatorToString.setEditable(false);
        applicationWindow.add(calculatorToString);
        calculatorToString.setText(calculator.toString());

        calculatorToString.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {

                int keyKode = e.getKeyCode();

                logger.info(String.format("Pressed: code: %s, Key: %s",
                        keyKode, keyMapping.get(keyKode)));

                if (keyKode == 27) {
                    logger.info("Exiting...");
                    System.exit(0);
                }

                calculator.keyPressed(keyMapping.get(keyKode));
                calculatorToString.setText(calculator.toString());
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        applicationWindow.setVisible(true);
    }

    public static HashMap<Integer, Calculator.Key> getKeysMapping() {
        
        HashMap<Integer, Calculator.Key> mapping = new HashMap<>();

        Calculator.Key[] digitKeys = new Calculator.Key[] {
            Calculator.Key.ZERO, 
            Calculator.Key.ONE, 
            Calculator.Key.TWO, 
            Calculator.Key.THREE, 
            Calculator.Key.FOUR, 
            Calculator.Key.FIVE, 
            Calculator.Key.SIX, 
            Calculator.Key.SEVEN, 
            Calculator.Key.EIGHT, 
            Calculator.Key.NINE, 
        };

        /*
         * NumPad: 0-9, codes: 96-105
         * Standard keyboard: 0-9, codes: 48-57
         */
        for (int i = 48, j = 96, index = 0; i <= 57; i++, j++, index++) {
            mapping.put(i, digitKeys[index]);
            mapping.put(j, digitKeys[index]);
        }

        mapping.putAll(Map.<Integer, Calculator.Key>of(
                107, Calculator.Key.ADDITION,
                109, Calculator.Key.SUBSTRATION,
                106, Calculator.Key.MULTIPLICATION,
                111, Calculator.Key.DIVISION));

        mapping.putAll(Map.<Integer, Calculator.Key>of(
                110, Calculator.Key.DECIMAL_DOT,
                10, Calculator.Key.EQUALS,
                78, Calculator.Key.NEGATIVE_NUMBER,
                79, Calculator.Key.OFF,
                67, Calculator.Key.CLEAR_ALL));

        mapping.put(27, Calculator.Key.OFF);
        mapping.put(8, Calculator.Key.BACKSPACE);

        return mapping;
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
    private static ArrayList<Key> opKeys = new ArrayList<>();
    private static HashMap<Key, Character> KeyCharacterMapping
        = new HashMap<>();

    static {

        Key[] arr = Key.values();
        for (int i = 0; i <= 9; i++) {
            digitKeys.add(arr[i]);
        }

        Collections.addAll(opKeys, Key.ADDITION,
                Key.SUBSTRATION, Key.MULTIPLICATION, Key.DIVISION);
        
        KeyCharacterMapping.put(Key.ZERO, '0');
        KeyCharacterMapping.put(Key.ONE, '1');
        KeyCharacterMapping.put(Key.TWO, '2');
        KeyCharacterMapping.put(Key.THREE, '3');
        KeyCharacterMapping.put(Key.FOUR, '4');
        KeyCharacterMapping.put(Key.FIVE, '5');
        KeyCharacterMapping.put(Key.SIX, '6');
        KeyCharacterMapping.put(Key.SEVEN, '7');
        KeyCharacterMapping.put(Key.EIGHT, '8');
        KeyCharacterMapping.put(Key.NINE, '9');
    }

    private Register screen;
    private Register registerX;
    private Key operation;
    private boolean showResult = false;

    public Calculator() {

        screen = new Register(12);
        registerX = new Register(12);
    }

    public Calculator(int screenCapacity) {

        screen = new Register(screenCapacity);
        registerX = new Register(screenCapacity);
    }

    public void keyPressed(Key key) {

        if (key == null) {
            return;
        }

        if (digitKeys.contains(key)) {
            digitKeyPressed(key);
        } else if (opKeys.contains(key)) {
            operationKeyPresse(key);
        } else {

            switch (key) {
                case OFF:
                    System.exit(0);
                    break;
                case CLEAR_ALL:
                    clearAll();
                    break;
                case NEGATIVE_NUMBER:
                    screen.setResetNegative();
                    break;
                case DECIMAL_DOT:
                    screen.addDigit('.');
                    break;
                case EQUALS:
                    equalsPressed();
                    break;
                case BACKSPACE:
                    screen.backSpace();
                    break;
            }
        }
    }

    @Override
    public String toString() {

        return
            "Operation: " + operation
            + "\n\nRegister X: " + registerX
                    + "\n\nScreen: " + screen;
    }

    private void clearAll() {

        screen.reset();
        registerX.reset();
        showResult = false;
    }

    private void equalsPressed() {

        Register result = applyOperation(registerX, screen, operation);
        result.copyTo(screen);
    }

    private void digitKeyPressed(Key key) {

        if (operation != null && !registerX.isBlank() && showResult) {
            screen.reset();
            showResult = false;
        }
        screen.addDigit(KeyCharacterMapping.get(key));
    }

    private void operationKeyPresse(Key key) {

        operation = key;
        showResult = true;
        screen.copyTo(registerX);
    }

    private Register applyOperation(Register r1, Register r2, Key op) {

        Register result = new Register(r1.getRegisterCapacity());

        switch (op) {
            case ADDITION:
                result =  Register.sum(r1, r2);
                break;
            case SUBSTRATION:
                result =  Register.sub(r1, r2);
                break;
            case MULTIPLICATION:
                result =  Register.mul(r1, r2);
                break;
            case DIVISION:
                result =  Register.div(r1, r2);
                break;
        }

        return result;
    }
}

class Register implements Comparable {
    
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
                & elementData.getLast() == '0';
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

    @Override
    public String toString() {

        String s = "Neg: " + (negative ? "y" : "n")
            + "; Dot: " + (dotPresent ? "y" : "n") + "; Val: ";
        for (Character c : elementData) {
            s += String.valueOf(c);
        }
        return s;
    }

    public int getRegisterCapacity() {
        return registerCapacity;
    }

    public int getSize() {
        return elementData.size();
    }

    public static Register sum(Register r1, Register r2) {

        Register result = new Register(r1.getRegisterCapacity());
        double r = r1.toDouble() + r2.toDouble();
        String strRep = Double.valueOf(r).toString();
        if ((r - (int)r) == 0) {
            strRep = strRep.substring(0, strRep.indexOf("."));
        }
        char[] charArray = strRep.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            result.addDigit(Character.valueOf(charArray[i]));
        }
        if (r < 0) {
            result.setResetNegative();
        }
        return result;
    }

    public static Register sub(Register r1, Register r2) {

        Register result = new Register(r1.getRegisterCapacity());
        double r = r1.toDouble() - r2.toDouble();
        String strRep = Double.valueOf(r).toString();
        if ((r - (int)r) == 0) {
            strRep = strRep.substring(0, strRep.indexOf("."));
        }
        char[] charArray = strRep.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            result.addDigit(Character.valueOf(charArray[i]));
        }
        if (r < 0) {
            result.setResetNegative();
        }
        return result;
    }

    public static Register mul(Register r1, Register r2) {

        Register result = new Register(r1.getRegisterCapacity());
        double r = r1.toDouble() * r2.toDouble();
        String strRep = Double.valueOf(r).toString();
        if ((r - (int)r) == 0) {
            strRep = strRep.substring(0, strRep.indexOf("."));
        }
        char[] charArray = strRep.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            result.addDigit(Character.valueOf(charArray[i]));
        }
        if (r < 0) {
            result.setResetNegative();
        }
        return result;
    }

    public static Register div(Register r1, Register r2) {

        Register result = new Register(r1.getRegisterCapacity());
        double r = r1.toDouble() / r2.toDouble();
        String strRep = Double.valueOf(r).toString();
        if ((r - (int)r) == 0) {
            strRep = strRep.substring(0, strRep.indexOf("."));
        }
        char[] charArray = strRep.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            result.addDigit(Character.valueOf(charArray[i]));
        }
        if (r < 0) {
            result.setResetNegative();
        }
        return result;
    }

    public boolean isDotPresent() {
        return dotPresent;
    }

    public boolean isNegative() {
        return negative;
    }

    @Override
    public int compareTo(Object o) {

        if (o.getClass() != Register.class) {
            throw new ClassCastException();
        }
        return Double.compare(toDouble(),
            ((Register)o).toDouble());
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
}

