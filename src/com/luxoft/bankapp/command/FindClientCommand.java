package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.util.InputMismatchException;
import java.util.Scanner;


public class FindClientCommand implements Command{
    Bank currentBank = null;

    public FindClientCommand(Bank currentBank) {
        this.currentBank = currentBank;
    }


	@Override
	public void printCommandInfo() {
		System.out.println("Find Client: ");
	}
	
	@Override
	public void execute() {
        BankServiceImpl bankServiceImpl = new BankServiceImpl();
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        String clientToFind = null;
        System.out.println("Type name of client to find ");
        //while(flag) {
            //try {
                clientToFind = sc.nextLine();
              //  flag = false;
            //}catch (InputMismatchException e) {
            //    System.out.println(e.getMessage());
           // }
        //}
        try {
            bankServiceImpl.getClient(currentBank, clientToFind);
        }catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
	

}
