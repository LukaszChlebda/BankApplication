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
    public static List<Client> listOfAllClients = new ArrayList<>();

    static Map<String, Command> commandMap = new TreeMap<>();


    public static void initCommands() {
        commandMap.put("0", new DBAddClientCommand());
        commandMap.put("1", new DBRemoveClientCommander());
        commandMap.put("2", new DBSelectBankCommander());
        commandMap.put("3", new DBSelectClientCommander());
        commandMap.put("4", new DepositCommand());
        commandMap.put("4", new Command() {
            @Override
            public void execute() throws ClientNotFoundException, NotEnoughtFundsException {
                System.exit(0);
            }

            @Override
            public void printCommandInfo() {
                System.out.println("Exit");
            }
        });
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
        System.out.println(commands.length);
        while (flag) {
            for (int i = 0; i < commands.length; i++) {
                System.out.print(i + ") ");
                commands[i].printCommandInfo();
            }
            String command = readUserInput.nextLine();
            commandMap.get(command).execute();
        }
    }

}
