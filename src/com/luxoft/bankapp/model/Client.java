package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.*;
import com.luxoft.bankapp.service.Gender;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client implements Report {

	private String firstName;
	private String sureName;
	private String email;
	private String phoneNumber;
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

	public Client(String firstName, String sureName, String email, String phoneNumber, Gender gender, float initialOverdraft) {
		accounts = new ArrayList<>();
		this.firstName = firstName;
		this.sureName = sureName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.initialOverdraft = initialOverdraft;
		Account savingAccount = new SavingAccount(0);
		Account checkingAccount = new CheckingAccount(0, initialOverdraft);

		accounts.add(savingAccount);
		accounts.add(checkingAccount);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (Float.compare(client.initialOverdraft, initialOverdraft) != 0) return false;
        if (firstName != null ? !firstName.equals(client.firstName) : client.firstName != null) return false;
        if (accounts != null ? !accounts.equals(client.accounts) : client.accounts != null) return false;
        return activeAccount != null ? activeAccount.equals(client.activeAccount) : client.activeAccount == null;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
        result = 31 * result + (activeAccount != null ? activeAccount.hashCode() : 0);
        result = 31 * result + (initialOverdraft != +0.0f ? Float.floatToIntBits(initialOverdraft) : 0);
        return result;
    }

	public String getName() {
		return firstName;
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
				.append(getClientSalutation()).append(getFirstName())
				.append(" ")
				.append(getSureName())
                .append("\n")
				.append("|----------------------------------------------------------\n")
				.append("|Information: \n")
				.append("|  Email: " + getEmail() + "\n")
				.append("|  Phone number: " + getPhoneNumber() + "\n")
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
