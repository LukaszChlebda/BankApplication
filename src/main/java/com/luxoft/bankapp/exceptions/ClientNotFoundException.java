package com.luxoft.bankapp.exceptions;

import com.luxoft.bankapp.model.Client;

/**
 * Created by LChlebda on 2015-12-17.
 */
public class ClientNotFoundException extends Throwable{
    String message;

    public ClientNotFoundException(){
        message = "Client does not exist in database ";
    }

    public ClientNotFoundException(String name) {
        message = "Client " + name + " does not exist in database ";
    }

    @Override
    public String toString() {
        return message;
    }

}
