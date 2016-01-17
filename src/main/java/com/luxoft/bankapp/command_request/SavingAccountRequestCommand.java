package com.luxoft.bankapp.command_request;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.server.ChangeActiveAccountRequest;
import com.luxoft.bankapp.server.Request;
import com.luxoft.bankapp.server.WithdrawRequest;
import com.luxoft.bankapp.service.AccountType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Created by Łukasz on 17.01.2016.
 */
public class SavingAccountRequestCommand implements Command {

    private Request activeAccountRequest, withdrawRequest;
    private String message;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String amount;
    private Scanner readUserInput;
    private boolean activeAccountChoosen;

    public SavingAccountRequestCommand(String message, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.message = message;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        activeAccountChoosen = false;
        readUserInput = new Scanner(System.in);

    }


    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        activeAccountRequest = new ChangeActiveAccountRequest(AccountType.SAVING_ACCOUNT);
        sendRequest(activeAccountRequest);
        message = getMessage();
        activeAccountChoosen = true;
        performWithdraw();

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Saving account ");

    }

    public void performWithdraw() {
        if (activeAccountChoosen) {
            System.out.println("Enter amount ");
            amount = readUserInput.next();
            withdrawRequest = new WithdrawRequest();
            ((WithdrawRequest) withdrawRequest).setAmountToWithdraw(Float.valueOf(amount));
            sendRequest(withdrawRequest);
            message = getMessage();
            System.out.println(message);
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

    public void sendRequest(Request request) {
        try {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            System.out.println("client>" + request.getRequestType());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
