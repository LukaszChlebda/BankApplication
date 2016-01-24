package com.luxoft.bankapp.DBComands;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDaoImpl;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;

import java.util.Scanner;

/**
 * Created by Łukasz on 24.01.2016.
 */
public class DBDepositCommand implements Command {
    private Scanner userInput = new Scanner(System.in);
    private ClientDAO clientDAO = new ClientDaoImpl();
    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        if(isActiveClientSet()) {
            System.out.println("Enter amount to deposit ");
            //clientDAO.
        }else {
            new DBSelectClientCommander();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Deposit ");
    }

    private boolean isActiveClientSet() {
        return DBBankCommander.activeClient != null;
    }
}
