package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LChlebda on 2016-01-20.
 */
public class ClientDaoImpl extends BaseDAOImpl implements ClientDAO, AccountDao {

    private PreparedStatement preparedStatement = null;

    private static final String GET_CLIENT_BY_NAME_AND_BANK_QUERY = "SELECT c.ID, c.NAME, c.GENDER, c.EMAIL, b.NAME  FROM CLIENT c INNER JOIN "
            + "BANK b INNER JOIN ACCOUNT a on a.BANK_ID = b.ID WHERE b.NAME =? AND c.NAME =?";

    private final String GET_NEW_ADDED_CLIENT_ID = "SELECT ID FROM CLIENT WHERE NAME = ?";

    private static final String GET_ALL_CLIENTS_FROM_CURRENT_BANK_QUERY = "SELECT c.ID, c.NAME, c.GENDER, c.EMAIL, b.NAME FROM CLIENT c Inner JOIN BANK b On  ACCOUNT.ID=c.ACCOUNT_ID Join BANK b on ACCOUNT.BANK_ID = b.ID WHERE b.NAME = ?";

    private static final String SAVE_CLIENT_QUERY = "INSERT INTO CLIENT(NAME, GENDER, EMAIL) VALUES(?,?,?)";

    private static final String DELETE_CLIENT_QUERY = "DELETE FROM CLIENT WHERE NAME = ?";
    private final String SAVE_ACCOUNT_QUERY = "INSERT INTO ACCOUNT(BANK_ID, CLIENT_ID, ACCOUNT_TYPE, BALANCE, OVERDDRAFT) VALUES (?,?,?,?,?)";
    private final String REMOVE_ACCOUNT_BY_CLIENT_ID_QUERY = "DELETE FROM ACCOUNT WHERE CLIENT_ID=?";
    private final String GET_ALL_CLIENT_ACCOUNTS_QUERY = "SELECT ACCOUNT_TYPE, BALANCE, OVERDDRAFT FROM ACCOUNT WHERE CLIENT_ID =?";

    private final String UPDATE_ACCOUNT_QUERY = "UPDATE ACCOUNT SET BALANCE=? WHERE Id = ?";

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

        List<Client> listOfClient = new ArrayList<>();
        openConnection();

