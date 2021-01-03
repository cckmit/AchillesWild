package com.achilles.wild.server.manager.account.atom;

public interface AccountLockAtomManager {

    boolean lock(String accountCode,String userId, int seconds);

    boolean unLock(String accountCode);

    boolean unlockAll();

}
