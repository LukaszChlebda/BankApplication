package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.Bank;

/**
 * Created by LChlebda on 2015-12-18.
 */
public class DispClientsTestCLass implements Command {
    private Bank bank = null;

    DispClientsTestCLass(Bank bank) {
        this.bank = bank;
    }

    public void execute() {
        for (int i = 0; i < bank.getClients().size(); i++) {
           try {
               bank.getClient(i).printReport();
           }catch (ClientNotFoundException e) {
               System.out.println(e.getMessage());
           }
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Test clients");
    }
}
