package com.achilles.wild.server.business.service.account;

import com.achilles.wild.server.entity.account.Account;
import com.achilles.wild.server.model.request.account.BalanceRequest;


public interface BalanceService2 {

    String consumeUserBalance2(String userId,BalanceRequest request);

    Long getBalance(String userId);

    Account getAccount(String userId);

    void setAccount(Account account);

}
