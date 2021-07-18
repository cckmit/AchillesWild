package com.achilles.wild.server.business.service.account.impl;

import com.achilles.wild.server.business.manager.account.AccountManager;
import com.achilles.wild.server.business.manager.account.AccountTransactionFlowManager;
import com.achilles.wild.server.business.service.account.BalanceService2;
import com.achilles.wild.server.common.constans.AccountConstant;
import com.achilles.wild.server.entity.account.Account;
import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

@Service
public class BalanceService2Impl implements BalanceService2 {


    @Autowired
    AccountManager accountManager;

    @Autowired
    AccountTransactionFlowManager accountTransactionFlowManager;

    private final Cache<String,Long> balanceCache = Caffeine.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).initialCapacity(10).maximumSize(1000).build();

    @Override
    public String consumeUserBalance(Account account, BalanceRequest request) {
        //加锁判断余额充足后，生成流水

        return null;
    }

    @Override
    public Long getBalance(String userId) {

        Assert.state(StringUtils.isNotEmpty(userId),"userId can not be null !");

        String key = AccountConstant.BALANCE_PREFIX + userId;
        Long balance = balanceCache.getIfPresent(key);
        if (balance != null) {
            return balance;
        }
        Long accountBalance = accountManager.getUserBalance(userId);
        //获取未入账的流水

        Long flowBalance = accountTransactionFlowManager.getInitTransactionFlowAmount(userId);

        Long sumBalance = accountBalance + flowBalance;

        balanceCache.put(key,sumBalance);

        return sumBalance;
    }
}
