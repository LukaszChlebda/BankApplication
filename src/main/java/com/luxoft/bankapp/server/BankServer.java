package com.luxoft.bankapp.server;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.exceptions.RequestNotFoundException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LChlebda on 2016-01-04.
 */
public class BankServer {
    private Bank bank = null;
    private ServerSocket providerSocket;
    private Socket connection = null;
    private Client activeClient;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private String message;
    private Request requestMessage;

    private Map<RequestType, Request> requestMap;
    private Request[] requests;



    public BankServer(Bank bank) {
        this.bank = bank;
        requestMap = new HashMap<>();
        message = "";
    }

    public void getRequest(Request requestMessage) throws RequestNotFoundException {
        if (requestMessage.getRequestType() == RequestType.LOGIN_REQUEST) {
            try {
                System.out.println("Checking user in database ");
                activeClient = bank.getClient(((LoginReguest)requestMessage).getLogin());
                sendMessage("OK");
            } catch (ClientNotFoundException e) {
                activeClient = null;
                sendMessage("ERROR");
            }

        } else if (requestMessage.getRequestType() == RequestType.LOGOUT_REQUEST) {
            message = ((LogoutRequest)requestMessage).getRequestInfo();
            System.out.println(message);

        } else if (requestMessage.getRequestType() == RequestType.CHANGE_ACTIVE_ACCOUNT) {
            activeClient.setActiveAccount(activeClient.getAccounts().get(((ChangeActiveAccountRequest)requestMessage).getActiveAccount()));
            sendMessage("OK");
        } else if (requestMessage.getRequestType() == RequestType.GET_ACCOUNTS_INFO) {
            message = "Client " + activeClient.getName() + "\n";
            message = activeClient.getAccountsInfo();
            activeClient.setActiveAccount(activeClient.getAccounts().get(0));
            sendMessage(message);

        } else if (requestMessage.getRequestType() == RequestType.WITHDRAW_REQUEST) {
            try {
                activeClient.getActiveAccount().withdraw(((WithdrawRequest)requestMessage).getAmountToWithdraw());
                message = activeClient.getAccountsInfo();
                sendMessage("OK \n" + message);
            } catch (NotEnoughtFundsException e) {
                sendMessage("Not enough founds");
            }

        }else if(requestMessage.getRequestType() == RequestType.CLIENT_END_REQUEST) {
            message = "bye";
            sendMessage("bye");
        }else{
            throw new RequestNotFoundException();
        }
    }


    public void run() {
            try {
                    providerSocket = new ServerSocket(2004, 10);
                    System.out.println("Server waiting for connection ");
                    connection = providerSocket.accept();

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

                    } while (true);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    objectInputStream.close();
                    objectOutputStream.close();
                    //providerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

//    private void initRequests() {
//        requests = new Request[] {
//
//        }
//    }

    public void sendMessage(String message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
            System.out.println("server>" + message);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}

