package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;

import java.io.Serializable;
import java.util.Map;

public abstract class AbstractAccount implements Account {
	private float balance;
	private int id;

	public AbstractAccount(){};

	public AbstractAccount(float balance) throws IllegalArgumentException{
		setBalance(balance);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AbstractAccount that = (AbstractAccount) o;

		return Float.compare(that.balance, balance) == 0;

	}

	@Override
	public int hashCode() {
		return (balance != +0.0f ? Float.floatToIntBits(balance) : 0);
	}

	@Override
	public synchronized float getBalance() {
		return balance;
	}
	
	public  void setBalance(float balance) throws IllegalArgumentException {
			this.balance = balance;
	}

    public abstract String getAccountInfo();

	@Override
    public abstract void deposit(float x);

	@Override
	public  float getOverdraft() {
		return 0;
	};
	@Override
    public abstract void withdraw(float x) throws NotEnoughtFundsException;

	@Override
	public void parseFeed(Map<String, String> feed) {
		setBalance(Float.parseFloat(feed.get("balance")));
	}
}
