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

			System.out.println("enter amount you would like to withdraw ");
			amountToWithdraw = sc.nextFloat();


			try{
				BankCommander.currentClient.getActiveAccount().withdraw(amountToWithdraw);
				BankCommander.currentClient.printReport();
			}catch (NotEnoughtFundsException e) {
				System.out.println(e.getMessage());
			}
//			if(amountToWithdraw > BankCommander.currentClient.getActiveAccount().getBalance()) {
//				System.out.println("Not enough money SORRY ");
//			}else {
//				System.out.println("Take money ");
//				BankCommander.currentClient.getActiveAccount().withdraw(amountToWithdraw);
//				System.out.println("\n");
//				BankCommander.currentClient.printReport();
//			}

		}else{
			System.out.println("No active account, find client first ");
		}


//		if(BankCommander.currentClient.getActiveAccount() != null) {
//			System.out.println("Withdraw Money: \nYou are: \n");
//			BankCommander.currentClient.printReport();
//
//			System.out.println("enter amount you would like to withdraw ");
//			amountToWithdraw = sc.nextFloat();
//
//			if(amountToWithdraw > BankCommander.currentClient.getActiveAccount().getBalance()) {
//				System.out.println("Not enough money SORRY ");
//			}else {
//				System.out.println("Take money ");
//				BankCommander.currentClient.getActiveAccount().withdraw(amountToWithdraw);
//				System.out.println("\n");
//				BankCommander.currentClient.printReport();
//			}
//
//		}else{
//			System.out.println("No active account, find client first ");
//		}


		
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Withdraw ");
	}
}
