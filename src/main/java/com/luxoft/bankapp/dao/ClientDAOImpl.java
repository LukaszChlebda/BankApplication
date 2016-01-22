package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by LChlebda on 2016-01-20.
 */
public class ClientDAOImpl extends BaseDAOImpl implements ClientDAO {

    private PreparedStatement preparedStatement = null;

    private static final String GET_CLIENT_BY_NAME_AND_BANK_QUERY = "SELECT c.ID, c.NAME, c.GENDER, c.EMAIL, b.NAME  FROM CLIENT c INNER JOIN "
            + "BANK b INNER JOIN ACCOUNTS a on a.BANK_ID = b.ID WHERE b.NAME =? AND c.NAME =?";


    private static final String GET_ALL_CLIENTS_FROM_CURRENT_BANK_QUERY = "SELECT c.ID, c.NAME, c.GENDER, c.EMAIL FROM CLIENT c " +
            "INNER JOIN BANKS_CLIENTS bc ON c.ID=bc.CLIENT_ID " +
            "WHERE bc.BANK_ID=?";

    private static final String SAVE_CLIENT_QUERY = "INSERT INTO CLIENT(NAME) VALUES(?)";

    private static final String DELETE_BANKS_CLIENTS_QUERY = "DELETE FROM BANKS_CLIENTS WHERE CLIENT_ID = ?";
    private static final String DELETE_CLIENT_QUERY = "DELETE FROM CLIENT WHERE NAME = ?";


    @Override
    public Client getClientByName(Bank bank, String clientNameToSearch) throws DAOException, ClientNotFoundException {

        Client foundClient = new Client();

        openConnection();

        try {
            preparedStatement = getConnection().prepareStatement(GET_CLIENT_BY_NAME_AND_BANK_QUERY);
            prepareFindClientStatement(bank, clientNameToSearch);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                getClientDataFromDB(foundClient, resultSet, id);
            } else {
                throw new ClientNotFoundException(clientNameToSearch);
            }
        } catch (SQLException | ClientNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return foundClient;
    }

    private void prepareFindClientStatement(Bank bankToSearch, String nameOfTheClientToSearch) throws SQLException {
        preparedStatement.setString(1, bankToSearch.getName());
        preparedStatement.setString(2, nameOfTheClientToSearch);

    }

    private void getClientDataFromDB(Client foundClient, ResultSet resultSet, int id) throws SQLException {
        foundClient.setId(id);
        String foundClientName = resultSet.getString("NAME");
        foundClient.setName(foundClientName);
        String foundClientGender = resultSet.getString("GENDER");
        foundClient.setGender(foundClientGender);
        String foundClientEmail = resultSet.getString("EMAIL");
        foundClient.setEmail(foundClientEmail);
    }


    @Override
    public List<Client> getAllClients(Bank bank) throws DAOException {

        List<Client> listOfClient = null;
        openConnection();

        try {
            preparedStatement = getConnection().prepareStatement(GET_ALL_CLIENTS_FROM_CURRENT_BANK_QUERY);
            prepareBankStatement(bank);
            ResultSet allClientsOfTheGivenBankResultSet = preparedStatement.executeQuery();

            while (allClientsOfTheGivenBankResultSet.next()) {
                Client clientToAdd = new Client(allClientsOfTheGivenBankResultSet.getString("NAME"));
                clientToAdd.setId(allClientsOfTheGivenBankResultSet.getInt("ID"));
                clientToAdd.setName(allClientsOfTheGivenBankResultSet.getString("NAME"));
                listOfClient.add(clientToAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return listOfClient;
    }

    private void prepareBankStatement(Bank bank) throws SQLException {
        preparedStatement.setInt(1, bank.getId());
    }

    @Override
    public void save(Client clientToSave) throws DAOException {
        openConnection();
        try {
            preparedStatement = getConnection().prepareStatement(SAVE_CLIENT_QUERY);
            prepareSaveClientStatement(clientToSave);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new ClientExistsException(clientToSave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClientExistsException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void prepareSaveClientStatement(Client clientToSave) throws SQLException {
        preparedStatement.setString(1, clientToSave.getName());
    }

    @Override
    public void remove(Client clientToRemove) throws DAOException {
        openConnection();
        try {
            preparedStatement = getConnection().prepareStatement(DELETE_BANKS_CLIENTS_QUERY);
            prepareDeleteBanksClientsStatement(clientToRemove);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new ClientNotFoundException(clientToRemove.getName());
            }

            preparedStatement = getConnection().prepareStatement(DELETE_CLIENT_QUERY);
            prepareDeleteClientStatement(clientToRemove);
            affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new ClientNotFoundException(clientToRemove.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void prepareDeleteBanksClientsStatement(Client clientToDelete) throws SQLException {
        preparedStatement.setString(1, clientToDelete.getName());
    }

    private void prepareDeleteClientStatement(Client clientToDelete) throws SQLException {
        preparedStatement.setInt(1, clientToDelete.getId());
    }
}
