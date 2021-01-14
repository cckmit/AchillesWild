package com.achilles.wild.server.manager;

import com.achilles.wild.server.SpringbootApplicationTests;
import com.achilles.wild.server.common.constans.AccountConstant;
import com.achilles.wild.server.manager.account.atom.AccountLockAtomManager;
import org.junit.Test;

import javax.annotation.Resource;

public class AccountLockManagerTest extends SpringbootApplicationTests {


    @Resource
    private AccountLockAtomManager accountLockManager;


    @Test
    public void unlockAllAccount(){
        boolean ret = accountLockManager.unlockAll();
        System.out.println(ret);
    }

    @Test
    public void unLock(){
        boolean ret = accountLockManager.unLock("AEF7DD2BF46244BF913783C9ECF9A2D3_20200530184618331_4");
        System.out.println(ret);
    }

    @Test
    public void lock(){
        boolean ret = accountLockManager.lock("PAY_ACCOUNT_2", AccountConstant.ACCOUNT_INTER_USER_ID,38);
        System.out.println(ret);
    }
}
