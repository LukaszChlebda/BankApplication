package com.luxoft.bankapp.server;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.Bank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by LChlebda on 2016-01-04.
 */
public class BankServer {
    private Bank bank = null;
    ServerSocket providerSocket;
    Socket connection = null;

    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;

    String message;

    public BankServer(Bank bank) {
        this.bank = bank;
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

            do{
                try{
                    message = (String)objectInputStream.readObject();
                    System.out.println("client>" + message);
                    try {
                        bank.getClient(message);
                        sendMessage("OK");
                    }catch (ClientNotFoundException e) {
                        sendMessage("ERROR");
                        System.out.println(e);
                    }
                    if(message.equals("bye")) {
                        sendMessage("bye");
                    }
                }catch (ClassNotFoundException e) {
                    System.err.println("Data received in unknown format");
                }

            }while (!message.equals("bye"));

        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                objectInputStream.close();
                objectOutputStream.close();
                providerSocket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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

