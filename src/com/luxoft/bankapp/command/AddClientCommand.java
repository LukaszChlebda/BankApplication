package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Gender;

import java.util.Scanner;

public class AddClientCommand implements Command {

	private Bank bank = new Bank();

	public AddClientCommand(Bank bank) {
		this.bank = bank;
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Add new client: ");

	}

	@Override
	public void execute() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Your name ");
		String name = sc.next();

		try {
			bank.addClient(bank, new Client(name, Gender.MALE));

		}catch (ClientExistsException e) {
			System.out.println(e.getMessage());
		}
	}
}
