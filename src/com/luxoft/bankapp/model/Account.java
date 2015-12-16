package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughtFundsException;
import com.luxoft.bankapp.service.*;

public interface Account extends Report{
	public float getBalance();
    public void printReport();
	public void deposit(float x);
	public void withdraw(float x) throws NotEnoughtFundsException;
	public void decimalValue();
	
}
