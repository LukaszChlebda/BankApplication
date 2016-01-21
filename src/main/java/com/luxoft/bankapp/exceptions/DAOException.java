package com.luxoft.bankapp.exceptions;

/**
 * Created by LChlebda on 2016-01-20.
 */
public class DAOException extends Exception{


    private String message;

    public DAOException() {
        message = "Dao exception";
    }

    public String getMessage() {
        return message;
    }
}
