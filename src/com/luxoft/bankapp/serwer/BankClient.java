package com.luxoft.bankapp.serwer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by LChlebda on 2016-01-04.
 */
public class BankClient {
    Socket requestSocket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    String message;
    static final String SERVER = "localhost";

    public void run() {
        try {
            requestSocket = new Socket(SERVER, 2004);
            System.out.println("Connected to the localhost ");
            objectOutputStream = new ObjectOutputStream(requestSocket.getOutputStream());
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(requestSocket.getInputStream());

            do {
                try {
                    message = (String) objectInputStream.readObject();
                    System.out.println("Server>" + message);
                    sendMessage("Hi my server ");
                    message = "bye";
                    sendMessage(message);

                } catch (ClassNotFoundException e) {
                    System.err.print("Class not found ");
                } catch (IOException f) {
                    System.err.print("IE exception ");
                }

            } while (!message.equals("bye"));


        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }finally {
            try{
                objectInputStream.close();
                objectOutputStream.close();
                requestSocket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
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

    public static void main(String[] args) {
        BankClient bankClient = new BankClient();
        bankClient.run();
    }
}
