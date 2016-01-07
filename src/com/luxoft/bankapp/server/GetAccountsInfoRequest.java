package com.luxoft.bankapp.server;

/**
 * Created by LChlebda on 2016-01-07.
 */
public class GetAccountsInfoRequest implements Request {

    private RequestType requestType = RequestType.GET_ACCOUNTS_INFO;

    @Override
    public void printRequestInfo() {
        System.out.println("");
    }

    @Override
    public RequestType getRequestType() {
        return requestType;
    }
}
