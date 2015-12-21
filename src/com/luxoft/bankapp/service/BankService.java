package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.*;

public interface BankService {

    public void addClient(Bank bank, Client client) throws ClientExistsException;
    public void removeClient(Bank bank, Client client);
    public void addAccount(Client client, Account account);
    public void setActiveAccoutnt(Client client, Account account);
    public Client getClient(Bank bank, String clientName) throws ClientNotFoundException;
}
