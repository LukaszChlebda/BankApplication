package com.luxoft.bankapp.serwer;


/**
 * Created by LChlebda on 2016-01-04.
 */
public class LoginReguest implements Request {

    private String name;

    public LoginReguest(){};

    public LoginReguest(String name) {
        this.name = name;
    }

    public String getLogin() {
        return name;
    }


    @Override
    public void printRequestInfo() {
        System.out.println("Log in to the system ");
    }
}
