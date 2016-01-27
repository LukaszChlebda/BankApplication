package com.luxoft.bankapp.DBComands;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.dao.ClientDaoImpl;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by LChlebda on 2016-01-25.
 */
public class DBWithdrawCommand implements Command{

    private Scanner userInput = new Scanner(System.in);
    private float amountToWithdraw;
    private ClientDaoImpl clientDao = new ClientDaoImpl();
    private Logger log = Logger.getLogger(DBWithdrawCommand.class.getName());

    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        if(DBBankCommander.isActiveClientChoosen(DBBankCommander.activeClient)) {
            new DBChooseActiveAccount().execute();
            System.out.println("Enter amount to withdraw ") ;
            amountToWithdraw = userInput.nextFloat();


            try {
                DBBankCommander.activeClient.getActiveAccount().withdraw(amountToWithdraw);
                clientDao.add(DBBankCommander.activeClient.getActiveAccount(), DBBankCommander.activeClient.getId());
            } catch (DAOException | NotEnoughtFundsException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
                e.getMessage();
            }

        }else {
            new DBSelectClientCommander().execute();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Withdraw ");
    }
}
