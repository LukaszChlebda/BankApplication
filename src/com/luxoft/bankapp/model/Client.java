package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.*;
import com.luxoft.bankapp.service.Gender;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client implements Report {

	private String name;
    private Gender gender;
	private List<Account> accounts; 
	private Account activeAccount;
	private float initialOverdraft;

    public Client(String name, Gender gender) {
        accounts = new ArrayList<>();
        this.name = name;
        this.gender = gender;
    }

    public Client(String name, Gender gender,  float initialOverdraft) {
        accounts = new ArrayList<>();
        this.name = name;
        this.initialOverdraft = initialOverdraft;
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (Float.compare(client.initialOverdraft, initialOverdraft) != 0) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (accounts != null ? !accounts.equals(client.accounts) : client.accounts != null) return false;
        return activeAccount != null ? activeAccount.equals(client.activeAccount) : client.activeAccount == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
        result = 31 * result + (activeAccount != null ? activeAccount.hashCode() : 0);
        result = 31 * result + (initialOverdraft != +0.0f ? Float.floatToIntBits(initialOverdraft) : 0);
        return result;
    }
	
	public void addAccount(Account accountToAdd) {
		accounts.add(accountToAdd);
	}

	public List<Account> getAccounts() {
		return Collections.unmodifiableList(accounts);
	}
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		sb.append(getClientSalutation()).append(getName())
                .append(" \n");
        System.out.println(sb.toString());
		for (Account account : accounts) {
            account.printReport();
		}
        System.out.println("\n");

	}

}
