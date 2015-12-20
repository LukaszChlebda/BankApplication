package com.luxoft.bankapp.command;

import java.util.Scanner;

public class DepositCommand implements Command {

	@Override
	public void execute() {
		StringBuilder sb = new StringBuilder();
		Scanner sc = new Scanner(System.in);
		int whereDeposit = 0, index=0;
		float amountToDeposit = 0;
		boolean flag = false;
		boolean doDeposit = false;
		int[] howMenyAccounts;
		if(BankCommander.currentClient != null) {
			howMenyAccounts = new int[BankCommander.currentClient.getAccounts().size()];

			sb.append("Deposit money \n");

			for (int i = 0; i < BankCommander.currentClient.getAccounts().size(); i++) {
				sb.append(i).append(" - ").append(BankCommander.currentClient.getAccounts().get(i)).append(" \n");
				index++;
			}

			System.out.println(sb.toString() + "\n"+ index + " - exit \n");
			flag = true;
			do{
				whereDeposit = sc.nextInt();

				switch (whereDeposit) {
					case 0:
						BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(0));
						doDeposit = true;
						flag = true;
						break;
					case 1:
						BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(1));
						doDeposit = true;
						flag = true;
						break;
					case 2:
						doDeposit = false;
						flag = true;
						break;
					default:
						System.out.println("No option \nRepeat operation\n");
						break;
				}

			}while (doDeposit && !flag);

			if(doDeposit) {
				System.out.println("How many mny you would like to deposit ");
				System.out.println("Test accounts " + BankCommander.currentClient.getAccounts().size());

				amountToDeposit = sc.nextFloat();

				BankCommander.currentClient.getActiveAccount().deposit(amountToDeposit);

				System.out.println(" Money has been transfer to ");
				BankCommander.currentClient.getActiveAccount().printReport();
				System.out.println("account ");
			}
		}else {
			System.out.println("There is no active client. Please find client first ");
		}

		
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Add deposit");
	}

}
