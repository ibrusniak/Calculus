package tests.classes;

import org.junit.Test;
import com.classes.Num;

public class NumTests {
    
    @Test
    public void constructorTest1() {

        new Num(0);
        new Num(100);
        new Num(-0);
        new Num(-100);
        new Num(00);
        new Num(-00);
    }

    @Test
    public void constructorTest2() {

        new Num(0.0);
        new Num(.0);
        new Num(-0.0);
        new Num(.0);
        new Num(-.0);
        new Num(2.5);
        new Num(-2.5);
    }
}