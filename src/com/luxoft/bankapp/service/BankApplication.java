package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.exceptions.OverDraftLimitExceededException;
import com.luxoft.bankapp.model.*;
import java.util.*;

public class BankApplication {

	public static void main(String[] args) throws NotEnoughtFundsException {
        BankService bService = new BankServiceImpl();
        Bank bank = BankApplication.initialize(bService);

        printBankReport(bank);
        modifyBank(bank);
        printBankReport(bank);


        //bService.removeClient(bank, bank.getClient(1));
        printBankReport(bank);
	}
	
	public static Bank initialize(BankService bService) {

		Client client1 = new Client("Lukasz", Gender.MALE);
		Client client2 = new Client("Jarek", Gender.MALE);
        Client client3 = new Client("Krzysiek", Gender.MALE);
        Client client4 = new Client("Test", Gender.FEMALE);

		SavingAccount savingAccount1 = new SavingAccount(1000);
		SavingAccount savingAccount2 = new SavingAccount(1000);
		CheckingAccount checkingAccount = new CheckingAccount(1000);

        Bank bank = new Bank();

        try {
			bService.addClient(bank, client1);
		} catch (ClientExistsException e) {
			System.out.println(e.getMessage());
		}
        try {
			bService.addClient(bank, client2);
		} catch (ClientExistsException e) {
			System.out.println(e.getMessage());
		}
        try {
			bService.addClient(bank, client3);
		} catch (ClientExistsException e) {
			System.out.println(e.getMessage());
		}
        try {
			bService.addClient(bank, client4);
		} catch (ClientExistsException e) {
			System.out.println(e.getMessage());
		}
        bService.addAccount(client1, savingAccount1);
        bService.addAccount(client1, checkingAccount);
        bService.addAccount(client1, checkingAccount);
        bService.addAccount(client2, savingAccount1);
        bService.addAccount(client2, checkingAccount);
        bService.addAccount(client3, savingAccount2);
        bService.addAccount(client3, checkingAccount);
        bService.addAccount(client4, savingAccount2);
        bService.addAccount(client4, checkingAccount);
		return bank;
	}
	
	public static void modifyBank(Bank bank) throws NotEnoughtFundsException {
		List<Client> listOfClients = bank.getClients();
        System.out.println("Client list size: " + listOfClients.size());
        Random rand = new Random();
        for(int i=0; i<bank.getClients().size(); i++) {
            
//            bank.getClient(i).getAccounts().get(0).deposit(5000);
//            bank.getClient(i).getAccounts().get(1).deposit(8000);
//
//            try {
//            	bank.getClient(i).getAccounts().get(0).withdraw(6000);
//            }catch(NotEnoughtFundsException e) {
//            	System.out.println(e.getMessage());
//            }
//
//            try {
//            	bank.getClient(i).getAccounts().get(1).withdraw(11000);
//            }catch(OverDraftLimitExceededException e) {
//            	System.out.println(e.getMessage());
//            }
            
        }
	}
	
	public static void printBankReport(Bank bank) {
		bank.printReport();
	}

}
