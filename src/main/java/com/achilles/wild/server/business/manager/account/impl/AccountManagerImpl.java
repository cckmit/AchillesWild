package com.achilles.wild.server.business.manager.account.impl;

import com.achilles.wild.server.common.cache.AccountLock;
import com.achilles.wild.server.common.constans.AccountConstant;
import com.achilles.wild.server.business.dao.account.AccountDao;
import com.achilles.wild.server.business.dao.account.AccountRuleCollectDao;
import com.achilles.wild.server.entity.account.Account;
import com.achilles.wild.server.entity.account.AccountRuleCollect;
import com.achilles.wild.server.entity.account.AccountRulePay;
import com.achilles.wild.server.enums.StatusEnum;
import com.achilles.wild.server.enums.account.AccountTypeEnum;
import com.achilles.wild.server.business.manager.account.AccountManager;
import com.achilles.wild.server.business.manager.account.atom.AccountAtomManager;
import com.achilles.wild.server.model.query.account.AccountQuery;
import com.achilles.wild.server.tool.date.DateUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AccountManagerImpl implements AccountManager {

    private final static Logger LOG = LoggerFactory.getLogger(AccountManagerImpl.class);

    @Resource
    private AccountDao accountDao;

    @Resource
    private AccountRuleCollectDao accountRuleCollectDao;

    @Resource
    private AccountAtomManager accountAtomManager;

    private static Cache<String,List<AccountRuleCollect>> collectRuleCache = CacheBuilder.newBuilder().concurrencyLevel(1000).maximumSize(20000).expireAfterWrite(5, TimeUnit.SECONDS).build();


    private static Cache<String,List<AccountRulePay>> payRuleCache = CacheBuilder.newBuilder().concurrencyLevel(1000).maximumSize(20000).expireAfterWrite(5, TimeUnit.SECONDS).build();

    @Override
    public boolean addAccount(Account account) {
        if(account==null){
            return false;
        }

        int insert = accountDao.insertSelective(account);
        if(insert==0){
            return false;
        }

        return true;
    }

    @Override
    public boolean ifExist(AccountQuery query) {

        if(query==null){
            query = new AccountQuery();
        }

        query.setStatus(StatusEnum.NORMAL.toNumbericValue());

        long count = accountDao.selectCount(query);
        if(count!=0){
            return true;
        }

        return false;
    }

    @Override
    public boolean addAccounts(List<Account> accounts) {

        if(CollectionUtils.isEmpty(accounts)){
            return false;
        }

        for (int i = 0; i < accounts.size(); i++) {
            String code = AccountConstant.ACCOUNT_PREFIX+DateUtil.getCurrentStr(DateUtil.YYYY_MM_DD_HH_MM_SS_SSS)+"_"+Thread.currentThread().getId()+"_"+i;
            accounts.get(i).setAccountCode(code);
        }


        accountDao.batchInsert(accounts);

        return true;
    }

    @Override
    public String getAccountCode(Integer accountType) {

        if(accountType==null){
            return null;
        }

        if(!AccountTypeEnum.contains(accountType)){
            return null;
        }

        String code = AccountConstant.ACCOUNT_PREFIX+DateUtil.getCurrentStr(DateUtil.YYYY_MM_DD_HH_MM_SS_SSS)+"_"+Thread.currentThread().getId()+"_";

        return code;
    }

    @Override
    public Account getBalanceByCode(String accountCode,String userId) {

        if(StringUtils.isEmpty(accountCode)){
            return null;
        }

        Account account = accountDao.selectAccountByCode(accountCode,userId);

        return account;
    }


    @Override
    public boolean updateBalanceById(Long id, Long amount) {

        if(id==null||id==0||amount==null||amount<=0){
            return false;
        }

        int update =accountDao.updateBalanceById(id,amount);
        if(update==0){
            return false;
        }

        return true;
    }

    @Override
    public boolean updateUserBalanceById(Long id, Long balance) {

        if(id==null||id==0||balance==null||balance<0){
            return false;
        }

        int update =accountDao.updateUserBalanceById(id,balance);
        if(update==0){
            return false;
        }

        return true;
    }

    @Override
    public Long getUserBalance(String userId) {

        Assert.state(StringUtils.isNotEmpty(userId),"userId can not be null");

        Long balance = accountDao.selectUserBalance(userId,StatusEnum.NORMAL.toNumbericValue());

        return balance==null ? 0L : balance;
    }

    @Override
    public Long getUserBalanceById(Long id) {

        Long balance = accountDao.selectBalanceById(id,StatusEnum.NORMAL.toNumbericValue());

        return balance==null ? 0L : balance;
    }

    @Override
    public Account getUserAccount(String userId) {

        Assert.state(StringUtils.isNotEmpty(userId),"userId can not be null");

        Account account = accountDao.selectAccountByUserId(userId,StatusEnum.NORMAL.toNumbericValue());

        return account;
    }

    @Override
    public Account getCollectAccount(String userId) {

        if(StringUtils.isEmpty(userId)){
            return null;
        }

        List<AccountRuleCollect> accountRuleCollects= collectRuleCache.getIfPresent(AccountConstant.ACCOUNT_COLLECT_RULE+userId);
        if(CollectionUtils.isEmpty(accountRuleCollects)){
            accountRuleCollects = accountRuleCollectDao.selectRuleByUser(userId);
        }

        if(CollectionUtils.isEmpty(accountRuleCollects)){
            return null;
        }

        accountRuleCollects = accountRuleCollects.stream().sorted(Comparator.comparing(AccountRuleCollect::getWeight).reversed()).collect(Collectors.toList());

        AccountRuleCollect accountRuleColl = null;
        for(AccountRuleCollect accountRuleCollect:accountRuleCollects){
            if(AccountLock.lock(accountRuleCollect.getAccountCode())){
                accountRuleColl = accountRuleCollect;
                break;
            }
        }

        if(accountRuleColl==null){
            Collections.shuffle(accountRuleCollects);
            accountRuleColl = accountRuleCollects.get(0);
        }

        Account account = accountDao.selectAccountByCode(accountRuleColl.getAccountCode(),userId);

        return account;
    }

    @Override
    public Account getAccountById(Long id) {

        if(id==null||id==0){
            return null;
        }

        return accountDao.selectById(id);
    }

    @Override
    public boolean reduceUserBalance(Account account, Long amount) {

        Assert.state(account != null,"account can not be null !");
        Assert.state(amount != null && amount > 0,"amount is illegal !");

        boolean updateBalance = accountAtomManager.reduceBalance(account.getId(),amount);

        return updateBalance;
    }
}
