package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.FeedException;
import com.luxoft.bankapp.service.*;
import com.luxoft.bankapp.service.Gender;

import java.io.Serializable;
import java.util.*;

public class Client implements Report, Serializable {

	private String name;
	private String email;
	private String phoneNumber;
	private String city;
	private float initialOverdraft;
    private Gender gender;
	private List<Account> accounts;
	private Account activeAccount;
	private int id;



	private int bankId;
	private int accountId;

	public Client() {
		accounts = new ArrayList<>();
	}

	public Client(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Client(String name) {
		accounts = new ArrayList<>();
		this.name = name;
	}
    public Client(String name, Gender gender) {
        accounts = new ArrayList<>();
		this.name = name;
        this.gender = gender;
    }

	public Client(String name, Gender gender, String email) {
		accounts = new ArrayList<>();
		this.name = name;
		this.gender = gender;
		this.email = email;
	}


	public Client(String name, Gender gender, float initialOverdraft) {
        accounts = new ArrayList<>();
		this.name = name;
        this.initialOverdraft = initialOverdraft;
        this.gender = gender;
    }

	public Client(String name,String city, String email, String phoneNumber, Gender gender) {
		accounts = new ArrayList<>();
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.initialOverdraft = 0;
		this.city = city;
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

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getGender() {
		return gender.toString();
	}

	private Account getAccount(String accountType) {
		for (Account acc: accounts) {
			if (acc.getAccountType().equals(accountType)) {
				return acc;
			}
		}
		return createAccount(accountType);
	}

	private Account createAccount(String accountType) {
		Account acc;
		if ("s".equals(accountType)) {
			acc = new SavingAccount(0);
		} else if ("c".equals(accountType)) {
			acc = new CheckingAccount(0);
		} else {
			throw new FeedException("Account type not found "+accountType);
		}
		accounts.add(acc);
		return acc;
	}

	public void parseFeed(Map<String, String> feed) {
		String accountType = feed.get("accounttype");
		float initialOverdraft = 0;
		float balance = Float.parseFloat(feed.get("balance"));
		Account account;// = getAccount(accountType);
		if(accountType.equals("c")) {
			initialOverdraft = Float.parseFloat(feed.get("overdraft"));
			account = new CheckingAccount(balance, initialOverdraft);
		}
		else if(accountType.equals("s")) {
			account = new SavingAccount(balance);
		}else {
			return;
		}
		accounts.add(account);
		account.parseFeed(feed);
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

	public void addAccounts(List<Account> accounts) {
		this.accounts = accounts;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setGender(String gender) {
		switch (gender) {
			case "female":
				this.gender = Gender.FEMALE;
				break;
			case "male":
				this.gender = Gender.MALE;
				break;
			default:
				break;
		}

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

	public String getAccountsInfo() {
		String accountsInfo = "";
		for(Account account: getAccounts()) {
			accountsInfo += account.getAccountInfo() + " ";
		}
		return accountsInfo;
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
