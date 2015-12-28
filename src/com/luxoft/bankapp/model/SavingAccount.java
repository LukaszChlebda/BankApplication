package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;

import java.io.Serializable;


public class SavingAccount extends AbstractAccount implements Serializable{

    public SavingAccount(){};
	
	public SavingAccount(float balance){
		super(balance);
	}

    public String getAccountInfo() {
        return toString();
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
    public void withdraw(float x) throws NotEnoughtFundsException{
        if(getBalance() > x) {
            setBalance(getBalance()-x);
        }else {
        	throw new NotEnoughtFundsException(this, x);
        }
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
