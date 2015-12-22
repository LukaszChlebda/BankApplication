package com.luxoft.bankapp.exceptions;

import com.luxoft.bankapp.model.Client;

public class ClientExistsException extends Throwable{
	private String message;
	
	public ClientExistsException(Client client) {
		message = client.getClientSalutation() + client.getName() + " already exists in database ";
	}
	
	@Override
	public String toString() {
		return message;
	}
}
