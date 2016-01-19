package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.server.BankServer;
import com.luxoft.bankapp.server.BankServerThreaded;

public class BankApplication {



	static BankService bService = new BankServiceImpl();
	static Bank bank;

	public BankApplication() {
		bank = BankApplication.initialize(bService);
	}

	public BankApplication(Bank bank) {
		this.bank = bank;
	}
	public static void main(String[] args) throws NotEnoughtFundsException {

		BankServer bankServer = new BankServer(bank);

		//bankServer.run();
		BankServerThreaded bankServerThreaded = new BankServerThreaded(bank);
		//bankServerThreaded.start();

//        printBankReport(bank);
//        modifyBank(bank);
//		System.out.println("AFTER MODIFICATIONS");
//		printBankReport(bank);
		if(args.length>0) {
			if(args[0].equals("-report"))
				printBankReport(bank);
		}
	}
	
	public static Bank initialize(BankService bService) {

		Client client1 = new Client("Lukasz","Krakow","lukasz@gmail.com","123456789", Gender.MALE,1000);
		Client client2 = new Client("Jarek","Krakow","Jarek@gmail.com","123456789", Gender.MALE,500);
        Client client3 = new Client("Przemek","Warszawa","przemo@gmail.com","123456789", Gender.MALE,200);

		SavingAccount savingAccount = new SavingAccount(5000);
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

		Account savingAccount1 = new SavingAccount(52000);
		Account checkingAccount1 = new CheckingAccount(1000,1000);
        bService.addAccount(client1,savingAccount1);
        bService.addAccount(client1, checkingAccount1);
		bService.setActiveAccoutnt(client1, checkingAccount1);
		//-----------------------------------------------------
		Account savingAccount2 = new SavingAccount(1000);
		Account checkingAccount2 = new CheckingAccount(1000);
		bService.addAccount(client2,savingAccount2);
		bService.addAccount(client2, checkingAccount2);
		bService.setActiveAccoutnt(client2, savingAccount2);
		//-----------------------------------------------------
		Account savingAccount3 = new SavingAccount(1000);
		Account checkingAccount3 = new CheckingAccount(1000,1000);
		bService.addAccount(client3,savingAccount3);
		bService.addAccount(client3, checkingAccount3);
		bService.setActiveAccoutnt(client3, checkingAccount3);

		return bank;
	}
	
	public static void modifyBank(Bank bank) throws NotEnoughtFundsException {

        for(Client client: bank.getClients()) {
//	        client.getActiveAccount().deposit(1000 + (float)(Math.random()*10000));
			client.getActiveAccount().deposit(1000);
	        //client.getActiveAccount().withdraw(1000);

        }
		try {
			bank.getClient("Przemek").getActiveAccount().withdraw(2300);
			bank.getClient("Lukasz").getActiveAccount().withdraw(2500);
		} catch (ClientNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public static void printBankReport(Bank bank) {
		BankReport bankReport=new BankReport();
		bankReport.getNumberOfClients(bank);
		bankReport.getAccountsNumber(bank);
		bankReport.getClientsSorted(bank);
		bankReport.getBankCreditSum(bank);
		bankReport.getClientsByCity(bank);
		//bank.printReport();
	}

	public static Bank getBankInstance() {
		return bank;
	}
}
