package com.luxoft.bankapp.model;

import java.io.NotActiveException;
import java.nio.channels.OverlappingFileLockException;

import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;


public class SavingAccount extends AbstractAccount {

    private String  accountInfo = "Account type: Saving account  balance: " + getBalance();
	
	public SavingAccount(float balance){
		super(balance);
	}

    public String getAccountInfo() {
        return this.accountInfo;
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
        System.out.println();
    }
    
    public String toString() {
    	return getAccountInfo();
    }

	

}
