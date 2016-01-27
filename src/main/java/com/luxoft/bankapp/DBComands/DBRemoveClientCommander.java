package com.luxoft.bankapp.DBComands;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDaoImpl;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;

import java.util.Scanner;

/**
 * Created by Łukasz on 24.01.2016.
 */
public class DBRemoveClientCommander implements Command {

    private ClientDAO clientDAO = new ClientDaoImpl();
    private Scanner userInput = new Scanner(System.in);

    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        if(DBBankCommander.activeBankChoosen(DBBankCommander.activeBank)) {
            System.out.printf("Enter client name ");
            try {
                clientDAO.remove(clientDAO.getClientByName(DBBankCommander.activeBank, userInput.next()));
            } catch (DAOException | ClientNotFoundException e) {
                e.printStackTrace();
            } finally {

            }
        }else {
            new DBSelectBankCommander().execute();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Remove client from DB");
    }


}
