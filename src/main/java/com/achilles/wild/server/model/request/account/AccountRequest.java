package com.achilles.wild.server.model.request.account;

import java.io.Serializable;

public class AccountRequest implements Serializable {

    private static final long serialVersionUID = 5814282994311417228L;

    private String userId;

    private Integer count;

    private Integer accountType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
}
