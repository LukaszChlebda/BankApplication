package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.*;

/**
 * Created by Łukasz on 21.12.2015.
 */
public class BankReport {

	public void getNumberOfClients(Bank bank) {
		System.out.println("--------------------------------------------" +
				"\nBank has " + bank.getClients().size() + " clients.\n" +
				"--------------------------------------------");
	}

	public void getAccountsNumber(Bank bank) {
		int numberOfAccounts = 0;

		for(Client client:bank.getClients())
			numberOfAccounts+=client.getAccounts().size();
		System.out.println("--------------------------------------------" +
				"\nBank has " + numberOfAccounts + " accounts.\n" +
				"--------------------------------------------");
	}

	public void getClientsSorted(Bank bank) {
		System.out.println("--------------------------------------------\nClients of bank in sorted order \n");
		for(Client client: bank.getClients())
			System.out.println(client.getName());
		System.out.println("--------------------------------------------");
	}

	public void getBankCreditSum(Bank bank) {
		float credit = 0;

		for(Client client: bank.getClients()) {
			for (Account account: client.getAccounts()) {
				if(account.getBalance() < 0) {
					credit+=account.getBalance();
				}
			}
		}

		System.out.println("--------------------------------------------" +
				"\nBank credit for all clients is: " + credit + " \n" +
				"--------------------------------------------");
	}

	public void getClientsByCity(Bank bank) {
		Map<String, List<Client>> cityMap = new TreeMap<>();

		for(Client client:bank.getClients())
		{
			if(cityMap.get(client.getCity()) == null)
			{
				cityMap.put(client.getCity(), new LinkedList<>());
				cityMap.get(client.getCity()).add(client);
			}
			else
				cityMap.get(client.getCity()).add(client);
		}
		for(String s: cityMap.keySet())
			System.out.println(s + " " + cityMap.get(s));
	}
}
