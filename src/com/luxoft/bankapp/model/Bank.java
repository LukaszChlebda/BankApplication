package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.*;
import com.luxoft.bankapp.exceptions.*;

import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.omg.CORBA.IMP_LIMIT;

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
		//return clients;
	}
	
	class PrintClientListener implements ClientRegistrationListener {

		@Override
		public void onClientAdded(Client client) {
			System.out.println("Client " + client.getName() + " added ");
			
		}
		
	}
	
	class EmailNotificationListener implements ClientRegistrationListener {

		@Override
		public void onClientAdded(Client client) {
			System.out.println("Email for clent " + client.getName() + " to be sent ");
			
		}
		
	}
	
	public List<ClientRegistrationListener> getListeners() {
        return registrationListener;
    }
	
	void registerEvent(ClientRegistrationListener actionListener) {
        registrationListener.add(actionListener);
    }

	public Client getClient(int index) {
		return clients.get(index);
	}

	public boolean removeClient(Client client) {
		return clients.remove(client);
	}

    public Client getClient(String name) {
		Iterator<Client> iterator = clients.iterator();
		Client clientFound = null;

		while(iterator.hasNext()) {
			if(iterator.next().getName().equals(name)) {
				clientFound = (Client) iterator;
			}
		}
        return clientFound;
    }
	
	public void addClient(Bank bank, Client client) throws ClientExistsException{
		
		
		try {
            if (bank.getClients().indexOf(client) != -1) {
                throw new ClientExistsException(client);
            } else {
                bank.clients.add(client);
            }
        } catch (ClientExistsException e) {
            e.getMessage();
        } finally {
        	for(int j=0; j<getListeners().size(); j++) {
				bank.getListeners().get(j).onClientAdded(client);
			}
        }
	}
		
		

	@Override
	public void printReport() {
		clients.forEach(client -> client.printReport());
		
	}

}
