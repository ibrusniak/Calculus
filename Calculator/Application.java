
import java.awt.event.*;
import javax.swing.JFrame;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Application {
    
    public static void main(String[] args) {

        Logger logger = Logger.getAnonymousLogger();
        logger.setLevel(Level.ALL);

        logger.info("Started...");

        Calculator calculator = new Calculator();

        HashMap<Integer, Calculator.Key> keyMapping =
            Calculator.Key.getKeysMapping();

        JFrame frame = new JFrame("Calculator GUI tester");

        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addKeyListener(new KeyListener() {

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
            }
                
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        frame.setVisible(true);
    }
}

class Calculator {

    public static enum Key {

        // NOTE: Do not change order from KEY_ADDITION to KEY_NINE
        KEY_ADDITION, KEY_SUBSTRATION, KEY_MULTIPLICATION, KEY_DIVISION,

        KEY_ZERO, KEY_ONE, KEY_TWO, KEY_THREE,
        KEY_FOUR, KEY_FIVE, KEY_SIX, KEY_SEVEN,
        KEY_EIGHT, KEY_NINE, 

        KEY_DECIMAL_DOT, KEY_EQUALS, KEY_NEGATIVE_NUMBER,
        KEY_CLEAR_ALL, KEY_OFF;

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
                67, KEY_CLEAR_ALL
            ));

            keysMapping.put(27, KEY_OFF);

            return keysMapping;
        }
    }

    public void keyPressed(Key k) {

    }
}


