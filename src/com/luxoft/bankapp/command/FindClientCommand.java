package com.luxoft.bankapp.command;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.util.Scanner;


public class FindClientCommand implements Command{
    private Bank currentBank = null;

    public FindClientCommand(Bank currentBank) {
        this.currentBank = currentBank;
    }


	@Override
	public void printCommandInfo() {
		System.out.println("Find Client: ");
	}
	
	@Override
	public void execute() {
        BankService bankServiceImpl = new BankServiceImpl();
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        String clientToFind = null;
        Client client = null;
        System.out.println("Type name of client to find ");

                clientToFind = sc.nextLine();

        try {
            client = bankServiceImpl.getClient(currentBank, clientToFind);
        }catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        }

        if(currentBank.getClients().size()>0) {
            System.out.println(currentBank.getClients().size());
            client.printReport();
        }else {
            System.out.println("No clients ");
        }

    }
	

}
