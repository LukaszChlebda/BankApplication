package com.luxoft.bankapp.server;


/**
 * Created by LChlebda on 2016-01-04.
 */
public class LoginReguest implements Request {

    private String name;

    private RequestType requestType = RequestType.LOGIN_REQUEST;

    public LoginReguest(){};

    public LoginReguest(String name) {
        this.name = name;
    }

    public String getLogin() {
        return name;
    }

    public void setLogin(String login) {
        this.name = login;
    }
    public RequestType getRequestType() {
        return requestType;
    }


    @Override
    public void printRequestInfo() {
        System.out.println("Log in to the system ");
    }
}
