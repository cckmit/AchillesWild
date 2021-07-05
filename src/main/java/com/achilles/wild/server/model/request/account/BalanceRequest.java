package com.achilles.wild.server.model.request.account;

import com.achilles.wild.server.model.request.BaseRequest;

public class BalanceRequest extends BaseRequest {

    private String userId;

    private String key;

    private Long amount;

    private Long tradeTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Long tradeTime) {
        this.tradeTime = tradeTime;
    }
}
