package com.luxoft.bankapp.server;

import com.luxoft.bankapp.model.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by LChlebda on 2016-01-04.
 */
public class BankClient {
    Socket requestSocket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    String message = "test";
    static final String SERVER = "localhost";
    private boolean loggedIn = false;
    private Client client = null;

   // String tempGetRequest;

    private Request logoutRequest;
    private Request loginRequest;
    private Request getActiveAccountRequest;
    private Request withdrawRequest;
//    Request[] requests = {loginRequest};

    public void serviceRequest() {
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        String userName;
        String userInput;
        System.out.println("Welcome in super Bank \nEnter your name to login into system \n$> ");
        userName = sc.next();

        loginRequest = new LoginReguest();
        ((LoginReguest)loginRequest).setLogin(userName);
        sendRequest(loginRequest);

        try {
            message = (String) objectInputStream.readObject();
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(message.equals("OK")) {
            loggedIn = true;
        }

        if(loggedIn){
            System.out.println("Hello " + userName);
            while (flag) {
                System.out.println("\nChoose action: \n1 - Display accounts information \n" +
                        "2 - Withdraw \n" +
                        "3 - Logout \n$> ");
                userName = sc.next();

                switch (userName) {
                    case "1":
                        getActiveAccountRequest = new GetAccountsInfoRequest();
                        sendRequest(getActiveAccountRequest);
                        try {
                            message = (String)objectInputStream.readObject();
                            System.out.println(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "2":
                        System.out.println("Enter amount ");
                        userInput = sc.next();
                        withdrawRequest = new WithdrawRequest();
                        ((WithdrawRequest)withdrawRequest).setAmountToWithdraw(Float.valueOf(userInput));
                        sendRequest(withdrawRequest);
                        try {
                            message = (String)objectInputStream.readObject();
                            System.out.println(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "3":
                        logoutRequest = new LogoutRequest();
                        sendRequest(logoutRequest);
                        ((LoginReguest) loginRequest).setLogin("");
                        loggedIn = false;
                        flag = false;
                        break;
                    default:
                        System.out.println("No such option ");
                        break;
                }
            }
        }else {
           System.out.println("No user " + userName + " found in database ");
        }
    }


    public void run() {
        try {
            requestSocket = new Socket(SERVER, 2004);
            System.out.println("Connected to the localhost ");
            objectOutputStream = new ObjectOutputStream(requestSocket.getOutputStream());
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(requestSocket.getInputStream());
            message = (String)objectInputStream.readObject();
            System.out.println(message);

            do {
                serviceRequest();

            } while (!message.equals("bye"));


        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try{
                objectInputStream.close();
                objectOutputStream.close();
                requestSocket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean loginRequest(String name) {
        sendMessage(name);
        try {
            message = (String) objectInputStream.readObject();
            if(message.equals("OK"));
            {
                return true;
            }
        }catch (ClassNotFoundException e) {

        }catch (IOException f) {

        }
        return false;
    }
    public void sendMessage(String message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
            System.out.println("client>" + message);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
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

    public String reciveRequest() {
        try {
            message = (String)objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static void main(String[] args) {
        BankClient bankClient = new BankClient();
        bankClient.run();
    }
}
