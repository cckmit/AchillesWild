package com.achilles.wild.server.business.service.account;

import com.achilles.wild.server.entity.account.Account;


public interface BalanceService2 {

    Long getBalance(String userId);

    Account getAccount(String userId);

}
