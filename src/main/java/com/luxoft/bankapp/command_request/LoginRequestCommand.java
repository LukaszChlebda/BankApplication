package com.luxoft.bankapp.command_request;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.server.LoginReguest;
import com.luxoft.bankapp.server.Request;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Łukasz on 17.01.2016.
 */
public class LoginRequestCommand implements Command {

    private Map<String, Command> commandMap;
    private Command[] commands;
    private boolean secondLeverMenuFlag;
    private Scanner readUserInput;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String message;
    private String username;
    private Request LoginRequest, clientEndRequest;
    private Command logOutRequest;
    private boolean isUserLoggedIn;


    public LoginRequestCommand(String message, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        commandMap = new HashMap<>();
        secondLeverMenuFlag = true;
        readUserInput = new Scanner(System.in);
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.message = message;
        isUserLoggedIn = false;
        initCommands();
    }

    public void mainMenu() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        isUserLoggedIn = logintoSystem();
        while (secondLeverMenuFlag && isUserLoggedIn) {
            System.out.println("Welcome " + username);
            for (int i = 0; i < commands.length; i++) {
                System.out.print(i + ") ");
                commands[i].printCommandInfo();
            }
            String command = readUserInput.next();
            try {
                commandMap.get(command).execute();
            } catch (NullPointerException e) {
                System.out.println("No such option ");
            }
        }
    }

    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        mainMenu();
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Login ");
    }

    private void sendMessage(String message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
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

    public void sendRequest(Request request) {
        try {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            System.out.println("client>" + request.getRequestType());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private boolean logintoSystem() {
        System.out.println("Enter your login: ");
        username = readUserInput.next();
        LoginRequest = new LoginReguest(username);
       sendRequest(LoginRequest);
        message = getMessage();

        if (message.equals("OK")) {
            return true;
        }
        return false;
    }

    private void initCommands() {
        commands = new Command[]{
                new DisplayAccountInfoRequestCommand(message, objectOutputStream, objectInputStream),
                new WithdrawRequestCommand(message, objectInputStream, objectOutputStream),
                new Command() {
                    @Override
                    public void printCommandInfo() {
                        System.out.println("Exit ");
                    }

                    @Override
                    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
                        logOutRequest = new LogOutRequestCommand(message, objectInputStream, objectOutputStream);
                        logOutRequest.execute();
                    }
                }
        };
        commandMap.put("0", commands[0]);
        commandMap.put("1", commands[1]);
        commandMap.put("2", commands[2]);
    }
}
