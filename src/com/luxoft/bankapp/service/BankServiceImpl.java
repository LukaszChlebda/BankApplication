package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.*;

import java.io.*;

public class BankServiceImpl implements BankService {
    private static final String FILE_OBJECT_DATA = "files/object.data";

    @Override
    public void addClient(Bank bank, Client client) throws ClientExistsException {
        bank.addClient(bank, client);
    }

    @Override
    public void removeClient(Bank bank, Client client) {

        if (bank.removeClient(client)) {
            System.out.println("Client removed ");
        } else {
            System.out.println("No client to remove ");
        }
    }

    @Override
    public void addAccount(Client client, Account account) {
        client.addAccount(account);
    }

    @Override
    public void setActiveAccoutnt(Client client, Account account) {
        client.setActiveAccount(account);
    }

    @Override
    public Client getClient(Bank bank, String clientName) throws ClientNotFoundException {

	    return bank.getClient(clientName);
    }

    @Override
    public void saveClient(Client client) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(client.getName()+".object");
            ObjectOutputStream objectInputStream = new ObjectOutputStream(fileOutputStream))
        {
            objectInputStream.writeObject(client);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client loadClient(String objectPath) {
        Client client = null;

        try(FileInputStream fileInputStream = new FileInputStream(objectPath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            client = (Client)objectInputStream.readObject();
        }catch (FileNotFoundException e) {
            System.out.println("No object to load ");
            e.printStackTrace();
        }catch (IOException f) {
            f.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return client;
    }
}
