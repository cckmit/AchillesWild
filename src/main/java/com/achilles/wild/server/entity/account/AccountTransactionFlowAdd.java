package com.achilles.wild.server.entity.account;

import java.util.Date;

public class AccountTransactionFlowAdd {

    private Long id;

    private String flowNo;

    private String idempotent;

    private String accountCode;

    private Long amount;

    private String userId;

    private Date tradeDate;

    private Integer tradeDay;

    private Integer status;

    private Date createDate;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo == null ? null : flowNo.trim();
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode == null ? null : accountCode.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Integer getTradeDay() {
        return tradeDay;
    }

    public void setTradeDay(Integer tradeDay) {
        this.tradeDay = tradeDay;
    }

    public String getIdempotent() {
        return idempotent;
    }

    public void setIdempotent(String idempotent) {
        this.idempotent = idempotent;
    }
}