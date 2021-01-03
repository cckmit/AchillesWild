package com.achilles.wild.server.manager.account;

import com.achilles.wild.server.entity.account.AccountTransactionFlowInterAdd;

/**
 * flow
 */
public interface AccountTransactionFlowInterAddManager {

    boolean addFlow(AccountTransactionFlowInterAdd accountTransactionFlow);


    String getFlowNo();
}
