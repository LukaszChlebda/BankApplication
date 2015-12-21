package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.*;
import com.luxoft.bankapp.service.Gender;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client implements Report {

	private String name;
	private String firstName;
	private String sureName;
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
        this.firstName = firstName;
        this.gender = gender;

    }

	@Deprecated
    public Client(String firstName, Gender gender, float initialOverdraft) {
        accounts = new ArrayList<>();
        this.firstName = firstName;
        this.initialOverdraft = initialOverdraft;
        this.gender = gender;

    }

	public Client(String name,String city, String email, String phoneNumber, Gender gender, float initialOverdraft) {
		accounts = new ArrayList<>();
		this.name = name;
		this.firstName = firstName;
		this.sureName = sureName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.initialOverdraft = initialOverdraft;
		this.city = city;
		Account savingAccount = new SavingAccount(0);
		Account checkingAccount = new CheckingAccount(0, initialOverdraft);

		accounts.add(savingAccount);
		accounts.add(checkingAccount);
	}


//	@Override
//	public boolean equals(Object o) {
//		if (this == o) return true;
//		if (o == null || getClass() != o.getClass()) return false;
//
//		Client client = (Client) o;
//
//		if (!firstName.equals(client.firstName)) return false;
//		return sureName.equals(client.sureName);
//
//	}
//
//	@Override
//	public int hashCode() {
//		int result = firstName.hashCode();
//		result = 31 * result + sureName.hashCode();
//		return result;
//	}


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

	public String getSureName() {
		return sureName;
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


	public String getFirstName() {
		return firstName;
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
		for (int i = 0; i < accounts.size(); i++) {
			System.out.print("|  ");
			accounts.get(i).printReport();
		}
		System.out.println("|----------------------------------------------------------");
	}
}
