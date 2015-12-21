package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.*;

/**
 * Created by Łukasz on 21.12.2015.
 */
public class BankReport {

//	private Bank bank = null;
//	public BankReport(Bank bank) {
//		this.bank = bank;
//	}
	public void getNumberOfClients(Bank bank) {
		System.out.println("Bank has " + bank.getClients().size() + " clients.");
	}

	public void getAccountsNumber(Bank bank) {
		int numberOfAccounts = 0;

		for(Client client:bank.getClients())
			numberOfAccounts+=client.getAccounts().size();
		System.out.println("Bank has " + numberOfAccounts + " accounts.");
	}

	public void getClientsSorted(Bank bank) {
//		Set<Client> sortedClient = new TreeSet<>();
//		sortedClient.addAll(bank.getClients());
//
//		Iterator<Client> iterator = sortedClient.iterator();
//		System.out.println("Client of the bank \n");
//		while (iterator.hasNext()) {
//			System.out.println(iterator.next());
//		}
		for(Client client: bank.getClients())
			System.out.println(client.getName());
	}

	//To implement
	public void getBankCreditSum(Bank bank) {

	}

	public void getClientsByCity(Bank bank){
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
