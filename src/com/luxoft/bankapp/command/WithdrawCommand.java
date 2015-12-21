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
		boolean flag = true;

		if(BankCommander.currentClient.getActiveAccount() != null) {
			System.out.println("Withdraw Money: \nYou are: \n");
			BankCommander.currentClient.printReport();
			System.out.println("Choose account to withdraw: \n0 - Saving account \n1 - Checking account ");


			System.out.println("enter amount you would like to withdraw ");
			amountToWithdraw = sc.nextFloat();


			try{
				BankCommander.currentClient.getActiveAccount().withdraw(amountToWithdraw);
				BankCommander.currentClient.printReport();
			}catch (NotEnoughtFundsException e) {
				System.out.println(e.getMessage());
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
