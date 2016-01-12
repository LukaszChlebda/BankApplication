package com.luxoft.bankapp.server;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import com.luxoft.bankapp.service.Gender;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LChlebda on 2016-01-12.
 */
public class BankServerThreaded {

    private ServerSocket serverSocket;
    private ExecutorService executorServicePool;
    private final int POOL_SIZE = 10;
    private Bank bank;

    public BankServerThreaded(Bank bank) {
        try {
            this.bank = bank;
            serverSocket = new ServerSocket(2004);
            executorServicePool = Executors.newFixedThreadPool(POOL_SIZE);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Code is working ");
        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();
                executorServicePool.execute(new ServerThread(clientSocket, bank));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BankService bService = new BankServiceImpl();
        Bank bank = BankServerThreaded.initialize(bService);
        BankServerThreaded sb = new BankServerThreaded(bank);
        sb.start();
    }

    //------------------------------------------------------------------
    public static Bank initialize(BankService bService) {

        Client client1 = new Client("Lukasz","Krakow","lukasz@gmail.com","123456789", Gender.MALE,1000);
        Client client2 = new Client("Jarek","Krakow","Jarek@gmail.com","123456789", Gender.MALE,500);
        Client client3 = new Client("Przemek","Warszawa","przemo@gmail.com","123456789", Gender.MALE,200);

        SavingAccount savingAccount = new SavingAccount(1000);
        CheckingAccount checkingAccount = new CheckingAccount(1000);

        Bank bank = new Bank();

        try {
            bService.addClient(bank, client1);
        } catch (ClientExistsException e) {
            System.out.println(e.getMessage());
        }
        try {
            bService.addClient(bank, client2);
        } catch (ClientExistsException e) {
            System.out.println(e.getMessage());
        }
        try {
            bService.addClient(bank, client3);
        } catch (ClientExistsException e) {
            System.out.println(e.getMessage());
        }

        Account savingAccount1 = new SavingAccount(1000);
        Account checkingAccount1 = new CheckingAccount(1000,1000);
        bService.addAccount(client1,savingAccount1);
        bService.addAccount(client1, checkingAccount1);
        bService.setActiveAccoutnt(client1, checkingAccount1);
        //-----------------------------------------------------
        Account savingAccount2 = new SavingAccount(1000);
        Account checkingAccount2 = new CheckingAccount(1000);
        bService.addAccount(client2,savingAccount2);
        bService.addAccount(client2, checkingAccount2);
        bService.setActiveAccoutnt(client2, savingAccount2);
        //-----------------------------------------------------
        Account savingAccount3 = new SavingAccount(1000);
        Account checkingAccount3 = new CheckingAccount(1000,1000);
        bService.addAccount(client3,savingAccount3);
        bService.addAccount(client3, checkingAccount3);
        bService.setActiveAccoutnt(client3, checkingAccount3);

        return bank;
    }
}

