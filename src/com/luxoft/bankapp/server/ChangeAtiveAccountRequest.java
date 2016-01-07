package com.luxoft.bankapp.server;

/**
 * Created by LChlebda on 2016-01-07.
 */
public class ChangeAtiveAccountRequest implements Request{

    private RequestType requestType = RequestType.CHANGE_ACTIVE_ACCOUNT;

    @Override
    public void printRequestInfo() {

    }

    @Override
    public RequestType getRequestType() {
        return requestType;
    }
}
