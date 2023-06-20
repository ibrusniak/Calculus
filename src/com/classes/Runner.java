package com.classes;

import static com.classes.Utils.*;

public class Runner {
    
    public static void main(String[] args) {

        int a = 00;
        println(a);
        a = -00;
        println(a);
        a = -0;
        println(a);
        a = 00000;
        println(a);
        a = 00001;
        println(a);
        a = -00000000123;
        println(a);

        println();
        println(Integer.valueOf(000));
        println(Integer.valueOf(-000));
        println(Integer.valueOf(0001));
        println(Integer.valueOf(-1000));
        println();
        println();
        println(Integer.valueOf(-0).equals(Integer.valueOf(0)));
    }
}
