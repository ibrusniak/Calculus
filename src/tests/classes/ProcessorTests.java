package tests.classes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.classes.Num;
import com.classes.Processor;

public class ProcessorTests {
    
    Processor processor = new Processor();

    @Test
    public void additionTest1() {

        Num sum = new Num(100);
        assertEquals(sum, processor.summarize(new Num(100), new Num(80)));
        assertEquals(sum, processor.summarize(new Num(-200), new Num(300)));
        assertEquals(sum, processor.summarize(new Num(), new Num(100)));
    }
}
