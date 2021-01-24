package com.achilles.wild.server.model.request.account;

import java.util.Date;

public class BalanceRequest {


    private String userId;

    private String key;

    //private String accountCode;

    private Long amount;

    private Date tradeDate;

    private String tradeDateStr;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    //public String getAccountCode() {
    //    return accountCode;
    //}
    //
    //public void setAccountCode(String accountCode) {
    //    this.accountCode = accountCode;
    //}

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTradeDateStr() {
        return tradeDateStr;
    }

    public void setTradeDateStr(String tradeDateStr) {
        this.tradeDateStr = tradeDateStr;
    }
}
