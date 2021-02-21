package com.achilles.wild.server.business.manager.account.atom.impl;

import com.achilles.wild.server.common.constans.AccountConstant;
import com.achilles.wild.server.business.dao.account.AccountLockDao;
import com.achilles.wild.server.entity.account.AccountLock;
import com.achilles.wild.server.business.manager.account.atom.AccountLockAtomManager;
import com.achilles.wild.server.tool.date.DateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AccountLockAtomManagerImpl implements AccountLockAtomManager {

    private final static Logger LOG = LoggerFactory.getLogger(AccountLockAtomManagerImpl.class);

    @Resource
    private AccountLockDao accountLockDao;

    @Override
    public boolean unlockAll() {

        int count = accountLockDao.unlockAll();

        LOG.info("unlockAllAccount update count:"+count);

        return true;
    }

    @Override
    public boolean unLock(String accountCode) {

        if(StringUtils.isEmpty(accountCode)){
            throw new IllegalArgumentException();
        }

        List<AccountLock> accountLocks = accountLockDao.selectLockAccount(accountCode);

        //not locked, lock
        if(CollectionUtils.isEmpty(accountLocks)){
            return true;
        }

        AccountLock accountLock = accountLocks.get(0);

        //locked before, unlocked now
        if(accountLock.getLocked() == AccountConstant.ACCOUNT_LOCK){
            return true;
        }

        accountLock.setLocked(AccountConstant.ACCOUNT_UNLOCK);
        accountLock.setUnlockTime(new Date());
        int update = accountLockDao.updateByPrimaryKeySelective(accountLock);
        if(update==0){
            return false;
        }

        return true;
    }

    @Override
    public boolean lock(String accountCode, String userId, int seconds) {

        if(StringUtils.isEmpty(accountCode)||StringUtils.isEmpty(userId)||seconds<=0){
            throw new IllegalArgumentException("accountCode:"+accountCode+",userId:"+userId+",seconds:"+seconds);
        }

        Date now = new Date();
        Date unlockTime =DateUtil.getDateByAddMilli(seconds*1000);

        List<AccountLock> accountLocks = accountLockDao.selectLockAccount(accountCode);

        //not locked, lock
        if(CollectionUtils.isEmpty(accountLocks)){
            AccountLock accountLock = new AccountLock();
            accountLock.setAccountCode(accountCode);
            accountLock.setUserId(userId);
            accountLock.setUnlockTime(unlockTime);
            int insert = accountLockDao.insertSelective(accountLock);
            if(insert==0){
                return false;
            }
            return true;
        }

        AccountLock accountLock = accountLocks.get(0);

        //locked before, unlocked now
        if(accountLock.getLocked()== AccountConstant.ACCOUNT_UNLOCK){
            accountLock.setLocked(AccountConstant.ACCOUNT_LOCK);
            accountLock.setUnlockTime(unlockTime);
            int update = accountLockDao.updateByPrimaryKeySelective(accountLock);
            if(update==0){
                return false;
            }
            return true;
        }

        //still locked
        if(accountLock.getUnlockTime().getTime() > now.getTime()){
            return false;
        }

        //still locked,but timeout, then extend locking time
        accountLock.setUnlockTime(unlockTime);
        int update = accountLockDao.updateByPrimaryKeySelective(accountLock);
        if(update==0){
            return false;
        }

        return true;
    }
}
