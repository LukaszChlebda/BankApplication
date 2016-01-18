package com.luxoft.bankapp.server;

import com.luxoft.bankapp.command_request.ClientCommander;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by Łukasz on 17.01.2016.
 */
public class BankClient {
    private Socket requestSocket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String message;
    static final String SERVER = "localhost";

    private Request logoutRequest;
    private Request loginRequest;
    private Request getActiveAccountRequest;
    private Request changeActiveAccountRequest;
    private Request withdrawRequest;
    private ClientCommander clientCommander;

    public BankClient() {

    }

    public void run() {
        while (true) {
            try {
                requestSocket = new Socket(SERVER, 2004);
                System.out.println("Connected to the localhost ");
                objectOutputStream = new ObjectOutputStream(requestSocket.getOutputStream());
                objectOutputStream.flush();
                objectInputStream = new ObjectInputStream(requestSocket.getInputStream());
                clientCommander = new ClientCommander(message, objectOutputStream, objectInputStream);
                message = (String) objectInputStream.readObject();
                System.out.println(message);

                do {
                    serviceRequest();
                } while (!message.equals("bye"));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void serviceRequest() {
        try {
            clientCommander.mainMenu();
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        } catch (NotEnoughtFundsException e) {
            e.printStackTrace();
        } catch (ClientExistsException e) {
            e.printStackTrace();
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

    public void sendMessage(String message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
            System.out.println("client>" + message);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public String getMessage() {
        String recivedMessage = null;
        try {
            recivedMessage = (String)objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return recivedMessage;
    }

    public static void main(String[] args) {
        BankClient bankClientV2 = new BankClient();
        bankClientV2.run();
    }
}