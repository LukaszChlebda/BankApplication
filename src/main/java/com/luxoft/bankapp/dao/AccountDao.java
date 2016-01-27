package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

import java.util.List;

/**
 * Created by LChlebda on 2016-01-20.
 */
public interface AccountDao {
    public void save(Client client, Account account) throws DAOException;
    public void add(Account account, int clientId) throws DAOException;
    public void removeByClientId(int idClient) throws DAOException;
    public List<Account> getClientAccounts(int idClient) throws DAOException;
}