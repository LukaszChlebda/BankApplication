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

            //new FindClientCommand(currentBank),
            new GetAccountsCommand(),
            new WithdrawCommand(),
            new DepositCommand(),
            new TransferComand(),
            // new AddClientCommand(currentBank),
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


        Scanner readUserInput = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
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
