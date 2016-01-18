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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Łukasz on 17.01.2016.
 */
public class ClientCommander {

    private boolean firstLevelMenuFlag, loggedIn;
    private Map<String, Command> commandMap;
    private Command[] commands;
    private Scanner readUserInput;
    private String message;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private LoginRequestCommand loginRequestCommand;
    private Request clientEndRequest;
    private Command logOutRequest;

    public ClientCommander(String message, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        commandMap = new HashMap<>();
        readUserInput = new Scanner(System.in);
        firstLevelMenuFlag = true;
        loggedIn = false;
        this.message = message;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        initCommands();
    }

    private void initCommands() {
        commands = new Command[]{
                new LoginRequestCommand(message, objectInputStream, objectOutputStream),
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
    }

    public void mainMenu() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        while(firstLevelMenuFlag) {
            displayMenu(0);
            for (int i = 0; i < commands.length; i++) {
                System.out.print(i + ") ");
                commands[i].printCommandInfo();
            }
            String command = readUserInput.next();
            try {
                commandMap.get(command).execute();
            }catch (NullPointerException e) {
                System.out.println("No such option ");
            }
        }
    }

    private void displayMenu(int chooseMenu) {
        StringBuilder sb = new StringBuilder();
        switch (chooseMenu) {
            case 0:
                sb.append("|------------------\n")
                        .append("| Welcome in Super Bank |\n")
                        .append("|-------------------|\n");
                break;
            case 1:
                break;
            default:
                break;
        }
        System.out.println(sb);
    }
    private void sendMessage(String message) {
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
}
