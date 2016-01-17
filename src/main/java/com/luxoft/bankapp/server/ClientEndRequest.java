package com.luxoft.bankapp.server;

/**
 * Created by Łukasz on 17.01.2016.
 */
public class ClientEndRequest implements Request {
    private RequestType requestType = RequestType.CLIENT_END_REQUEST;
    @Override
    public void printRequestInfo() {

    }

    @Override
    public RequestType getRequestType() {
        return requestType;
    }
}
