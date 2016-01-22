package com.luxoft.bankapp.exceptions;

import com.luxoft.bankapp.model.Bank;

/**
 * Created by LChlebda on 2016-01-22.
 */
public class BankExistException extends Exception{
    private String message;

    public BankExistException(Bank bank ) {
        message = "Bank " + bank.getName() + " already exists ";
    }

    public String getMessage() {
        return message;
    }

}
