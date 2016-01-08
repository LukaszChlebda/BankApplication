package com.luxoft.bankapp.service;

/**
 * Created by Łukasz on 08.01.2016.
 */
public enum AccountType {
    SAVING_ACCOUNT(0),CHECKING_ACCOUNT(1);

    private int activeAccount;

    AccountType(int activeAccount) {
        this.activeAccount = activeAccount;
    }

    public int getActiveAccount() {
        return activeAccount;
    }

}
