package com.luxoft.bankapp.DBComands;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.dao.ClientDaoImpl;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;

import java.util.Scanner;

/**
 * Created by Łukasz on 24.01.2016.
 */
public class DBDepositCommand implements Command {
    private Scanner userInput = new Scanner(System.in);
    private ClientDaoImpl clientDAO = new ClientDaoImpl();
    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        if(DBBankCommander.isActiveClientChoosen(DBBankCommander.activeClient)) {
            new DBChooseActiveAccount().execute();
                System.out.printf("Balance for account " + DBBankCommander.activeClient.getActiveAccount().getBalance());
                System.out.println("Enter amount to deposit ");
                float deposit = userInput.nextFloat();
                DBBankCommander.activeClient.getActiveAccount().deposit(deposit);
            System.out.println("After deposit " + DBBankCommander.activeClient.getActiveAccount().getBalance());
                try {
                    clientDAO.add(DBBankCommander.activeClient.getActiveAccount(), DBBankCommander.activeClient.getId());
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }else {
                new DBSelectClientCommander().execute();

        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Deposit ");
    }
}
