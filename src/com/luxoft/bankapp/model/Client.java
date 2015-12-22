package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.*;
import com.luxoft.bankapp.service.Gender;

import java.util.*;

public class Client implements Report {

	private String name;
	private String email;
	private String phoneNumber;
	private String city;
	private float initialOverdraft;
    private Gender gender;
	private List<Account> accounts;
	private Account activeAccount;

	@Deprecated
    public Client(String firstName, Gender gender) {
        accounts = new ArrayList<>();
        this.gender = gender;

    }

	@Deprecated
    public Client(String firstName, Gender gender, float initialOverdraft) {
        accounts = new ArrayList<>();
        this.initialOverdraft = initialOverdraft;
        this.gender = gender;

    }

	public Client(String name,String city, String email, String phoneNumber, Gender gender, float initialOverdraft) {
		accounts = new ArrayList<>();
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.initialOverdraft = initialOverdraft;
		this.city = city;
//		Account savingAccount = new SavingAccount(0);
//		Account checkingAccount = new CheckingAccount(0, initialOverdraft);
//
//		accounts.add(savingAccount);
//		accounts.add(checkingAccount);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Client client = (Client) o;

		return name.equals(client.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void addAccount(Account accountToAdd) {
		accounts.add(accountToAdd);
	}

	public List<Account> getAccounts() {
		return Collections.unmodifiableList(accounts);
	}

	public Account getActiveAccount() {
		return activeAccount;
	}

	public void setActiveAccount(Account account) {
		this.activeAccount = account;
	}

    public String getClientSalutation () {
        return gender.getPrefix();
    }
	
	@Override
	public void printReport() {
		StringBuilder sb = new StringBuilder();
		sb.append("|----------------------------------------------------------\n")
				.append("|Client: \n")
				.append("|  ")
				.append(getClientSalutation()).append(getName())
                .append("\n")
				.append("|----------------------------------------------------------\n")
				.append("|Information: \n")
				.append("|  Email: " + getEmail() + "\n")
				.append("|  Phone number: " + getPhoneNumber() + "\n")
				.append("|  City: " + getCity() + "\n")
				.append("|----------------------------------------------------------\n")
				.append("|Account info: \n");

        System.out.print(sb.toString());
//		for (int i = 0; i < accounts.size(); i++) {
//			System.out.print("|  ");
//			accounts.get(i).printReport();
//		}
		for(Account account: getAccounts()) {
			System.out.print("|  ");
			account.printReport();
		}
		System.out.println("|----------------------------------------------------------");
	}


	@Override
	public String toString() {
//		StringBuilder sb = new StringBuilder();
//
//		sb.append(getName()).append(" \n");
//		System.out.print(sb.toString());
//		for (int i = 0; i < accounts.size(); i++) {
//			System.out.print(" ");
//			accounts.get(i).printReport();
//		}
//		return sb.toString();

		return getName();
	}
}
