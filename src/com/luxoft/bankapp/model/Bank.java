package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.*;
import com.luxoft.bankapp.exceptions.*;

import java.util.*;


public class Bank implements Report{

	private Set<Client> clients;
	private Map<String, Client> mapOfClients;
	private List<ClientRegistrationListener> registrationListener;
	
	public Bank() {
		clients = new HashSet<>();
		mapOfClients = new TreeMap<>();
		registrationListener = new ArrayList<>();
		registerEvent(new PrintClientListener());
		registerEvent(new EmailNotificationListener());
	}
	
	public Set<Client> getClients() {
		return Collections.unmodifiableSet(clients);

	}
	
	class PrintClientListener implements ClientRegistrationListener {

		@Override
		public void onClientAdded(Client client) {
			mapOfClients.put(client.getName(), client);
			System.out.println("Client " + client.getName() + " added ");
		}
		
	}
	
	class EmailNotificationListener implements ClientRegistrationListener {

		@Override
		public void onClientAdded(Client client) {
			System.out.println("Email for client " + client.getName() + " to be sent ");
			
		}
		
	}

	public Map<String, Client> getMapOfClients() {
		return mapOfClients;
	}
	
	public List<ClientRegistrationListener> getListeners() {
        return registrationListener;
    }
	
	void registerEvent(ClientRegistrationListener actionListener) {
        registrationListener.add(actionListener);
    }

//	public Client getClient(int index) throws ClientNotFoundException{
//		return clients.get(index);
//	}

	public boolean removeClient(Client client) {
		return clients.remove(client);
	}

    public Client getClient(String name) throws ClientNotFoundException {
	    boolean clientFoundFlag = false;

	    for(Iterator<Client> iterator = clients.iterator(); iterator.hasNext(); ) {
		    Client client = iterator.next();
		    if(client.getName().equals(name)) {
			    clientFoundFlag = true;
			    return client;
		    }
	    }
	    if(!clientFoundFlag) {
		    throw new ClientNotFoundException(name);
	    }
	    return null;

    }
	
	public void addClient(Bank bank, Client client) throws ClientExistsException{
//		try {
//            if (bank.getClients().indexOf(client) != -1) {
//                throw new ClientExistsException(client);
//            } else {
//	            bank.clients.add(client);
//	            for (int j = 0; j < getListeners().size(); j++) {
//		            bank.getListeners().get(j).onClientAdded(client);
//	            }
//            }
//        } catch (ClientExistsException e) {
//			System.out.println("Error client name: " + client.getFirstName() + " " + client.getSureName() + " already exists in database");
//        }

		if(bank.clients.add(client)) {
			for (int j = 0; j < getListeners().size(); j++) {
		            bank.getListeners().get(j).onClientAdded(client);
	            }
		}else {
			System.out.println("Client already exists in data base ");
		}
	}

	@Override
	public void printReport() {
		clients.forEach(client -> client.printReport());
		
	}

}
