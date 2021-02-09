package com.achilles.wild.server.model.response.code;

public enum AccountResultCode{

    BALANCE_NOT_ENOUGH("-600", "balance not enough ~");

    public final String code;
    public final String message;

    AccountResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
