package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.util.Scanner;

public class TransferComand implements Command{
	private Bank currentBank = null;
	private BankService bankServiceImpl = new BankServiceImpl();
	public TransferComand(Bank bank) {
		//this.bankServiceImpl = bankServiceImpl;
		this.currentBank = bank;
	}

	@Override
	public void execute() throws ClientNotFoundException, NotEnoughtFundsException {
		Client clientToTransfer = null;
		boolean flag = true;
		int choseAccount = 0;
		float amountToTransfer = 0;
		String name = null, accountFirstNameToTransfer = null, accountSureNameToTransfer = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("You are ");
		BankCommander.currentClient.printReport();
		System.out.println("\nChose account from which you would like to transfer money \n0 - Saving Account \n1 - Checking account \n\n3 - exit");

		while(flag) {
			choseAccount = sc.nextInt();
			switch (choseAccount) {
				case 0:
					BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(0));
					flag = false;
					break;
				case 1:
					BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(1));
					flag = false;
					break;
				case 2:
					flag = false;
					break;
				default:
					System.out.println("No option ");
					break;
			}
		}

		System.out.println("Enter name and surname of client you would like to transfer money (separate with space)");
		name = sc.nextLine();

		accountFirstNameToTransfer = name.substring(0, name.indexOf(" "));
		accountSureNameToTransfer = name.substring(name.indexOf(" "), name.length());
		accountFirstNameToTransfer = accountFirstNameToTransfer.trim();
		accountSureNameToTransfer = accountSureNameToTransfer.trim();

		//clientToTransfer = bankServiceImpl.getClient(currentBank, clientToFindName, clientToFindSureName);
		clientToTransfer = bankServiceImpl.getClient(currentBank, accountFirstNameToTransfer, accountSureNameToTransfer);

		System.out.println("Chose account from which you would like to transfer money \n0 - Saving Account \n1 - Checking account \n\n3 - exit");
		flag = true;
		while(flag) {
			choseAccount = sc.nextInt();
			switch (choseAccount) {
				case 0:
					clientToTransfer.setActiveAccount(BankCommander.currentClient.getAccounts().get(0));
					flag = false;
					break;
				case 1:
					clientToTransfer.setActiveAccount(BankCommander.currentClient.getAccounts().get(1));
					flag = false;
					break;
				case 2:
					flag = false;
					break;
				default:
					System.out.println("No option ");
					break;
			}
		}

		System.out.println("How much money you would like to transfer ");

		flag = true;

		while (flag) {
			amountToTransfer = sc.nextFloat();

			if(amountToTransfer > BankCommander.currentClient.getActiveAccount().getBalance()) {
				throw new NotEnoughtFundsException();
			}
			else{
				clientToTransfer.getActiveAccount().deposit(amountToTransfer);
				BankCommander.currentClient.getActiveAccount().withdraw(amountToTransfer);
				System.out.println("Transfer succeed" );
				flag = false;
			}

		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Transfer ");
	}

}
