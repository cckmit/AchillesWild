package com.achilles.wild.server.manager.account.impl;

import javax.annotation.Resource;

import com.achilles.wild.server.common.constans.AccountConstant;
import com.achilles.wild.server.dao.account.AccountTransactionFlowDao;
import com.achilles.wild.server.entity.account.AccountTransactionFlow;
import com.achilles.wild.server.manager.account.AccountTransactionFlowManager;
import com.achilles.wild.server.tool.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountTransactionFlowManagerImpl implements AccountTransactionFlowManager {


    @Resource
    private AccountTransactionFlowDao accountTransactionFlowDao;

    @Override
    public boolean addFlow(AccountTransactionFlow accountTransactionFlow) {

        if(accountTransactionFlow==null){
            return false;
        }

        String flowNo = AccountConstant.FLOW_USER_REDUCE_PREFIX+DateUtil.getCurrentStr(DateUtil.FORMAT_YYYYMMDDHHMMSSSSS)+"_"+Thread.currentThread().getId();
        accountTransactionFlow.setFlowNo(flowNo);
        Integer tradeDay = DateUtil.getIntDateFormat(DateUtil.FORMAT_YYYYMMDD,accountTransactionFlow.getTradeDate());
        accountTransactionFlow.setTradeDay(tradeDay);
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
