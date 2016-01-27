package com.luxoft.bankapp.DBComands;

import com.luxoft.bankapp.command.Command;
import com.luxoft.bankapp.dao.ClientDaoImpl;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.model.Client;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by LChlebda on 2016-01-25.
 */
public class DBTransferCommand implements Command {
    private String userToTransfer;
    private Scanner userInput = new Scanner(System.in);
    private ClientDaoImpl clientDAO = new ClientDaoImpl();
    private float balanceBeforeWithdraw = 0, balanceAfterWithdraw = 0;
    private Logger log = Logger.getLogger(DBTransferCommand.class.getName());

    @Override
    public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException {
        if(DBBankCommander.isActiveClientChoosen(DBBankCommander.activeClient)) {
            setActiveAccount(DBBankCommander.activeClient);
            System.out.println("Enter client whom you would like to transfer money ");
            userToTransfer = userInput.next();

            try {
                DBBankCommander.clientToTransfer = clientDAO.getClientByName(DBBankCommander.activeBank, userToTransfer);
                System.out.println("Client id "  + DBBankCommander.clientToTransfer.getId() );
                DBBankCommander.clientToTransfer.addAccounts(clientDAO.getClientAccounts(DBBankCommander.clientToTransfer.getId()));
            } catch (DAOException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
                e.printStackTrace();
            }

            setActiveAccount(DBBankCommander.clientToTransfer);

            System.out.println("Enter amount: ");
            userToTransfer = userInput.next();

            //balanceBeforeWithdraw = DBBankCommander.activeClient.getActiveAccount().getBalance();

            try {
                DBBankCommander.activeClient.getActiveAccount().withdraw(Float.valueOf(userToTransfer));
                clientDAO.add(DBBankCommander.activeClient.getActiveAccount(), DBBankCommander.activeClient.getId());
                DBBankCommander.clientToTransfer.getActiveAccount().deposit(Float.valueOf(userToTransfer));
                clientDAO.add(DBBankCommander.clientToTransfer.getActiveAccount(), DBBankCommander.clientToTransfer.getId());
            } catch (DAOException | NotEnoughtFundsException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
                e.printStackTrace();
            }

        }else{
            new DBSelectClientCommander().execute();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Transfer money ");
    }

    public void setActiveAccount(Client client) {
        Scanner userInput = new Scanner(System.in);
        System.out.println(client.getAccounts().size());
        System.out.println("Choose account \n");
        for (int i = 0; i<client.getAccounts().size(); i++) {
            System.out.println(">> " + i + " " + client.getAccounts().get(i).getAccountType());
        }
        String input = userInput.next();
        switch (input) {
            case "0":
                client.setActiveAccount(client.getAccounts().get(0));
                break;
            case "1":
                client.setActiveAccount(client.getAccounts().get(1));
                break;
            default:
                break;
        }
    }
}
