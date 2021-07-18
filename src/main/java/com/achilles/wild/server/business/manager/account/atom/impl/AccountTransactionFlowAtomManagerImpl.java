package com.achilles.wild.server.business.manager.account.atom.impl;

import com.achilles.wild.server.business.dao.account.AccountTransactionFlowDao;
import com.achilles.wild.server.business.manager.account.atom.AccountTransactionFlowAtomManager;
import com.achilles.wild.server.entity.account.AccountTransactionFlow;
import com.achilles.wild.server.enums.StatusEnum;
import com.achilles.wild.server.tool.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class AccountTransactionFlowAtomManagerImpl implements AccountTransactionFlowAtomManager {


    @Autowired
    private AccountTransactionFlowDao accountTransactionFlowDao;

    @Override
    public List<AccountTransactionFlow> getUserTransactionFlows(String userId) {

        Assert.state(StringUtils.isNotEmpty(userId),"userId can not be null !");

        Long tradeTimeStart = DateUtil.getTheDayFirstTime(0).getTime();
        Long tradeTimeEnd = DateUtil.getTheDayLastTime(0).getTime();
        List<AccountTransactionFlow> transactionFlowList = accountTransactionFlowDao.selectTransactionFlows(userId,tradeTimeStart,tradeTimeEnd,null, StatusEnum.NORMAL.toNumbericValue());

        return transactionFlowList;
    }

    @Override
    public List<AccountTransactionFlow> getInitTransactionFlows(String userId) {

        Assert.state(StringUtils.isNotEmpty(userId),"userId can not be null !");

        Long createTimeStart = DateUtil.getTheDayFirstTime(-3).getTime();

        List<AccountTransactionFlow> transactionFlowList = accountTransactionFlowDao.selectTransactionFlows(userId,null,null,createTimeStart, StatusEnum.NORMAL.toNumbericValue());

        return transactionFlowList;
    }
}
