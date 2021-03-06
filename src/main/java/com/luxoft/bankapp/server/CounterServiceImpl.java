package com.luxoft.bankapp.server;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LChlebda on 2016-01-13.
 */
public class CounterServiceImpl implements CounterService{
    private volatile AtomicInteger usersCounter = new AtomicInteger(0);
    public synchronized void incrementUserCounter() {
        usersCounter.getAndIncrement();
    }

    public synchronized void decrementUserCounter() {
        if(usersCounter.get() > 0) {
            usersCounter.getAndDecrement();
        }
    }

    public synchronized AtomicInteger getCounter() {
        return usersCounter;
    }
}
