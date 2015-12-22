package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.Gender;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class BankCommander {

    static Bank currentBank = new Bank();
	static Client currentClient = null;

    static Map<String, Command> commandMap = new TreeMap<>();


    public static void initCommands() {
        commandMap.put("0", new FindClientCommand(currentBank));
        commandMap.put("1", new GetAccountsCommand());
        commandMap.put("2", new WithdrawCommand());
        commandMap.put("3", new DepositCommand());
        commandMap.put("4", new TransferComand(currentBank));
        commandMap.put("5", new AddClientCommand(currentBank));
        commandMap.put("6", new Command() {
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

            new FindClientCommand(currentBank),
            new GetAccountsCommand(),
            new WithdrawCommand(),
            new DepositCommand(),
            new TransferComand(currentBank),
            new AddClientCommand(currentBank),
            new Command() {
                public void execute() {
                    System.exit(0);
                }
                public void printCommandInfo() {
                    System.out.println("Exit");
                }
            }
    };

    public static void main(String[] args) throws NotEnoughtFundsException, ClientNotFoundException {
        BankCommander.init();
        BankCommander.initCommands();
        Scanner readUserInput = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            for (int i = 0; i < commands.length; i++) {
                System.out.print(i + ") ");
                commands[i].printCommandInfo();
            }
            String command = readUserInput.nextLine();
            commandMap.get(command).execute();
        }
    }

    public static void init() {
        try {
            currentBank.addClient(currentBank,new Client("Lukasz","krakow","aa@gmail.com","123456789",Gender.MALE,2000));
            currentBank.addClient(currentBank,new Client("Marek","Krakow","aa@gmail.com","123456789",Gender.MALE,2000));
            try {
                currentBank.getClient("Lukasz").addAccount(new SavingAccount(0));
                currentBank.getClient("Lukasz").addAccount(new CheckingAccount(0, 2000));
                currentBank.getClient("Marek").addAccount(new SavingAccount(0));
                currentBank.getClient("Marek").addAccount(new CheckingAccount(0, 2000));
            } catch (ClientNotFoundException e) {
                e.printStackTrace();
            }

	        try {
		        currentBank.getClient("Lukasz").getAccounts().get(0).deposit(2000);
	        } catch (ClientNotFoundException e) {
		        e.printStackTrace();
	        }
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
    }
}
