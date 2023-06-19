package com.ibrusniak;

public class Processor {

    public Num summarize(Num n1, Num n2) {
        
        final Num emptyNum = new Num();
        
        if (n1.equals(emptyNum)) return n2;
        if (n2.equals(emptyNum)) return n1;

        return new Num(500);
    }
}