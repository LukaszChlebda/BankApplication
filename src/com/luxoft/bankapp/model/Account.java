package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.service.*;

import java.util.Map;

public interface Account extends Report{
	public float getBalance();
	public float getOverdraft();
    public void printReport();
	public void deposit(float x);
	public void withdraw(float x) throws NotEnoughtFundsException;
	public void decimalValue();
	public void parseFeed(Map<String,String> feed);
	public String getAccountType();
	
}
