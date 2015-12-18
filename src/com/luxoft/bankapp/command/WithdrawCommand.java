package com.luxoft.bankapp.command;

import com.luxoft.bankapp.model.Bank;

public class WithdrawCommand implements Command{

	private Bank bank = new Bank();

	@Override
	public void execute() {
		System.out.println("Withdraw : ");
		
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Withdraw");



	}
}
