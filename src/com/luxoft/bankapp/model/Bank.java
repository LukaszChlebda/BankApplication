package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.*;
import com.luxoft.bankapp.exceptions.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class Bank implements Report{

	private List<Client> clients;
	private List<ClientRegistrationListener> registrationListener;
	
	public Bank() {
		clients = new ArrayList<>();
		registrationListener = new ArrayList<>();
		registerEvent(new PrintClientListener());
		registerEvent(new EmailNotificationListener());
	}
	
	public List<Client> getClients() {
		return Collections.unmodifiableList(clients);

	}
	
	class PrintClientListener implements ClientRegistrationListener {

		@Override
		public void onClientAdded(Client client) {
			System.out.println("Client " + client.getFirstName() + " added ");
			
		}
		
	}
	
	class EmailNotificationListener implements ClientRegistrationListener {

		@Override
		public void onClientAdded(Client client) {
			System.out.println("Email for client " + client.getFirstName() + " to be sent ");
			
		}
		
	}
	
	public List<ClientRegistrationListener> getListeners() {
        return registrationListener;
    }
	
	void registerEvent(ClientRegistrationListener actionListener) {
        registrationListener.add(actionListener);
    }

	public Client getClient(int index) throws ClientNotFoundException{
		return clients.get(index);
	}

	public boolean removeClient(Client client) {
		return clients.remove(client);
	}

    public Client getClient(String name, String surName) throws ClientNotFoundException {
	    boolean clientFoundFlag = false;

	    for(Iterator<Client> iterator = clients.iterator(); iterator.hasNext(); ) {
		    Client client = iterator.next();
		    if((client.getName().equals(name)) && (client.getSureName().equals(surName))) {
			    clientFoundFlag = true;
			    return client;
		    }
	    }
	    if(!clientFoundFlag) {
		    throw new ClientNotFoundException("Client not found ");
	    }
	    return null;

    }
	
	public void addClient(Bank bank, Client client) throws ClientExistsException{
		try {
            if (bank.getClients().indexOf(client) != -1) {
                throw new ClientExistsException(client);
            } else {
	            bank.clients.add(client);
	            for (int j = 0; j < getListeners().size(); j++) {
		            bank.getListeners().get(j).onClientAdded(client);
	            }
            }
        } catch (ClientExistsException e) {
			System.out.println("Error client name: " + client.getFirstName() + " " + client.getSureName() + " already exists in database");
        }
	}

	@Override
	public void printReport() {
		clients.forEach(client -> client.printReport());
		
	}

}
