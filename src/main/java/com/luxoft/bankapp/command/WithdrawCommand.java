package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.model.Bank;

import java.util.Scanner;

public class WithdrawCommand implements Command{

	private Bank bank = new Bank();

	@Override
	public void execute() throws NotEnoughtFundsException {
		Scanner sc = new Scanner(System.in);
		float amountToWithdraw = 0;
		int chooseAccount = 0;
		boolean flag = true, doWithdraw = false;
		if(BankCommander.currentClient != null) {
			System.out.println("Withdraw Money: \nYou are: \n");
			BankCommander.currentClient.printReport();
			System.out.println("Choose account to withdraw: \n0 - Saving account \n1 - Checking account ");
			while (flag) {
				chooseAccount = sc.nextInt();
				switch (chooseAccount) {
					case 0:
						BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(0));
						doWithdraw = true;
						flag = false;
						break;
					case 1:
						BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(1));
						doWithdraw = true;
						flag = false;
						break;
					case 2:
						doWithdraw = false;
						flag = false;
						break;
					default:
						System.out.println("No option ");
						break;

				}
			}

			if(doWithdraw) {
				System.out.println("enter amount you would like to withdraw ");
				amountToWithdraw = sc.nextFloat();

				try {
					BankCommander.currentClient.getActiveAccount().withdraw(amountToWithdraw);
					BankCommander.currentClient.printReport();
				} catch (NotEnoughtFundsException e) {
					System.out.println(e.getMessage());
				}
			}

		}else{
			System.out.println("No active account, find client first ");
		}

	}

	@Override
	public void printCommandInfo() {
		System.out.println("Withdraw ");
	}
}
