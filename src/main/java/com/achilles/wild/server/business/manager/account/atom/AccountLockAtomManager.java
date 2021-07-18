package com.achilles.wild.server.business.manager.account.atom;

import com.achilles.wild.server.entity.account.Account;

public interface AccountLockAtomManager {

    boolean lock(String accountCode,String userId, int seconds);

    boolean lock(Account account);

    boolean unLock(Account account);

    boolean unLock(String accountCode);

    boolean unlockAll();

}