        try {
            preparedStatement = getConnection().prepareStatement(GET_ALL_CLIENTS_FROM_CURRENT_BANK_QUERY);
            prepareBankStatement(bank);
            ResultSet allClientsOfTheGivenBankResultSet = preparedStatement.executeQuery();

            while (allClientsOfTheGivenBankResultSet.next()) {
                //Client clientToAdd = new Client(allClientsOfTheGivenBankResultSet.getString("NAME"));
                Client clientToAdd = new Client();

                int id = allClientsOfTheGivenBankResultSet.getInt("ID");

                clientToAdd.setId(id);

                String name = allClientsOfTheGivenBankResultSet.getString("NAME");
                clientToAdd.setName(name);
                String gender = allClientsOfTheGivenBankResultSet.getString("GENDER");
                clientToAdd.setGender(gender);
                String email = allClientsOfTheGivenBankResultSet.getString("EMAIL");
                clientToAdd.setEmail(email);

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
        preparedStatement.setString(1, bank.getName());
    }

    @Override
    public void save(Bank  bank, Client clientToSave) throws DAOException {
        int addedClientId = 0;
        openConnection();
        try {
            preparedStatement = getConnection().prepareStatement(SAVE_CLIENT_QUERY);
            prepareSaveClientStatement(clientToSave);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new ClientExistsException(clientToSave);
            }

            preparedStatement = getConnection().prepareStatement(GET_NEW_ADDED_CLIENT_ID);
            preparedStatement.setString(1,clientToSave.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                addedClientId = resultSet.getInt("ID");
            }

            clientToSave.setId(addedClientId);

            preparedStatement = getConnection().prepareStatement(SAVE_ACCOUNT_QUERY);
            prepareAddDefaultAccounts(bank, clientToSave, "s");
            preparedStatement.executeUpdate();

            preparedStatement = getConnection().prepareStatement(SAVE_ACCOUNT_QUERY);
            prepareAddDefaultAccounts(bank, clientToSave, "c");
            preparedStatement.executeUpdate();

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
        preparedStatement.setString(2, clientToSave.getGender());
        preparedStatement.setString(3, clientToSave.getEmail());
    }
    private void prepareAddDefaultAccounts(Bank bank, Client client, String accountType) throws SQLException{
        preparedStatement.setInt(1, bank.getId());
        preparedStatement.setInt(2, client.getId());
        preparedStatement.setString(3, accountType);
        preparedStatement.setFloat(4, 0f);
        preparedStatement.setFloat(5,2000f);
    }

    @Override
    public void remove(Client clientToRemove) throws DAOException {
        openConnection();
        try {
            removeByClientId(clientToRemove.getId());
            preparedStatement = getConnection().prepareStatement(DELETE_CLIENT_QUERY);
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

    @Override
    public void save(Client client, Account account) throws DAOException {
        openConnection();
        try{
            preparedStatement = getConnection().prepareStatement(SAVE_ACCOUNT_QUERY);
            prepareSaveAccountStatement(client, account);
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            System.out.println(e);
        }finally {
            closeConnection();
        }
    }
    private void prepareSaveAccountStatement(Client client, Account account) throws SQLException {
        preparedStatement.setInt(1, client.getBankId());
        preparedStatement.setInt(2, client.getAccountId());
        preparedStatement.setString(3, account.getAccountType());
        preparedStatement.setFloat(4, account.getBalance());
        preparedStatement.setFloat(5, account.getOverdraft());
    }

    @Override
    public void add(Account client) throws DAOException {
        openConnection();
        try{
            preparedStatement = getConnection().prepareStatement(UPDATE_ACCOUNT_QUERY);
            prepareAddBalance(client);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void prepareAddBalance(Account account) throws SQLException{
        preparedStatement.setFloat(1,account.getBalance());
        preparedStatement.setInt(2,account.getId());
    }

    @Override
    public void removeByClientId(int idClient) throws DAOException {
        openConnection();
        try{
            preparedStatement = getConnection().prepareStatement(REMOVE_ACCOUNT_BY_CLIENT_ID_QUERY);
            prepareDeleteAccountStatement(idClient);
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows < 1) {
                throw new ClientNotFoundException();
            }
        }catch (SQLException e) {
            System.out.println(e);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void prepareDeleteAccountStatement(int clientId) throws SQLException{
        preparedStatement.setInt(1, clientId);
    }

    @Override
    public List<Account> getClientAccounts(int idClient) throws DAOException {
        List<Account> allClientAccounts = new ArrayList<>();
        Account accountToAdd = null;
        openConnection();

        try{
            preparedStatement.getConnection().prepareStatement(GET_ALL_CLIENT_ACCOUNTS_QUERY);
            prepareGetAllClientAccountsStatement(idClient);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                int bankID= resultSet.getInt("BANK_ID");
                int clientId = resultSet.getInt("CLIENT_ID");
                float balance = resultSet.getFloat("BALANCE");
                float overdraft = resultSet.getFloat("OVERDDRAFT");

                switch (resultSet.getString("ACCOUNT_TYPE")) {
                    case "s":
                        accountToAdd = new SavingAccount();
                        break;
                    case "c":
                        accountToAdd = new CheckingAccount();
                        break;
                    default:
                        break;
                }

                accountToAdd.setBanId(bankID);
                accountToAdd.setClientID(clientId);
                accountToAdd.setBalance(balance);
                accountToAdd.setOverdraft(overdraft);
                allClientAccounts.add(accountToAdd);
            }

        }catch (SQLException e) {
            System.out.println(e);
        }finally {
            closeConnection();
        }

        return allClientAccounts;
    }

    private void prepareGetAllClientAccountsStatement(int idClient) throws SQLException {
        preparedStatement.setInt(1, idClient);

    }
}
