package com.luxoft.bankapp.exceptions;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;

import com.luxoft.bankapp.model.*;

public class OverDraftLimitExceededException extends NotEnoughtFundsException{
	private String message;
	
	public OverDraftLimitExceededException(String message) {
		message = "Overdraft limit exceeded ";	
	}
	public OverDraftLimitExceededException(CheckingAccount account, float toWithdraw) {
		message = "For account " + account + "the founds are " + account.getBalance() + 
				" so you can't withcdraw " + account;

	}
	
	@Override
	public String toString() {
		return message;
	}
	
}
