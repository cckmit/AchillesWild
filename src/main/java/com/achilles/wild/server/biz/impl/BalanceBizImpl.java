package com.achilles.wild.server.biz.impl;

import com.achilles.wild.server.biz.BalanceBiz;
import com.achilles.wild.server.manager.account.AccountTransactionFlowManager;
import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.model.response.ResultCode;
import com.achilles.wild.server.model.response.account.BalanceResponse;
import com.achilles.wild.server.service.account.BalanceService;
import com.achilles.wild.server.tool.date.DateUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
    public DataResult<BalanceResponse> reduce(BalanceRequest request) {

        if(!checkParam(request)){
            return DataResult.baseFail(ResultCode.ILLEGAL_PARAM);
        }

        if(StringUtils.isNotBlank(request.getTradeDateStr())){
            request.setTradeDate(DateUtil.getDateFormat(DateUtil.FORMAT_YYYY_MM_DD_HHMMSS,request.getTradeDateStr()));
        }

        //idempotent
//        String flowNo =  accountTransactionFlowManager.getFlowNoByKey(request.getKey(),request.getUserId());
        //if(flowNo==null){
        //    flowNo = accountTransactionFlowManager.getFlowNoByKey(request.getKey(),request.getUserId());
        //}

        BalanceResponse response = new BalanceResponse();
//        Long balance =null;
//        if(flowNo!=null){
//            //keyCache.put(request.getKey(),flowNo);
//            balance = balanceService.getBalance(request.getUserId());
//            response.setBalance(balance);
//            response.setFlowNo(flowNo);
//            return DataResult.success(response);
//        }
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

        DataResult<String> dataResult = balanceService.consumeUserBalance(request);
        if(dataResult ==null || !dataResult.isSuccess()){
            throw new RuntimeException(" consumeUserBalance  fail");
        }
        response.setFlowNo(dataResult.getData());

        dataResult = balanceService.consumeInterBalance(request);
        if(dataResult ==null || !dataResult.isSuccess()){
            throw new RuntimeException(" consumeInterBalance  fail");
        }

        Long balance = balanceService.getBalance(request.getUserId());
        response.setBalance(balance);

        return DataResult.success(response);
    }

    @Override
    @Transactional(rollbackForClassName ="Exception")
    public DataResult<String> add(BalanceRequest request) {

        if(!checkParam(request)){
            return DataResult.baseFail(ResultCode.ILLEGAL_PARAM);
        }

        if(request.getTradeDate()==null){
            request.setTradeDate(new Date());
        }

        //idempotent
        String flowNo =  keyCache.getIfPresent(request.getKey());
        if(flowNo!=null){
            return DataResult.success(flowNo);
        }else{
            //flowNo = accountTransactionFlowAddManager.getFlowNoByKey(request.getKey(),request.getUserId());
            if(flowNo!=null){
                keyCache.put(request.getKey(),flowNo);
                return DataResult.success(flowNo);
            }
        }

        DataResult<String> dataResult = balanceService.addInterBalance(request);
        if(dataResult ==null || !dataResult.isSuccess()){
            throw new RuntimeException(" addUserBalance  fail");
        }

        dataResult = balanceService.addInterBalance(request);
        if(dataResult ==null || !dataResult.isSuccess()){
            throw new RuntimeException(" addInterBalance  fail");
        }

        keyCache.put(request.getKey(), dataResult.getData());
        return DataResult.success(dataResult.getData());
    }


    private boolean checkParam(BalanceRequest request){

        if(request == null || StringUtils.isEmpty(request.getUserId()) || request.getAmount()==null || request.getAmount()<=0
            || StringUtils.isEmpty(request.getKey())){
            return false;
        }

        return true;
    }
}
