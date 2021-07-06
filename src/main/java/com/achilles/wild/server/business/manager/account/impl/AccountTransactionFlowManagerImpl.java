package com.achilles.wild.server.business.manager.account.impl;

import com.achilles.wild.server.business.dao.account.AccountTransactionFlowDao;
import com.achilles.wild.server.business.manager.account.AccountTransactionFlowManager;
import com.achilles.wild.server.common.constans.AccountConstant;
import com.achilles.wild.server.entity.account.AccountTransactionFlow;
import com.achilles.wild.server.tool.date.DateUtil;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service
public class AccountTransactionFlowManagerImpl implements AccountTransactionFlowManager {


    @Resource
    private AccountTransactionFlowDao accountTransactionFlowDao;

    @Override
    public boolean addFlow(AccountTransactionFlow accountTransactionFlow) {

        Assert.state(accountTransactionFlow != null,"accountTransactionFlow can not be null !");

        String flowNo = AccountConstant.FLOW_USER_REDUCE_PREFIX
                + DateUtil.getCurrentStr(DateUtil.YYYY_MM_DD_HH_MM_SS_SSS)
                + "_" + Thread.currentThread().getId()
                + "_" + GenerateUniqueUtil.getUuId();
        accountTransactionFlow.setFlowNo(flowNo);
        accountTransactionFlow.setCreateTime(System.currentTimeMillis());
        accountTransactionFlow.setUpdateTime(System.currentTimeMillis());
        int insert = accountTransactionFlowDao.insertSelective(accountTransactionFlow);
        if(insert==0){
            return false;
        }

        return true;
    }

    @Override
    public String getFlowNoByKey(String key, String userId) {
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(userId)){
           throw new IllegalArgumentException();
        }

        String flowNo = accountTransactionFlowDao.selectFlowNoByKey(key,userId);
        if(StringUtils.isEmpty(flowNo) ){
            return null;
        }

        return flowNo;
    }
}
