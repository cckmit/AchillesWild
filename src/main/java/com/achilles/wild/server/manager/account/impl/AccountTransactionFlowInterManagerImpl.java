package com.achilles.wild.server.manager.account.impl;

import javax.annotation.Resource;

import com.achilles.wild.server.constans.account.AccountConstant;
import com.achilles.wild.server.dao.account.AccountTransactionFlowInterDao;
import com.achilles.wild.server.entity.account.AccountTransactionFlowInter;
import com.achilles.wild.server.manager.account.AccountTransactionFlowInterManager;
import com.achilles.wild.server.tool.date.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountTransactionFlowInterManagerImpl implements AccountTransactionFlowInterManager {


    @Resource
    private AccountTransactionFlowInterDao accountTransactionFlowInterDao;

    @Override
    public Integer getVersionByAccountCode(String accountCode, String userId, Integer tradeDay) {

        if(StringUtils.isEmpty(accountCode) || StringUtils.isEmpty(userId) || tradeDay==null){
            throw new IllegalArgumentException("geu user account flow Argument");
        }

        Integer version = accountTransactionFlowInterDao.selectVersionByAccountCode(accountCode,userId,tradeDay);

        return version==null?0:version;
    }

    @Override
    public boolean addFlow(AccountTransactionFlowInter accountTransactionFlowInter) {
        if(accountTransactionFlowInter ==null){
            return false;
        }

        Integer tradeDay = DateUtil.getIntDateFormat(DateUtil.FORMAT_YYYYMMDD, accountTransactionFlowInter.getTradeDate());
        accountTransactionFlowInter.setTradeDay(tradeDay);
        String flowNo = AccountConstant.FLOW_INTER_REDUCE_PREFIX+ DateUtil.getCurrentStr(DateUtil.FORMAT_YYYYMMDDHHMMSSSSS)+"_"+Thread.currentThread().getId();
        accountTransactionFlowInter.setFlowNo(flowNo);

        int insert = accountTransactionFlowInterDao.insertSelective(accountTransactionFlowInter);
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

        String flowNo = accountTransactionFlowInterDao.selectFlowNoByKey(key,userId);
        if(StringUtils.isEmpty(flowNo) ){
            return null;
        }

        return flowNo;
    }
}
