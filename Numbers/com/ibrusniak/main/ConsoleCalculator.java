package com.ibrusniak.main;

public class ConsoleCalculator implements Runnable {

    @Override
    public void run() {}

    public void start() {
        new Thread(this).start();
    }
}


