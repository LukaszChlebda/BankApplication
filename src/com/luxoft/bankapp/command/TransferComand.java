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
		boolean doTransfer = false;
		String name = null, accountFirstNameToTransfer = null, accountSureNameToTransfer = null;
		Scanner sc = new Scanner(System.in);
		if(BankCommander.currentClient != null) {
			System.out.println("You are ");
			BankCommander.currentClient.printReport();
			System.out.println("\nChose account from which you would like to transfer money " +
					"\n0 - Saving Account \n1 - Checking account \n\n2 - exit");

			while (flag) {
				choseAccount = sc.nextInt();
				switch (choseAccount) {
					case 0:
						BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(0));
						doTransfer = true;
						flag = false;
						break;
					case 1:
						BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(1));
						doTransfer = true;
						flag = false;
						break;
					case 2:
						doTransfer = false;
						flag = false;
						break;
					default:
						System.out.println("No option ");
						break;
				}
			}
			if (doTransfer) {


				//System.out.println("Enter name and surname of client you would like to transfer money (separate with space)");
				System.out.println("Enter name ");
				accountFirstNameToTransfer = sc.next();
				System.out.println("Enter sure name ");
				accountSureNameToTransfer = sc.next();

				accountFirstNameToTransfer = accountFirstNameToTransfer.trim();
				accountSureNameToTransfer = accountSureNameToTransfer.trim();

				try {
					clientToTransfer = bankServiceImpl.getClient(currentBank, accountFirstNameToTransfer, accountSureNameToTransfer);
					clientToTransfer.printReport();
				} catch (ClientNotFoundException e) {
					//doTransfer = false;
					System.out.println(e.getMessage());

				}

				//clientToTransfer = bankServiceImpl.getClient(currentBank, accountFirstNameToTransfer, accountSureNameToTransfer);

				System.out.println("Chose account from which you would like to transfer money \n0 - Saving Account \n1 - Checking account \n\n2 - exit");
				flag = true;
				while (flag) {
					choseAccount = sc.nextInt();
					switch (choseAccount) {
						case 0:
							//clientToTransfer.setActiveAccount(BankCommander.currentClient.getAccounts().get(0));
							clientToTransfer.setActiveAccount(clientToTransfer.getAccounts().get(0));
							doTransfer = true;
							flag = false;
							break;
						case 1:
							//clientToTransfer.setActiveAccount(BankCommander.currentClient.getAccounts().get(1));
							clientToTransfer.setActiveAccount(clientToTransfer.getAccounts().get(1));
							doTransfer = true;
							flag = false;
							break;
						case 2:
							doTransfer = false;
							flag = false;
							break;
						default:
							System.out.println("No option ");
							break;
					}
				}

				System.out.println("How much money you would like to transfer ");

				flag = true;

				while (flag && doTransfer) {
					amountToTransfer = sc.nextFloat();

					if (amountToTransfer > BankCommander.currentClient.getActiveAccount().getBalance()) {
						throw new NotEnoughtFundsException();
					} else {
						clientToTransfer.getActiveAccount().deposit(amountToTransfer);
						BankCommander.currentClient.getActiveAccount().withdraw(amountToTransfer);
						System.out.println("Transfer succeed");
						flag = false;
					}

				}
			}
		}else {
			System.out.println("No active account, find client first ");
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Transfer ");
	}

}
