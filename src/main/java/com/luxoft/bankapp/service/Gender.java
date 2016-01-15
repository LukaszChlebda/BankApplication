package com.luxoft.bankapp.service;

public enum Gender {
    FEMALE("Ms. "), MALE("Mr. ");

    private String salutation;

    Gender(String salutation) {
        this.salutation = salutation;
    }

    public String getPrefix() {
        return salutation;
    }
}
