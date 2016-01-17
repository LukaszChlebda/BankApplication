package com.luxoft.bankapp.command_request;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.server.ClientEndRequest;
import com.luxoft.bankapp.server.LogoutRequest;
import com.luxoft.bankapp.server.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Łukasz on 17.01.2016.
 */
public class LogOutRequestCommand implements Command{

    private Request logOutRequest, clientEndRequest;
    private String message;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public LogOutRequestCommand(String message, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.message = message;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public void execute() {
        logOutRequest = new LogoutRequest();
        sendRequest(logOutRequest);
        clientEndRequest = new ClientEndRequest();
        System.out.println("Good by ");
        System.exit(0);


    }

    public void sendRequest(Request request) {
        try {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            System.out.println("client>" + request.getRequestType());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {

    }
}
