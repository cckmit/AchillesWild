package com.achilles.wild.server.dao;

import javax.annotation.Resource;

import com.achilles.wild.server.dao.account.AccountDao;
import com.achilles.wild.server.tool.BaseSpringJUnitTest;
import org.junit.Test;

public class AccountDaoTest extends BaseSpringJUnitTest {

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
