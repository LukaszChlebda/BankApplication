package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.DAOException;

import java.sql.Connection;

/**
 * Created by LChlebda on 2016-01-20.
 */
public interface BaseDAO {
    public Connection openConnection() throws DAOException;
    public void closeConnection();
}
