package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Gender;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

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
		boolean flag = true;
		System.out.println("Add new client ");
		String name = null, firstName = null, sureName = null, email = null, phoneNumber = null;
		Gender gender= null;
		int chooseGender;
		float initialOverdraft = 0;
		String namePattern= "[a-zA-Z]*\\s[a-zA-Z]+";
		String emailPattern = "[a-zA-Z0-9\\.]*[@][a-zA-z0-9]*\\.[a-zA-Z]{2,}";
		String phonePattern = "[0-9]{9,9}";

		while (flag) {
			flag = true;
			while(flag) {
				System.out.println("Enter your name and sure name (separated with space) ");
				name = sc.nextLine();
				if(name.matches(namePattern)) {
					firstName = name.substring(0,name.indexOf(" "));
					firstName = firstName.trim();
					sureName = name.substring(name.indexOf(" "), name.length());
					sureName = sureName.trim();
					flag = false;
				}else {
					System.out.println("Wrong first name or sure name ");
				}
			}
			flag = true;
			while(flag) {
				System.out.println("Enter your email (ex. jSmith@yahoo.com ");
				email = sc.next();
				if(email.matches(emailPattern)) {
					flag = false;
				}else {
					System.out.println("Wrong email ");
				}
			}
			flag = true;
			while(flag) {
				System.out.println("Enter your phone number (ex. 512342112 ");
				phoneNumber = sc.next();
				if(phoneNumber.matches(phonePattern)) {
					flag = false;
				}else {
					System.out.println("Wrong number ");
				}
			}
			flag = true;
			while(flag) {
				System.out.println("You gender: \n1 - female \n2 - male");
				chooseGender = sc.nextInt();
				switch (chooseGender) {
					case 1:
						gender = Gender.FEMALE;
						flag = false;
						break;
					case 2:
						gender = Gender.MALE;
						flag = false;
						break;
					default:
						System.out.println("Wrong option ");
				}

			}

			flag = true;
			while(flag) {
				try{
					System.out.println("Enter overdraft: ");
					initialOverdraft = sc.nextFloat();

					flag = false;
				}catch (InputMismatchException e) {
					System.out.println(e.toString());
				}
			}

			try {
				bank.addClient(bank, new Client(firstName,sureName, email, phoneNumber, gender, initialOverdraft));

			}catch (ClientExistsException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
