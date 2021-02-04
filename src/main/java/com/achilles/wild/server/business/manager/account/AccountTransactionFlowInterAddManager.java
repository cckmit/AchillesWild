package com.achilles.wild.server.business.manager.account;

import com.achilles.wild.server.business.entity.account.AccountTransactionFlowInterAdd;

/**
 * flow
 */
public interface AccountTransactionFlowInterAddManager {

    boolean addFlow(AccountTransactionFlowInterAdd accountTransactionFlow);


    String getFlowNo();
}
