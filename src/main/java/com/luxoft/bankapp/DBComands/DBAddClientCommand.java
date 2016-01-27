package com.luxoft.bankapp.DBComands;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.dao.ClientDaoImpl;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Gender;

import java.util.Scanner;

/**
 * Created by Łukasz on 24.01.2016.
 */
public class DBAddClientCommand implements Command {

    private ClientDaoImpl clientDao = new ClientDaoImpl();
    private Scanner  userInput = new Scanner(System.in);
    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        if (isBankSelected()) {
            ClientDaoImpl clientDAO = new ClientDaoImpl();
            String name = getName();
            Gender gender = getGender();
            String email = getEmail();
            Client clientToAdd = new Client(name, gender, email);
            try {
                clientDAO.save(DBBankCommander.activeBank, clientToAdd);

                DBBankCommander.activeClient = clientToAdd;
                //DBBankCommander.activeBank.addClient(DBBankCommander.activeBank, DBBankCommander.activeClient);

                DBBankCommander.activeClient.addAccounts(clientDao.getClientAccounts(clientToAdd.getId()));


            } catch (DAOException e) {
                e.getMessage();
            }
        } else {
            System.err.println("Select Bank first!");
        }
    }

    private String getName() {
        System.out.println("Enter client name:");
        return getUserInput();
    }

    private Gender getGender() {
        Gender gender = null;
        System.out.println("Enter client gender:");

        switch (getUserInput()) {
            case "male":
                gender = Gender.MALE;
                break;
            case "female":
                gender = Gender.FEMALE;
                break;
            default:
                break;
        }

        return gender;
    }

    private String getEmail() {
        System.out.println("Enter client email:");
        return getUserInput();
    }

    private boolean isBankSelected() {
        return DBBankCommander.activeBank != null;
    }

    private String getUserInput() {
        return userInput.nextLine();
    }


    @Override
    public void printCommandInfo() {
        System.out.println("Ad client ");
    }
}
