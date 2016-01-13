package com.luxoft.bankapp.server;

/**
 * Created by LChlebda on 2016-01-13.
 */
public class BankServerMonitor implements Runnable{
    private CounterService counterService;

    public BankServerMonitor(CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    public void run() {
        while(true) {
            System.out.println();
        }
    }
}
