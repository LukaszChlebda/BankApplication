package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;

import java.io.Serializable;

public class SavingAccount extends AbstractAccount implements Serializable{
    private Object monitor = new Object();
    private int bankId;
    private int clientId;
    public SavingAccount(){};
	
	public SavingAccount(float balance){
		super(balance);
	}

    public String getAccountInfo() {
        return toString();
    }

    @Override
    public void setClientID(int id) {
        this.clientId = id;
    }

    @Override
    public int getClientId() {
        return getClientId();
    }

    @Override
    public int getBankId() {
        return getBankId();
    }

    @Override
    public void setBanId(int id) {
        this.bankId = id;
    }

    @Override
	public void decimalValue() {
		System.out.println(Math.round(getBalance()));
		
	}

    @Override
    public void deposit(float x) {
        setBalance(getBalance()+x);
    }

    @Override
    public void withdraw(float amount) throws NotEnoughtFundsException{
        synchronized (monitor) {
            if (getBalance() >= amount) {
                setBalance(getBalance() - amount);
            } else {
                throw new NotEnoughtFundsException(this, amount);
            }
        }
    }

    @Override
    public void setOverdraft(float overdraft) {

    }

    public void printReport() {
        System.out.println(toString());
    }
    
    public String toString() {
    	return "Saving account  balance: " + getBalance();
    }

    @Override
    public String getAccountType() {
        return "s";
    }

}
