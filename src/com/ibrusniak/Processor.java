package com.ibrusniak;

public class Processor {
    
    public static Num addition(Num operand1, Num operand2) {
        
        Num sum = new Num();
        if (bothZeroes(operand1, operand2)) return sum;
        return sum;
    }
    
    public static Num substraction(Num operand1, Num operand2) {
        
        Num difference = new Num();
        if (bothZeroes(operand1, operand2)) return difference;
        return difference;
    }
    
    public static Num musltiplication(Num operand1, Num operand2) {
        
        Num product = new Num();
        if (bothZeroes(operand1, operand2)) return product;
        return product;
    }
    
    public static Num division(Num operand1, Num operand2) {
        
        if (operand2.isZero())
            throw new Error("Can't divide by zero. operand2 should't be zero!")
        Num quotient = new Num();
        if (operand1.isZero()) return quotient;
        return quotient;
    }

    private static boolean bothZeroes(Num operand1, Num operand2) {
        return
            operand1.isZero() && operand2.isZero();
    }
}