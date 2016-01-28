package com.luxoft.bankapp.DBComands;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;

import java.util.Scanner;

/**
 * Created by LChlebda on 2016-01-25.
 */
public class DBChooseActiveAccount implements Command {
    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        setActiveAccount();
    }

    @Override
    public void printCommandInfo() {
        System.out.printf("Choose active account ");
    }


    public void setActiveAccount() {
        Scanner userInput = new Scanner(System.in);
        System.out.println(DBBankCommander.activeClient.getAccounts().size());
        System.out.println("Choose account \n");
        for (int i = 0; i<DBBankCommander.activeClient.getAccounts().size(); i++) {
            System.out.println(">> " + i + " " + DBBankCommander.activeClient.getAccounts().get(i).getAccountType());
        }
        String input = userInput.next();
        switch (input) {
            case "0":
                DBBankCommander.activeClient.setActiveAccount(DBBankCommander.activeClient.getAccounts().get(0));
                DBBankCommander.getLogger().fine("Chosen account type " + DBBankCommander.activeClient.getAccounts().get(0).getAccountType());
                break;
            case "1":
                DBBankCommander.activeClient.setActiveAccount(DBBankCommander.activeClient.getAccounts().get(1));
                DBBankCommander.getLogger().fine("Chosen account type " + DBBankCommander.activeClient.getAccounts().get(0).getAccountType());
                break;
            default:
                break;
        }
    }
}
