package com.ibrusniak;

import static com.ibrusniak.Utils.*;

public class Test1 {

    public static void main(String[] args) {

        Processor processor = new Processor();
        println(processor.summarize(new Num(), new Num(-100)));

    }
}