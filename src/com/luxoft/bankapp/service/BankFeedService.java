package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dvorak on 28.12.15.
 */
public class BankFeedService {
    private static Bank activeBank = null;

    public BankFeedService(Bank bank) {
        activeBank = bank;
    }

    public static void setActiveBank(Bank activeBank) {
        BankFeedService.activeBank = activeBank;
    }

    public static void loadFeed(String folder) throws ClientExistsException {

        File sourceFolder = new File(folder);
        String lineToRead = null;
        try(BufferedReader reader = new BufferedReader(new FileReader(sourceFolder))) {
            while ((lineToRead = reader.readLine()) != null) {
                Map<String,String> feedMap = new HashMap<>();
                String[] separator = lineToRead.split(";");
                for(String record: separator) {
                    String[] dataInput = record.split("=");
                    feedMap.put(dataInput[0], dataInput[1]);
                }
                activeBank.parseFeed(feedMap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
