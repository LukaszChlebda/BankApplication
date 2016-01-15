package com.luxoft.bankapp.server;

import java.io.Serializable;

/**
 * Created by LChlebda on 2016-01-04.
 */
public interface Request extends Serializable{
    public void printRequestInfo();
    public RequestType getRequestType();
}
