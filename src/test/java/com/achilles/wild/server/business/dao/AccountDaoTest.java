package com.achilles.wild.server.business.dao;

import com.achilles.wild.server.StarterApplicationTests;
import com.achilles.wild.server.business.dao.account.AccountDao;
import com.achilles.wild.server.entity.account.Account;
import com.achilles.wild.server.enums.StatusEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class AccountDaoTest extends StarterApplicationTests {

    @Autowired
    private AccountDao accountDao;

    @Test
    public void selectBalanceByCodes(){
        //Account account = new Account();
        //account.setUserId("ACHILLES");
        //account.setBalance(1L);
        //account.setAccountType(AccountTypeEnum.PAY_ACCOUNT.toNumbericValue());
        List<Account> accounts  = accountDao.selectBalanceByCodes(Arrays.asList("ACCOUNT_20200618172720044_1_7","ACCOUNT_20200618172720044_1_8"),"wild");
        System.out.println();

    }

    @Test
    public void reduceUserBalance(){
        //Account account = new Account();
        //account.setUserId("ACHILLES");
        //account.setBalance(1L);
        //account.setAccountType(AccountTypeEnum.PAY_ACCOUNT.toNumbericValue());
        for (int i = 0; i <1000 ; i++) {
            int update  = accountDao.reduceUserBalance(33L,1l, StatusEnum.NORMAL.toNumbericValue(), System.currentTimeMillis());
            System.out.println("============================================"+update);
        }
    }
}
