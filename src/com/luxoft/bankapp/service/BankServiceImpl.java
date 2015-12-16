package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.*;

public class BankServiceImpl implements BankService {
    @Override
    public void addClient(Bank bank, Client client) throws ClientExistsException {
        bank.addClient(bank, client);
    }

    @Override
    public void removeClient(Bank bank, Client client) {

        if(bank.getClients().remove(client)) {
            System.out.println("Client removed ");
        }
        else {
            System.out.println("No client to remove ");
        }
    }

    @Override
    public void addAccount(Client client, Account account) {
        client.addAccount(account);
    }

    @Override
    public void setActiveAccoutn(Client client, Account account) {
        client.setActiveAccount(account);
    }
}
