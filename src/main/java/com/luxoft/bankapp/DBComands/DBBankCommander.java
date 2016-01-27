package com.luxoft.bankapp.DBComands;

import com.luxoft.bankapp.command.*;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.*;

/**
 * Created by Łukasz on 24.01.2016.
 */
public class DBBankCommander {

    public static Bank activeBank = null;
    public static Client activeClient = null;
    public static Client clientToTransfer = null;
    public static List<Client> listOfAllClients = new ArrayList<>();

    static Map<String, Command> commandMap = new TreeMap<>();


    public static void initCommands() {
        commandMap.put("0", commands[0]);
        commandMap.put("1", commands[1]);
        commandMap.put("2", commands[2]);
        commandMap.put("3", commands[3]);
        commandMap.put("4", commands[4]);
        commandMap.put("5", commands[5]);
        commandMap.put("6", commands[6]);
        commandMap.put("7", commands[7]);
    }
    public void registerCommand(String name, Command comand) {
        commandMap.put(name, comand);
    }

    public void removeCommand(String name) {
        commandMap.remove(name);
    }

    static Command[] commands = {

            new DBAddClientCommand(),
            new DBRemoveClientCommander(),
            new DBSelectBankCommander(),
            new DBSelectClientCommander(),
            new DBDepositCommand(),
            new DBWithdrawCommand(),
            new DBTransferCommand(),
            new Command() {
                public void execute() {
                    System.exit(0);
                }
                public void printCommandInfo() {
                    System.out.println("Exit");
                }
            }
    };

    public static void main(String[] args) throws NotEnoughtFundsException, ClientNotFoundException, ClientExistsException {
        DBBankCommander.initCommands();
        Scanner readUserInput = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            dispActiveClient();
            for (int i = 0; i < commands.length; i++) {
                System.out.print(i + ") ");
                commands[i].printCommandInfo();
            }
            String command = readUserInput.nextLine();
            commandMap.get(command).execute();
        }
    }

    public static void dispActiveClient() {
        if(clientToTransfer != null) {
            System.out.printf("Active client: " + clientToTransfer + "\n");
        }
    }

    public static boolean isActiveClientChoosen(Client client) {
        if(client != null) {
            return true;
        }
        return false;
    }

    public static boolean activeBankChoosen(Bank bankToCheck ) {
        if(bankToCheck !=null) {
            return true;
        }
        return false;
    }

}
