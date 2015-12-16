package com.luxoft.bankapp.exceptions;

public class BankException extends Exception {
	private String message;
	
	public BankException() {
		message = "Bank exception ";
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}
