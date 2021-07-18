package com.achilles.wild.server.business.service.account;

import com.achilles.wild.server.entity.account.Account;
import com.achilles.wild.server.model.request.account.BalanceRequest;

/**
 * �ո���
 */
public interface BalanceService2 {

    String consumeUserBalance(Account account, BalanceRequest request);

    Long getBalance(String userId);


}
