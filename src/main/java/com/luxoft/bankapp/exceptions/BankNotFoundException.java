package com.luxoft.bankapp.exceptions;

/**
 * Created by LChlebda on 2016-01-20.
 */
public class BankNotFoundException extends Throwable{
    private String message;

    public BankNotFoundException() {
        message = "Bank not fund ";
    }

    public BankNotFoundException(String name) {
        message = "Bank name " + name + " not found ";
    }

    public String getMessage() {
        return message;
    }

}
