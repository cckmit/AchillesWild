package com.achilles.wild.server.biz.impl;

import com.achilles.wild.server.biz.BalanceBiz;
import com.achilles.wild.server.common.annotations.CommonLog;
import com.achilles.wild.server.manager.account.AccountTransactionFlowManager;
import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.model.response.PageResult;
import com.achilles.wild.server.model.response.ResultCode;
import com.achilles.wild.server.model.response.account.BalanceResponse;
import com.achilles.wild.server.service.account.BalanceService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class BalanceBizImpl implements BalanceBiz {

    private final static Logger LOG = LoggerFactory.getLogger(BalanceBizImpl.class);

    @Resource
    private BalanceService balanceService;

    //@Resource
    //private AccountTransactionFlowAddManager accountTransactionFlowAddManager;

    @Resource
    private AccountTransactionFlowManager accountTransactionFlowManager;

    private static Cache<String,String> keyCache = CacheBuilder.newBuilder().concurrencyLevel(1000).maximumSize(20000).expireAfterWrite(1, TimeUnit.SECONDS).build();

    private static Cache<String,Long> balanceCache = CacheBuilder.newBuilder().concurrencyLevel(1000).maximumSize(20000).expireAfterWrite(1, TimeUnit.SECONDS).build();

    @Override
    @Transactional(rollbackForClassName ="Exception")
    public PageResult<BalanceResponse> reduce(BalanceRequest request) {

        if(!checkParam(request)){
            return PageResult.baseFail(ResultCode.ILLEGAL_PARAM);
        }

        //idempotent
        String flowNo =  accountTransactionFlowManager.getFlowNoByKey(request.getKey(),request.getUserId());
        //if(flowNo==null){
        //    flowNo = accountTransactionFlowManager.getFlowNoByKey(request.getKey(),request.getUserId());
        //}

        BalanceResponse response = new BalanceResponse();
        Long balance =null;
        if(flowNo!=null){
            //keyCache.put(request.getKey(),flowNo);
            balance = balanceService.getBalance(request.getUserId());
            response.setBalance(balance);
            response.setFlowNo(flowNo);
            return PageResult.success(response);
        }
        //
        //Long balance =  balanceCache.getIfPresent(request.getKey());
        //if(balance==null){
        //    balance = balanceService.getBalance(request.getUserId());
        //}
        //
        //if(balance<request.getAmount()){
        //    return DataResult.baseFail(ResultCode.BALANCE_NOT_ENOUGH);
        //}
        //
        //balance = balanceService.getInterBalance();

        PageResult<String> pageResult = balanceService.consumeUserBalance(request);
        if(pageResult ==null || !pageResult.isSuccess()){
            throw new RuntimeException(" consumeUserBalance  fail");
        }
        response.setFlowNo(pageResult.getData());

        pageResult = balanceService.consumeInterBalance(request);
        if(pageResult ==null || !pageResult.isSuccess()){
            throw new RuntimeException(" consumeInterBalance  fail");
        }

        balance = balanceService.getBalance(request.getUserId());
        response.setBalance(balance);

        return PageResult.success(response);
    }

    @Override
    @CommonLog
    @Transactional(rollbackForClassName ="Exception")
    public PageResult<String> add(BalanceRequest request) {

        if(!checkParam(request)){
            return PageResult.baseFail(ResultCode.ILLEGAL_PARAM);
        }

        //idempotent
        String flowNo =  keyCache.getIfPresent(request.getKey());
        if(flowNo!=null){
            return PageResult.success(flowNo);
        }else{
            //flowNo = accountTransactionFlowAddManager.getFlowNoByKey(request.getKey(),request.getUserId());
            if(flowNo!=null){
                keyCache.put(request.getKey(),flowNo);
                return PageResult.success(flowNo);
            }
        }

        PageResult<String> pageResult = null;
            //balanceService.addUserBalance(request);
        if(pageResult ==null || !pageResult.isSuccess()){
            throw new RuntimeException(" addUserBalance  fail");
        }

        pageResult = balanceService.addInterBalance(request);
        if(pageResult ==null || !pageResult.isSuccess()){
            throw new RuntimeException(" addInterBalance  fail");
        }

        keyCache.put(request.getKey(), pageResult.getData());
        return PageResult.success(pageResult.getData());
    }


    private boolean checkParam(BalanceRequest request){

        if(request == null || StringUtils.isEmpty(request.getUserId()) || request.getAmount()==null || request.getAmount()<=0
            || StringUtils.isEmpty(request.getKey())){
            return false;
        }

        return true;
    }
}
