package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.List;
import java.util.Set;

/**
 * Created by LChlebda on 2016-01-20.
 */
public interface ClientDAO {
    public Client getClientByName(Bank bank, String name) throws DAOException, ClientNotFoundException;
    public List<Client> getAllClients(Bank bank) throws DAOException;
    public void save(Bank bank, Client client) throws DAOException;
    public void remove(Client client) throws DAOException;


}
