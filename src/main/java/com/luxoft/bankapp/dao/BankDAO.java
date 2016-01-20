package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

/**
 * Created by LChlebda on 2016-01-20.
 */
public interface BankDAO {
    public Bank getBankByName(String name) throws DAOException, BankNotFoundException;
    public void saveBank(Bank bank) throws DAOException;
    public void removeBank(Bank bank) throws DAOException;
}
