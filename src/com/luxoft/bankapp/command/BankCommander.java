package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exceptions.ClientExistsException;
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

    static Command[] commands = {
           // new AddClientCommand(currentBank),
          //  new FindClientCommand(currentBank),
            new GetAccountsCommand(),
            new Command() {
                public void execute() {
                    System.exit(0);
                }
                public void printCommandInfo() {
                    System.out.println("Exit");
                }
            }
    };

    public static void main(String[] args) {
        Bank currentBank = new Bank();

        BankService bankService = new BankServiceImpl();
        Client client = new Client("Lukasz", Gender.MALE);
        SavingAccount savingAccount1 = new SavingAccount(1000);
        SavingAccount savingAccount2 = new SavingAccount(1000);


        try {
            bankService.addClient(currentBank, client);
        }catch(ClientExistsException e) {
            System.out.println(e.getMessage());
        }
        //currentBank = bankService
        //currentBank.addClient();
        //client.addAccount();

        Scanner readUserInput = new Scanner(System.in);
        while (true) {
            for (int i = 0; i < commands.length; i++) {
                System.out.print(i+") ");
                commands[i].printCommandInfo();
            }
            int commandString = readUserInput.nextInt();
            int command=0; // initialize command with commandString
            commands[commandString].execute();
        }
    }

}
