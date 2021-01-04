package com.achilles.wild.server.service;

import javax.annotation.Resource;

import com.achilles.wild.server.enums.account.AccountTypeEnum;
import com.achilles.wild.server.model.request.account.AccountRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.service.account.AccountService;
import com.achilles.wild.server.tool.BaseSpringJUnitTest;
import org.junit.Test;

public class AccountServiceTest  extends BaseSpringJUnitTest {

    @Resource
    private AccountService accountService;

    @Test
    public void addMasterAccount(){
        AccountRequest request = new AccountRequest();
        request.setUserId("ACHILLES");
        DataResult result = accountService.addMasterAccount(request);
        System.out.println(result);
    }

    @Test
    public void addMasterAndSlaveAccount(){
        AccountRequest request = new AccountRequest();
        request.setUserId("ACHILLES");
        DataResult result = accountService.addMasterAndSlaveAccount(request);
        System.out.println(result);
    }

    @Test
    public void addAllAccounts(){
        AccountRequest request = new AccountRequest();
        request.setUserId("honor");
        DataResult result = accountService.addAllAccounts(request);
        System.out.println(result);
    }

    @Test
    public void addAccountsByType(){
        AccountRequest request = new AccountRequest();
        request.setUserId("wild");
        request.setAccountType(AccountTypeEnum.PAY_ACCOUNT.toNumbericValue());
        DataResult result = accountService.addAccountsByType(request,10);
        System.out.println(result);
    }
}
