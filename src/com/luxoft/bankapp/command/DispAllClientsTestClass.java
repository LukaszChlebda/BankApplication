package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.Iterator;

/**
 * Created by dvorak on 28.12.15.
 */
public class DispAllClientsTestClass implements Command{
    private Bank currentbank = null;

    public DispAllClientsTestClass(Bank currentBank) {
        this.currentbank = currentBank;
    }

    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException {
        Iterator<Client> iterator = currentbank.getClients().iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Display all comands ");
    }
}
