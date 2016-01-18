package com.luxoft.bankapp.server;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LChlebda on 2016-01-13.
 */
public interface CounterService {
    public void incrementUserCounter();
    public void decrementUserCounter();
    public AtomicInteger getCounter();

}
