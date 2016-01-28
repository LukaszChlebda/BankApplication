package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.BankException;
import com.luxoft.bankapp.exceptions.BankExistException;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by LChlebda on 2016-01-20.
 */
public class BankDAOImpl extends BaseDAOImpl implements BankDAO{

    private final String INSERT_NEW_BANK_NAME_INTO_BANK_TABLE_QUERY = "INSERT INTO BANK(NAME) VALUES (?)";
    private final String GET_BANK_BY_NAME_QUERY = "SELECT ID, NAME FROM BANK WHERE name=?";
    private final String REMOVE_BANK_BY_NAME_QUERY = "DELETE FROM BANK WHERE name=?";

    private PreparedStatement preparedStatement;

    public Bank getBankByName(String name) throws DAOException, BankNotFoundException {
        Bank bank = new Bank(name);
        openConnection();

        PreparedStatement stmt;
        try {
            openConnection();
            stmt = getConnection().prepareStatement(GET_BANK_BY_NAME_QUERY);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id  = rs.getInt("ID");
                bank.setId(id);
                bank.setName(name);
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
        openConnection();
        try {
            preparedStatement = getConnection().prepareStatement(INSERT_NEW_BANK_NAME_INTO_BANK_TABLE_QUERY);
            preparedStatement.setString(1, bank.getName());
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows <1) {
                throw new BankExistException(bank);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (BankExistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeBank(Bank bank) throws DAOException {
        openConnection();
        try {
            preparedStatement = getConnection().prepareStatement(REMOVE_BANK_BY_NAME_QUERY);
            preparedStatement.setString(1, bank.getName());
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows < 1) {
                throw new BankNotFoundException(bank.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        } catch (BankNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}
