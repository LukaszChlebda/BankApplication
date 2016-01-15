package com.luxoft.bankapp.server;

/**
 * Created by LChlebda on 2016-01-13.
 */
public class BankServerMonitor extends Thread{
    private CounterService counterService;
    private final int COUNTER_REFRESH_IN_MILISEC = 3000;

    public BankServerMonitor(CounterService counterService) {
        this.counterService = counterService;
    }

    @Override
    public void run() {
        while(true) {
            System.out.println("Connected clients: " + counterService.getCounter());
            try {
                Thread.sleep(COUNTER_REFRESH_IN_MILISEC);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
