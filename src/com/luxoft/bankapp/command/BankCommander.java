package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.Gender;

import java.util.Scanner;

/**
 * Created by LChlebda on 2015-12-17.
 */
public class BankCommander {

    static Bank currentBank = new Bank();
	static Client currentClient = null;


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
        Scanner readUserInput = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            for (int i = 0; i < commands.length; i++) {
                System.out.print(i + ") ");
                commands[i].printCommandInfo();
            }
            int command = readUserInput.nextInt();

            commands[command].execute();
        }
    }

    public static void init() {
	    //Defaults clients to test
        try {
            currentBank.addClient(currentBank,new Client("a","krakow","aa@gmail.com","123456789",Gender.MALE,2000));
            currentBank.addClient(currentBank,new Client("b","Krakow","aa@gmail.com","123456789",Gender.MALE,2000));
            //currentBank.getClients().get(0).getAccounts().get(0).deposit(2000);
	        try {
		        currentBank.getClient("a").getAccounts().get(0).deposit(2000);
	        } catch (ClientNotFoundException e) {
		        e.printStackTrace();
	        }
	        //currentBank.getClients().get(0).getAccounts().get(1).deposit(3000);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
    }
}
