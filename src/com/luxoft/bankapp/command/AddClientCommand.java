package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Gender;

import java.util.Scanner;

public class AddClientCommand implements Command {

	Bank bank = new Bank();

	public AddClientCommand(Bank bank) {
		this.bank = bank;
	}
	@Override
	public void printCommandInfo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		Scanner sc = new Scanner(System.in);
		String name=null;

		System.out.println("Your name ");
		name = sc.next();

		try {
			bank.addClient(bank, new Client(name, Gender.MALE));
			//System.out.println(bank.getClient(0).printReport());
			bank.getClient(0).printReport();
			bank.getClient(0).getName();

		}catch (ClientExistsException e) {
			System.out.println(e.getMessage());
		}
	}
}
