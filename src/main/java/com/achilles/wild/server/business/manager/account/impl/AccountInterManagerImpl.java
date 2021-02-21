package com.achilles.wild.server.business.manager.account.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.achilles.wild.server.common.constans.AccountConstant;
import com.achilles.wild.server.business.dao.account.AccountInterDao;
import com.achilles.wild.server.common.cache.AccountLock;
import com.achilles.wild.server.entity.account.AccountInter;
import com.achilles.wild.server.enums.account.AccountInterTypeEnum;
import com.achilles.wild.server.enums.account.AccountTypeEnum;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.achilles.wild.server.business.manager.account.AccountInterManager;
import com.achilles.wild.server.business.manager.account.atom.AccountInterAtomManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountInterManagerImpl implements AccountInterManager {

    @Resource
    private AccountInterDao accountInterDao;

    @Resource
    private AccountInterAtomManager accountInterAtomManager;

    private static Cache<String,List<AccountInter>> accountIntersCache = CacheBuilder.newBuilder().concurrencyLevel(1000).maximumSize(20000).expireAfterWrite(5, TimeUnit.SECONDS).build();


    @Override
    public boolean updateBalanceById(Long id, Long amount) {

        if(id==null||id==0||amount==null||amount<=0){
            return false;
        }

        int update =accountInterDao.updateBalanceById(id,amount);
        if(update==0){
            return false;
        }

        return true;
    }

    @Override
    public boolean updateInterBalanceById(Long id, Long balance) {

        if(id==null||id==0||balance==null||balance<0){
            return false;
        }

        int update =accountInterDao.updateInterBalanceById(id,balance);
        if(update==0){
            return false;
        }

        return true;
    }

    @Override
    public AccountInter getCollectAccountInter() {

        List<AccountInter> collAccountInters =accountIntersCache.getIfPresent(AccountConstant.ACCOUNT_INTER_COLLECT);
        if(CollectionUtils.isEmpty(collAccountInters)){
            collAccountInters = accountInterDao.selectAccountByType(AccountInterTypeEnum.COLLECTION_ACCOUNT.toNumbericValue());
            if(CollectionUtils.isNotEmpty(collAccountInters)){
                accountIntersCache.put(AccountConstant.ACCOUNT_INTER_COLLECT,collAccountInters);
            }
        }

        if(CollectionUtils.isEmpty(collAccountInters)) {
            return null;
        }

        AccountInter accountInte = null;
        for(AccountInter accountInter:collAccountInters){
            if(AccountLock.lock(accountInter.getAccountCode())){
                accountInte = accountInter;
                break;
            }
        }

        if(accountInte==null){
            Collections.shuffle(collAccountInters);
            accountInte = collAccountInters.get(0);
        }

        return accountInte;
    }



    @Override
    public Long getBalance(String userId) {

        if(StringUtils.isEmpty(userId)){
            return 0L;
        }

        Long balance = accountInterDao.selectBalance(userId);

        return balance==null?0L:balance;
    }

    @Override
    public boolean reduceBalance(Long id, Long amount) {

        if (id == null || id == 0 || amount == null || amount <= 0) {
            return false;
        }

        int update = 0;
        try {
            update = accountInterDao.reduceInterBalance(id, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (update == 0) {
            return false;
        }

        return true;
    }

    @Override
    public AccountInter getAccountById(Long id) {

        if(id==null||id==0){
            return null;
        }

        return accountInterDao.selectById(id);
    }

    @Override
    public long getVersionById(Long id) {

        if(id==null||id==0){
            throw new IllegalArgumentException("id is null");
        }

        return accountInterDao.selectVersionById(id);
    }

    @Override
    public AccountInter reduceInterBalance(Long amount) {

        if(amount==null||amount<=0){
            throw new IllegalArgumentException();
        }

        AccountInter account = accountInterAtomManager.getPayAccountInter(AccountConstant.ACCOUNT_INTER_USER_ID,amount);
        if(account!=null){
            boolean updateBalance = accountInterAtomManager.reduceInterBalance(account.getId(),amount);
            if(updateBalance){
                return account;
            }
            throw new RuntimeException("reduceInterBalance reduceBalance fail");
        }

        account = accountInterAtomManager.mergeInterBalance(AccountConstant.ACCOUNT_INTER_USER_ID,AccountTypeEnum.PAY_ACCOUNT.toNumbericValue(),amount);
        if(account==null){
            throw new RuntimeException("reduceInterBalance get user Account null  after mergeBalance");
        }
        boolean updateBalance = accountInterAtomManager.reduceInterBalance(account.getId(),amount);
        if(!updateBalance){
            throw new RuntimeException("reduceInterBalance fail  after mergeBalance");
        }

        account.setBalance(account.getBalance()-amount);

        return account;
    }
}
