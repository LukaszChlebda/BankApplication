package com.luxoft.bankapp.server;

/**
 * Created by LChlebda on 2016-01-07.
 */
public enum RequestType {
    LOGIN_REQUEST("loginRequest"), WITHDRAW_REQUEST("withdrawRequest"), LOGOUT_REQUEST("logoutRequest"),
    CHANGE_ACTIVE_ACCOUNT("changeActiveAccountRequest"), GET_ACCOUNTS_INFO("getAccountsInfoRequest"),
    CLIENT_END_REQUEST("clientEndRequest");

    String requestType;

    RequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }
}
