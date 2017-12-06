package com.examples.calculator;

/**
 * Created by alee2 on 8/2/17.
 *
 * @author alee2
 */
public class Add {
    private final int num1;
    private final int num2;
    private int num3;

    public Add(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    public int getNum3() {
        return num3;
    }

    public int getNum2() {
        return num2;
    }

    public int getNum1() {
        return num1;
    }

    public void add() {
        num3 = num1 + num2;
    }
}
