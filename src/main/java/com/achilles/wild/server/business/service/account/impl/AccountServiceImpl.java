package com.achilles.wild.server.business.service.account.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.achilles.wild.server.business.entity.account.Account;
import com.achilles.wild.server.enums.account.AccountTypeEnum;
import com.achilles.wild.server.business.manager.account.AccountManager;
import com.achilles.wild.server.model.query.account.AccountQuery;
import com.achilles.wild.server.model.request.account.AccountRequest;
import com.achilles.wild.server.model.response.PageResult;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.achilles.wild.server.business.service.account.AccountService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountManager accountManager;

    @Override
    @Transactional(rollbackForClassName ="Exception",isolation= Isolation.DEFAULT,propagation=Propagation.REQUIRED)
    public PageResult addMasterAccount(AccountRequest request) {

        if(request==null || StringUtils.isEmpty(request.getUserId())){
            return PageResult.baseFail(BaseResultCode.MISSING_PARAMETER);
        }

        AccountQuery query = new AccountQuery();
        query.setUserId(request.getUserId());
        query.setAccountType(AccountTypeEnum.MASTER_ACCOUNT.toNumbericValue());
        boolean exist = accountManager.ifExist(query);
        if(exist){
            return PageResult.baseFail(BaseResultCode.DATA_HAS_EXISTS);
        }

        Account account = getAccount(request.getUserId(),AccountTypeEnum.MASTER_ACCOUNT.toNumbericValue());
        boolean result = accountManager.addAccount(account);
        if(!result){
            return PageResult.baseFail();
        }

        String accountCode = account.getAccountCode();

        return PageResult.success(accountCode);
    }

    @Override
    @Transactional(rollbackForClassName ="Exception",isolation= Isolation.DEFAULT,propagation=Propagation.REQUIRED)
    public PageResult addMasterAndSlaveAccount(AccountRequest request) {

        if(request==null || StringUtils.isEmpty(request.getUserId())){
            return PageResult.baseFail(BaseResultCode.MISSING_PARAMETER);
        }

        AccountQuery query = new AccountQuery();
        query.setUserId(request.getUserId());
        query.setAccountType(AccountTypeEnum.MASTER_ACCOUNT.toNumbericValue());
        boolean exist = accountManager.ifExist(query);
        if(exist){
            return PageResult.baseFail(BaseResultCode.DATA_HAS_EXISTS);
        }

        List<Account> accounts = getAccounts(request.getUserId(), new AccountTypeEnum[]{AccountTypeEnum.MASTER_ACCOUNT,AccountTypeEnum.SLAVE_ACCOUNT});

        boolean result = accountManager.addAccounts(accounts);
        if(!result){
            return PageResult.baseFail();
        }

        return PageResult.success(accounts);
    }

    @Override
    @Transactional(rollbackForClassName ="Exception",isolation= Isolation.DEFAULT,propagation=Propagation.REQUIRED)
    public PageResult addAllAccounts(AccountRequest request) {

        if(request==null || StringUtils.isEmpty(request.getUserId())){
            return PageResult.baseFail(BaseResultCode.MISSING_PARAMETER);
        }

        AccountQuery query = new AccountQuery();
        query.setUserId(request.getUserId());
        query.setAccountType(AccountTypeEnum.MASTER_ACCOUNT.toNumbericValue());
        boolean exist = accountManager.ifExist(query);
        if(exist){
            return PageResult.baseFail(BaseResultCode.DATA_HAS_EXISTS);
        }

        List<Account> accounts = getAccounts(request.getUserId(), null);

        boolean result = accountManager.addAccounts(accounts);
        if(!result){
            return PageResult.baseFail();
        }

        return PageResult.success(accounts);
    }

    private List<Account> getAccounts(String userId,AccountTypeEnum[] accountTypeEnums){

        if(accountTypeEnums == null || CollectionUtils.sizeIsEmpty(accountTypeEnums)){
            accountTypeEnums = AccountTypeEnum.values();
        }

        List<Account> accounts = new ArrayList<>();

        for (AccountTypeEnum accountTypeEnum:accountTypeEnums) {
            Account account = new Account();
            account.setUserId(userId);
            account.setAccountType(accountTypeEnum.toNumbericValue());
            account.setAccountCode(accountManager.getAccountCode(accountTypeEnum.toNumbericValue()));
            accounts.add(account);
        }

        return accounts;
    }


    private Account getAccount(String userId,Integer accountType){
        Account account = new Account();
        account.setUserId(userId);
        account.setAccountType(accountType);
        return account;
    }

    @Override
    @Transactional(rollbackForClassName ="Exception",isolation= Isolation.DEFAULT,propagation=Propagation.REQUIRED)
    public PageResult addAccountsByType(AccountRequest request, int count) {

        if(request==null || StringUtils.isEmpty(request.getUserId()) || !AccountTypeEnum.contains(request.getAccountType()) || count==0){
            return PageResult.baseFail(BaseResultCode.MISSING_PARAMETER);
        }

        AccountQuery query = new AccountQuery();
        query.setUserId(request.getUserId());
        query.setAccountType(request.getAccountType());
        query.setStatus(1);
        if(accountManager.ifExist(query)){
            return PageResult.baseFail(BaseResultCode.DATA_HAS_EXISTS);
        }

        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < count; i++) {

            Account account = new Account();
            account.setUserId(request.getUserId());
            account.setAccountType(request.getAccountType());

            accounts.add(account);
        }

        boolean result = accountManager.addAccounts(accounts);
        if(!result){
            return PageResult.baseFail(BaseResultCode.FAIL);
        }

        return PageResult.success(accounts);
    }
}
