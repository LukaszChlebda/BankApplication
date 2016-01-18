package com.luxoft.bankapp.server;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.exceptions.RequestNotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by LChlebda on 2016-01-12.
 */
public class ServerThread implements Runnable {

    private Bank bank = null;
    private ServerSocket providerSocket;
    private Socket connection = null;
    private Client activeClient;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;


    private Object monitor = new Object();
    private volatile String message;
    private Request requestMessage;
    private CounterService counterService;

    public ServerThread(Socket clientSocket, Bank bank, CounterService counterService) {

        this.connection = clientSocket;
        this.bank = bank;
        this.counterService = counterService;
        message = "";
    }

    public synchronized void getRequest(Request requestMessage) throws RequestNotFoundException {
        if (requestMessage.getRequestType() == RequestType.LOGIN_REQUEST) {
            try {
                System.out.println("Checking user in database ");
                activeClient = bank.getClient(((LoginReguest) requestMessage).getLogin());
                counterService.incrementUserCounter();
                sendMessage("OK");


            } catch (ClientNotFoundException e) {
                activeClient = null;
                sendMessage("ERROR");
            }

        } else if (requestMessage.getRequestType() == RequestType.LOGOUT_REQUEST) {
            message = null;
            message = ((LogoutRequest) requestMessage).getRequestInfo();
            System.out.println(message);
            //usersCounter.getAndDecrement();
            counterService.decrementUserCounter();

        } else if (requestMessage.getRequestType() == RequestType.CHANGE_ACTIVE_ACCOUNT) {
            activeClient.setActiveAccount(activeClient.getAccounts().get(((ChangeActiveAccountRequest) requestMessage).getActiveAccount()));
            sendMessage("OK");
        } else if (requestMessage.getRequestType() == RequestType.GET_ACCOUNTS_INFO) {
            message = activeClient.getAccountsInfo();
            activeClient.setActiveAccount(activeClient.getAccounts().get(0));
            sendMessage(message);

        } else if (requestMessage.getRequestType() == RequestType.WITHDRAW_REQUEST) {
            try {
                activeClient.getActiveAccount().withdraw(((WithdrawRequest) requestMessage).getAmountToWithdraw());
                message = activeClient.getAccountsInfo();
                sendMessage("OK \n" + message);
            } catch (NotEnoughtFundsException e) {
                sendMessage("Not enough founds");
            }

        } else {
            throw new RequestNotFoundException();
        }
    }


    public synchronized void run() {
        try {

            System.out.println("Server waiting for connection ");
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());

            objectOutputStream = new ObjectOutputStream(connection.getOutputStream());
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(connection.getInputStream());

            sendMessage("Connection successful");


            do {
                try {
                    requestMessage = (Request) objectInputStream.readObject();
                    System.out.println("Request type " + requestMessage.getRequestType());

                    getRequest(requestMessage);
                } catch (RequestNotFoundException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } while (!message.equals("bye"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                objectInputStream.close();
                objectOutputStream.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Bank getBank() {
        return bank;
    }

    public synchronized void sendMessage(String message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
            System.out.println("server>" + message);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
