package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;

import com.luxoft.bankapp.exceptions.OverDraftLimitExceededException;

public class CheckingAccount extends AbstractAccount {

    private String accountInfo = "Account type: Checking account  balance: " + getBalance() 
    	+ ", overdraft: " + getOverdraft();

    private float overdraft;
	
	public CheckingAccount(float balance) {
		super(balance);
	}
	
	public CheckingAccount(float balance, float overdraft) {
		super(balance);
		//this.overdraft = overdraft;
		setOverdraft(overdraft);
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((accountInfo == null) ? 0 : accountInfo.hashCode());
		result = prime * result + Float.floatToIntBits(overdraft);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckingAccount other = (CheckingAccount) obj;
		if (accountInfo == null) {
			if (other.accountInfo != null)
				return false;
		} else if (!accountInfo.equals(other.accountInfo))
			return false;
		if (Float.floatToIntBits(overdraft) != Float.floatToIntBits(other.overdraft))
			return false;
		return true;
	}

	public void setOverdraft(float amount) throws IllegalArgumentException {
		if(amount <0) {
			throw new IllegalArgumentException();
		}
			this.overdraft = amount;
	}
	
	public float getOverdraft() {
		return overdraft;
	}

    public String getAccountInfo() {
        return accountInfo;
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
        System.out.println(getAccountInfo());
    }
    
    public String toString() {
    	return getAccountInfo();
    }

	
}
