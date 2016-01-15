package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;

public interface Command {
	
	public void execute() throws ClientNotFoundException, NotEnoughtFundsException, ClientExistsException;
	public void printCommandInfo();

}
