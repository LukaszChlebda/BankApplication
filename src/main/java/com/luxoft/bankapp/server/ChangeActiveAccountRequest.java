package com.luxoft.bankapp.server;

import com.luxoft.bankapp.service.AccountType;

/**
 * Created by LChlebda on 2016-01-07.
 */
public class ChangeActiveAccountRequest implements Request{

    private RequestType requestType = RequestType.CHANGE_ACTIVE_ACCOUNT;
    private int activeAccount;

    public ChangeActiveAccountRequest(AccountType accountType) {
        if(accountType.equals(AccountType.SAVING_ACCOUNT)) {
            activeAccount = 0;
        }else if(accountType.equals(AccountType.CHECKING_ACCOUNT)){
            activeAccount = 1;
        }
    }

    public int getActiveAccount() {
        return activeAccount;
    }

    @Override
    public void printRequestInfo() {

    }

    @Override
    public RequestType getRequestType() {
        return requestType;
    }
}
