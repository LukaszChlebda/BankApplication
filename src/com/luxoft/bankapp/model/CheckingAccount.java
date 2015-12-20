package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;

import com.luxoft.bankapp.exceptions.OverDraftLimitExceededException;

public class CheckingAccount extends AbstractAccount {

	public CheckingAccount(float balance) {
		super(balance);
	}

	public CheckingAccount(float balance, float overdraft) {
		super(balance);
		if(overdraft < 0) {
			throw new IllegalArgumentException();
		}
		this.overdraft = overdraft;
		System.out.println("Overdraft test " + this.overdraft);
	}

    private float overdraft;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CheckingAccount that = (CheckingAccount) o;

		return Float.compare(that.overdraft, overdraft) == 0;

	}

	@Override
	public int hashCode() {
		return (overdraft != +0.0f ? Float.floatToIntBits(overdraft) : 0);
	}

	public void setOverdraft(float amount) throws IllegalArgumentException {
		if(amount < 0) {
			throw new IllegalArgumentException();
		}else {
			System.out.println("New Overdraft has been set ");
			this.overdraft = amount;
		}
	}
	
	public float getOverdraft() {
		return overdraft;
	}

    public String getAccountInfo() {
        return toString();
    }

    @Override
	public void decimalValue() {
		System.out.println(Math.round(getBalance()));
		
	}
    
    @Override
    public void deposit(float amount) {
        setBalance(getBalance()+amount);
    }

    @Override
    public void withdraw(float amount) throws OverDraftLimitExceededException{
    	if(amount > getBalance()) {
    		throw new OverDraftLimitExceededException(this,amount);
    	}else {
    		if(getBalance() >= amount-overdraft) {
                setBalance(getBalance()-amount);
            }
    	}
        
    }

    public void printReport() {
        System.out.println(toString());
    }
    
    public String toString() {
    	return "Checking account  balance: " + this.getBalance()
			    + ", overdraft: " + this.getOverdraft();
    }
}
