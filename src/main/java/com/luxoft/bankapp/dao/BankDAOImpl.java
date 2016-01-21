package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by LChlebda on 2016-01-20.
 */
public class BankDAOImpl extends BaseDAOImpl implements BankDAO{
    private Connection con;




    public Bank getBankByName(String name) throws DAOException, BankNotFoundException {
        Bank bank = new Bank(name);
        String GET_BANK_BY_NAME_QUERY = "SELECT ID, NAME FROM BANK WHERE name=?";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = con.prepareStatement(GET_BANK_BY_NAME_QUERY);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id  = rs.getInt("ID");
                bank.setId(id);
            } else {
                throw new BankNotFoundException(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        } finally {
            closeConnection();
        }
        return bank;
    }

    @Override
    public void saveBank(Bank bank) throws DAOException {
        String bankName = bank.getName();
        String INSERT_NEW_BANK_NAME_INTO_BANK_TABLE_QUERY= "INSERT INTO BANK(NAME) VALUES (?)";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = con.prepareStatement(INSERT_NEW_BANK_NAME_INTO_BANK_TABLE_QUERY);
            stmt.setString(1, bankName);
            stmt.execute();
            //ResultSet rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void removeBank(Bank bank) throws DAOException {

    }
}
