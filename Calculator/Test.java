
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
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
        logger.setLevel(Level.OFF);

        logger.info("Started...");

        Calculator calculator = new Calculator();

        HashMap<Integer, Calculator.Key> keyMapping = getKeysMapping();

        JFrame applicationWindow = new JFrame("Calculator class tester (ESC - to exit)");

        applicationWindow.setSize(640, 200);
        applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea calculatorToString = new JTextArea();
        calculatorToString.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        calculatorToString.setForeground(Color.BLUE);
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

        Register result = new Register(getRegisterCapacity());
        String s = Double.valueOf(d).toString();
        
        if ((d - (int)d) == 0) {
            s = s.substring(0, s.indexOf("."));
        }

        char[] charArray = s.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            result.addDigit(Character.valueOf(charArray[i]));
        }
        if (d < 0) {
            result.setResetNegative();
        }
        return result;
    }
}

