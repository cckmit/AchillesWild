package com.achilles.wild.server.business.manager.account;

import com.achilles.wild.server.entity.account.AccountTransactionFlow;

public interface AccountTransactionFlowManager {

    boolean addFlow(AccountTransactionFlow accountTransactionFlow);

    String getFlowNoByKey(String key,String userId);

    Long getUserTodayIncome(String userId);


    Long getInitTransactionFlowAmount(String userId);
}
