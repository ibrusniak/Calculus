
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

public class Test {
    
    public static void main(String[] args) {

        Logger logger = Logger.getAnonymousLogger();
        logger.setLevel(Level.ALL);

        logger.info("Started...");

        Calculator calculator = new Calculator();

        HashMap<Integer, Calculator.Key> keyMapping =
            Calculator.Key.getKeysMapping();

        JFrame applicationWindow = new JFrame("Calculator class tester (ESC - to exit)");

        applicationWindow.setSize(640, 200);
        applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea calculatorToString = new JTextArea();
        calculatorToString.setEditable(false);
        applicationWindow.add(calculatorToString);
        calculatorToString.setText(calculator.toString());

        calculatorToString.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

	}
            
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
            public void keyReleased(KeyEvent e) {

	}
        });

        applicationWindow.setVisible(true);
    }
}

class Calculator {

    private ArrayDeque<Character> screen = new ArrayDeque<>();
    private ArrayDeque<Character> register = new ArrayDeque<>();

    private Key lastPressedOperation = null;

    private boolean justPressedOperationKey = false;
    private boolean isDotPresent = false;
    private boolean isOnlyZero = true;

    public Calculator() {
        clearAll();
    }

    public static enum Key {

        // NOTE: Do not change order from KEY_ADDITION to KEY_NINE
        KEY_ADDITION, KEY_SUBSTRATION, KEY_MULTIPLICATION, KEY_DIVISION,

        KEY_ZERO, KEY_ONE, KEY_TWO, KEY_THREE,
        KEY_FOUR, KEY_FIVE, KEY_SIX, KEY_SEVEN,
        KEY_EIGHT, KEY_NINE, 

        KEY_DECIMAL_DOT, KEY_EQUALS, KEY_NEGATIVE_NUMBER,
        KEY_CLEAR_ALL, KEY_OFF;

        public static List<Key> digits = Arrays.asList(new Key[] {
            KEY_ZERO, KEY_ONE, KEY_TWO, KEY_THREE,
            KEY_FOUR, KEY_FIVE, KEY_SIX, KEY_SEVEN,
            KEY_EIGHT, KEY_NINE
        });

        public static List<Key> operations = Arrays.asList(new Key[] {
            KEY_ADDITION, KEY_SUBSTRATION, KEY_MULTIPLICATION, KEY_DIVISION
        });

        public static List<Key> otherKeys = Arrays.asList(new Key[] {
            KEY_DECIMAL_DOT, KEY_EQUALS, KEY_NEGATIVE_NUMBER,
            KEY_CLEAR_ALL, KEY_OFF
        });

        public static HashMap<Integer, Key> getKeysMapping() {

            HashMap<Integer, Key> keysMapping = new HashMap<>();

            /*
             * NumPad: 0-9, codes: 96-105
             * Standard keyboard: 0-9, codes: 48-57
             */
            int position = 4;
            Key[] values = Key.values();
            for (int i = 96, j = 48; i <= 105; i++, j++) {
                keysMapping.put(i, values[position]);
                keysMapping.put(j, values[position]);
                position++;
            }

            keysMapping.putAll(Map.<Integer, Key>of(
                107, KEY_ADDITION,
                109, KEY_SUBSTRATION,
                106, KEY_MULTIPLICATION,
                111, KEY_DIVISION
            ));

            keysMapping.putAll(Map.<Integer, Key>of(
                110, KEY_DECIMAL_DOT,
                10, KEY_EQUALS,
                78, KEY_NEGATIVE_NUMBER,
                79, KEY_OFF,
                67, KEY_CLEAR_ALL
            ));

            keysMapping.put(27, KEY_OFF);

            return keysMapping;
        }
    }

    public void keyPressed(Key key) {

        if (Key.digits.contains(key)) {
            digitKeyPressed(key);
        } else if (Key.operations.contains(key)) {
            operationKeyPressed(key);
        } else if (Key.otherKeys.contains(key)) {
            otherKeyPressed(key);
        }
    }

    @Override
    public String toString() {

        String _screen = "";
        for (Character c : screen) {
            _screen += String.valueOf(c);
        }
        String _register = "";
        for (Character c : register) {
            _register += String.valueOf(c);
        }
        return super.toString()
            + "\n\nLast pressed operation key: " + lastPressedOperation
            + "\n\njust pressed operation key: " + justPressedOperationKey
            + "\n\nRegister: " + _register
            + "\n\nScreen: " + _screen;
    }

    private void digitKeyPressed(Key key) {
    
        switch (key) {
            
            case KEY_ZERO:
                zero();
                break;

            case KEY_ONE:
                one();
                break;

            case KEY_TWO:
                two();
                break;

            case KEY_THREE:
                three();
                break;

            case KEY_FOUR:
                four();
                break;
            
            case KEY_FIVE:
                five();
                break;

            case KEY_SIX:
                six();
                break;

            case KEY_SEVEN:
                seven();
                break;

            case KEY_EIGHT:
                eight();
                break;

            case KEY_NINE:
                nine();
                break;
        }
    }

    private void operationKeyPressed(Key key) {
        
        lastPressedOperation = key;
        justPressedOperationKey = true;
    }

    private void otherKeyPressed(Key key) {
        
        switch (key) {

            case KEY_DECIMAL_DOT:
                decimalDot();
                break;

            case KEY_EQUALS:
                equals();
                break;

            case KEY_NEGATIVE_NUMBER:
                negativeNumber();
                break;

            case KEY_CLEAR_ALL:
                clearAll();
                break;

            case KEY_OFF:
                off();
                break;
        }
    }

    private void zero() {
        if (!isOnlyZero || isDotPresent) {
            screen.addLast('0');
        }
    }
    
    private void one() {

        if (isOnlyZero) {
            screen.clear();
        }
        screen.addLast('1');
        isOnlyZero = false;
	}
    
    private void two() {

        if (isOnlyZero) {
            screen.clear();
        }
        screen.addLast('2');
        isOnlyZero = false;
	}
    
    private void three() {

        if (isOnlyZero) {
            screen.clear();
        }
        screen.addLast('3');
        isOnlyZero = false;
	}
    
    private void four() {

        if (isOnlyZero) {
            screen.clear();
        }
        screen.addLast('4');
        isOnlyZero = false;
	}
    
    private void five() {

        if (isOnlyZero) {
            screen.clear();
        }
        screen.addLast('5');
        isOnlyZero = false;
	}
    
    private void six() {

        if (isOnlyZero) {
            screen.clear();
        }
        screen.addLast('6');
        isOnlyZero = false;
	}
    
    private void seven() {

        if (isOnlyZero) {
            screen.clear();
        }
        screen.addLast('7');
        isOnlyZero = false;
	}
    
    private void eight() {

        if (isOnlyZero) {
            screen.clear();
        }
        screen.addLast('8');
        isOnlyZero = false;
	}
    
    private void nine() {

        if (isOnlyZero) {
            screen.clear();
        }
        screen.addLast('9');
        isOnlyZero = false;
	}

    private void decimalDot() {

        if (!isDotPresent) {
            screen.addLast('.');
            isDotPresent = true;
        }
	}

    private void equals() {

	}

    private void negativeNumber() {

	}

    private void clearAll() {

        register.clear();
        screen.clear();
        screen.addLast('0');
        register.addLast('0');
        lastPressedOperation = null;
        justPressedOperationKey = false;
        isDotPresent = false;
        isOnlyZero = true;
    }

    private void off() {
        System.exit(0);
    }
}


