package com.achilles.wild.server.business.manager.account.atom;

import com.achilles.wild.server.entity.account.Account;

public interface AccountAtomManager {


    Account getPayAccount(String userId,Long amount);

    /**
     * merge balance to one
     *
     * @param userId
     * @return
     */
    Account mergeBalance(String userId,Integer accountType,Long amount);


    boolean reduceBalance(Long id,Long amount);
}
