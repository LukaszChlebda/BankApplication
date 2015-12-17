package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.*;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.util.Iterator;

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

    @Override
    public Client getClient(Bank bank, String clientName) throws ClientNotFoundException {
//        Iterator<Client> iterator = (Iterator<Client>) bank.getClients();
//        int index;
//        while (iterator.hasNext()) {
//            if(iterator.next().getName().equals(clientName)) {
//                return (Client)iterator.next();
//            }else {
//                throw new ClientNotFoundException(clientName);
//            }
//        }
        boolean flag = true;
        Client clientToReturn = new Client("Lukasz1", Gender.MALE);
        int index=0;
        while(flag){
            if (bank.getClients().get(index).equals(clientName)) {
                return clientToReturn = bank.getClients().get(index);

            } else {
                throw new ClientNotFoundException(clientName);
            }
        }
        return null;
    }

    @Override
    public Client findClient(Bank bank, String clientName) {
        return null;
    }
}
