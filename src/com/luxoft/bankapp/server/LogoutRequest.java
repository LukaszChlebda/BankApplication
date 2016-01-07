package com.luxoft.bankapp.server;

/**
 * Created by LChlebda on 2016-01-07.
 */
public class LogoutRequest implements Request{

    private RequestType requestType = RequestType.LOGOUT_REQUEST;

    @Override
    public void printRequestInfo() {
        System.out.println("You are logged out ");
    }


    public String getRequestInfo() {
        return "You are logged out ";
    }

    @Override
    public RequestType getRequestType() {
        return requestType;
    }
}
