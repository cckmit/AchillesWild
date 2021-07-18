package com.achilles.wild.server.business.manager.account.atom;

import com.achilles.wild.server.entity.account.AccountTransactionFlow;

import java.util.List;

public interface AccountTransactionFlowAtomManager {

    List<AccountTransactionFlow> getUserTransactionFlows(String userId);


    List<AccountTransactionFlow> getInitTransactionFlows(String userId);
}
