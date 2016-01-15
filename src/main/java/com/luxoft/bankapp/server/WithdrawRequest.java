package com.luxoft.bankapp.server;

/**
 * Created by LChlebda on 2016-01-07.
 */
public class WithdrawRequest implements Request{

    private RequestType requestType = RequestType.WITHDRAW_REQUEST;

    private float amountToWithdraw;

    @Override
    public void printRequestInfo() {
        System.out.println();
    }

    public void setAmountToWithdraw(float amount) {
        this.amountToWithdraw = amount;
    }

    public float getAmountToWithdraw() {
        return amountToWithdraw;
    }

    @Override
    public RequestType getRequestType() {
        return requestType;
    }
}
