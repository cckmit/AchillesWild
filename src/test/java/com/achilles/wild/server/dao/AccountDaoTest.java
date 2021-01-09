package com.achilles.wild.server.dao;

import com.achilles.wild.server.SpringbootApplicationTests;
import com.achilles.wild.server.dao.account.AccountDao;
import org.junit.Test;

import javax.annotation.Resource;

public class AccountDaoTest extends SpringbootApplicationTests {

    @Resource
    private AccountDao accountDao;

    @Test
    public void reduceUserBalance(){
        //Account account = new Account();
        //account.setUserId("ACHILLES");
        //account.setBalance(1L);
        //account.setAccountType(AccountTypeEnum.PAY_ACCOUNT.toNumbericValue());
        for (int i = 0; i <1000 ; i++) {
            int update  = accountDao.reduceUserBalance(33L,1l);
            System.out.println("============================================"+update);
        }


    }
}
