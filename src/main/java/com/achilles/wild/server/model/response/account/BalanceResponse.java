package com.achilles.wild.server.model.response.account;

public class BalanceResponse {

    private String flowNo;

    private Long balance;

    public BalanceResponse() {
    }

    public BalanceResponse(String flowNo, Long balance) {
        this.flowNo = flowNo;
        this.balance = balance;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
