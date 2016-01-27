package com.luxoft.bankapp.DBComands;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.dao.BankDAO;
import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.dao.ClientDaoImpl;
import com.luxoft.bankapp.exceptions.*;

import java.util.Scanner;

/**
 * Created by Łukasz on 24.01.2016.
 */
public class DBSelectBankCommander implements Command {


    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        BankDAO bankDao = new BankDAOImpl();
        ClientDaoImpl clientDAO = new ClientDaoImpl();
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter bank name ");

        try {
            DBBankCommander.activeBank = bankDao.getBankByName(userInput.next());
            DBBankCommander.activeBank.addClients(clientDAO.getAllClients(DBBankCommander.activeBank));

            for(int i=0;i<DBBankCommander.activeBank.getClients().size(); i++) {
                DBBankCommander.activeBank.getClients().get(i).addAccounts(clientDAO.getClientAccounts(DBBankCommander.activeBank.getClients().get(i).getId()));
            }

        }catch (BankNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void printCommandInfo() {
        System.out.println("Select Bank ");
    }
}
