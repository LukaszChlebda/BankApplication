package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;

public abstract class AbstractAccount implements Account {
	private float balance;
	
	public AbstractAccount(float balance) throws IllegalArgumentException{
		setBalance(balance);
	}

	@Override
	public float getBalance() {
		return balance;
	}
	
	public void setBalance(float balance) throws IllegalArgumentException {
		if(balance < 0) {
			new IllegalArgumentException();
		}
		else {
			this.balance = balance;
		}
	}

    public abstract String getAccountInfo();

	@Override
    public abstract void deposit(float x);

	@Override
    public abstract void withdraw(float x) throws NotEnoughtFundsException;
}
