package com.achilles.wild.server.business.service;

import com.achilles.wild.server.StarterApplicationTests;
import com.achilles.wild.server.enums.account.AccountTypeEnum;
import com.achilles.wild.server.model.request.account.AccountRequest;
import com.achilles.wild.server.model.response.PageResult;
import com.achilles.wild.server.business.service.account.AccountService;
import org.junit.Test;

import javax.annotation.Resource;

public class AccountServiceTest  extends StarterApplicationTests {

    @Resource
    private AccountService accountService;

    @Test
    public void addMasterAccount(){
        AccountRequest request = new AccountRequest();
        request.setUserId("ACHILLES");
        PageResult result = accountService.addMasterAccount(request);
        System.out.println(result);
    }

    @Test
    public void addMasterAndSlaveAccount(){
        AccountRequest request = new AccountRequest();
        request.setUserId("ACHILLES");
        PageResult result = accountService.addMasterAndSlaveAccount(request);
        System.out.println(result);
    }

    @Test
    public void addAllAccounts(){
        AccountRequest request = new AccountRequest();
        request.setUserId("honor");
        PageResult result = accountService.addAllAccounts(request);
        System.out.println(result);
    }

    @Test
    public void addAccountsByType(){
        AccountRequest request = new AccountRequest();
        request.setUserId("wild");
        request.setAccountType(AccountTypeEnum.PAY_ACCOUNT.toNumbericValue());
        PageResult result = accountService.addAccountsByType(request,10);
        System.out.println(result);
    }
}
