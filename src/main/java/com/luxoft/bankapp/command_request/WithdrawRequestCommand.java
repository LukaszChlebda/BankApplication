package com.luxoft.bankapp.command_request;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Łukasz on 17.01.2016.
 */
public class WithdrawRequestCommand implements Command {

    private Command[] commands;
    private Map<String, Command> commandMap;
    private String message;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private float amount;
    private boolean thirdLevelMenuFlag;
    private Scanner readUserInput;


    public WithdrawRequestCommand(String message, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.message = message;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        thirdLevelMenuFlag = true;
        readUserInput = new Scanner(System.in);
        commandMap = new HashMap<>();
        initCommands();
    }

    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        menu();
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Withdraw ");
    }

    private void menu() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        while (thirdLevelMenuFlag) {
            System.out.println("Chose account ");
            for (int i = 0; i < commands.length; i++) {
                System.out.print(i + ") ");
                commands[i].printCommandInfo();
            }
            String command = readUserInput.nextLine();
            try {
                commandMap.get(command).execute();
            } catch (NullPointerException e) {
                System.out.println("No such option ");
            }
        }
    }

    private void initCommands() {
        commands = new Command[]{
                new SavingAccountRequestCommand(message, objectInputStream, objectOutputStream),
                new CheckingAccountRequestCommand(message, objectInputStream, objectOutputStream),
                new Command() {
                    @Override
                    public void printCommandInfo() {
                        System.out.println("Back ");
                    }

                    @Override
                    public void execute() {
                        thirdLevelMenuFlag = false;
                    }
                }
        };
        commandMap.put("0", commands[0]);
        commandMap.put("1", commands[1]);
        commandMap.put("2", commands[2]);
    }


}
