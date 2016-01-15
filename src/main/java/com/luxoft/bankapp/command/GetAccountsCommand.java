package com.luxoft.bankapp.command;

public class GetAccountsCommand implements Command{

	@Override
	public void execute() {
		if(BankCommander.currentClient != null) {
			BankCommander.currentClient.printReport();
		}else {
			System.out.println("No active client, choose client first ");
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Get accounts ");
	}
}
