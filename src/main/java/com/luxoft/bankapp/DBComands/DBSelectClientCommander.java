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
public class DBSelectClientCommander implements Command {
    private boolean activebankChoosen = false;
    private ClientDAO clientDao = new ClientDaoImpl();
    private Scanner userInput = new Scanner(System.in);
    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        if(DBBankCommander.activeBankChoosen(DBBankCommander.activeBank)) {
            System.out.println("Enter client name ");
            try {
//                DBBankCommander.clientToTransfer = clientDao.getClientByName(DBBankCommander.activeBank, userInput.next());
                DBBankCommander.activeClient = DBBankCommander.activeBank.getClient(userInput.next());
            } catch (ClientNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }else {
            new DBSelectBankCommander().execute();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Select client ");
    }


}
