package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;

import java.util.List;

/**
 * Created by LChlebda on 2016-01-20.
 */
public interface AccountDAO {
    public void save(Account account) throws DAOException;
    public void add(Account account) throws DAOException;
    public void removeByClientId(int idClient) throws DAOException;
    public List<Account> getClientAccounts(int idClient) throws DAOException;
}
