package com.luxoft.bankapp.command_request;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.server.GetAccountsInfoRequest;
import com.luxoft.bankapp.server.Request;
import com.luxoft.bankapp.server.WithdrawRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Łukasz on 17.01.2016.
 */
public class DisplayAccountInfoRequestCommand implements Command {

    private String message;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Request getAccountInfoRequest;
    private String clientName;

    public DisplayAccountInfoRequestCommand(String message, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        this.message = message;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }

    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        getAccountInfoRequest = new GetAccountsInfoRequest();
        sendRequest(getAccountInfoRequest);
        message = getMessage();
        System.out.println(message);
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Display accounts info ");
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

    public String getMessage() {
        String recivedMessage = null;
        try {
            recivedMessage = (String) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return recivedMessage;
    }
}
