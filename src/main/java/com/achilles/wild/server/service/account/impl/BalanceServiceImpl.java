package com.achilles.wild.server.service.account.impl;

import com.achilles.wild.server.cache.AcountLock;
import com.achilles.wild.server.common.annotations.CommonLog;
import com.achilles.wild.server.common.constans.AccountConstant;
import com.achilles.wild.server.entity.account.*;
import com.achilles.wild.server.enums.account.AmountFlowEnum;
import com.achilles.wild.server.manager.account.AccountInterManager;
import com.achilles.wild.server.manager.account.AccountManager;
import com.achilles.wild.server.manager.account.AccountTransactionFlowInterManager;
import com.achilles.wild.server.manager.account.AccountTransactionFlowManager;
import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.model.response.PageResult;
import com.achilles.wild.server.service.account.BalanceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BalanceServiceImpl implements BalanceService {

    private final static Logger LOG = LoggerFactory.getLogger(BalanceServiceImpl.class);

    @Resource
    private AccountManager accountManager;

    @Resource
    private AccountInterManager accountInterManager;

    @Resource
    private AccountTransactionFlowManager accountTransactionFlowManager;

    @Resource
    private AccountTransactionFlowInterManager accountTransactionFlowInterManager;


    @Override
    public DataResult<String> consumeUserBalance(BalanceRequest request) {

        if(!checkParam(request)){
            throw new IllegalArgumentException("consumeUserBalance check params ");
        }

        Account account = accountManager.reduceUserBalance(request.getUserId(),request.getAmount());
        if(account==null){
            throw new RuntimeException("reduceUserBalance get Account null");
        }

        //insert flow
        AccountTransactionFlow accountTransactionFlow = new AccountTransactionFlow();
        accountTransactionFlow.setUserId(request.getUserId());
        accountTransactionFlow.setAccountCode(account.getAccountCode());
        accountTransactionFlow.setIdempotent(request.getKey());
        accountTransactionFlow.setAmount(request.getAmount());
        accountTransactionFlow.setTradeDate(request.getTradeDate());
        accountTransactionFlow.setVersion(account.getVersion()+1);
        accountTransactionFlow.setType(AmountFlowEnum.MINUS.toNumbericValue());

        boolean insertFlow = accountTransactionFlowManager.addFlow(accountTransactionFlow);
        if(!insertFlow){
            throw new RuntimeException("consumeUserBalance insert user account reduce flow fail");
        }

        return DataResult.success(accountTransactionFlow.getFlowNo());
    }

    @Override
    public DataResult<String> consumeInterBalance(BalanceRequest request) {

        if(!checkParam(request)){
            throw new IllegalArgumentException(" reduceInterBalance check params ");
        }

        AccountInter account = accountInterManager.reduceInterBalance(request.getAmount());
        if(account==null){
            throw new RuntimeException("reduceUserBalance get Account null");
        }

        AccountTransactionFlowInter accountTransactionFlowInter = new AccountTransactionFlowInter();
        accountTransactionFlowInter.setUserId(AccountConstant.ACCOUNT_INTER_USER_ID);
        accountTransactionFlowInter.setAccountCode(account.getAccountCode());
        accountTransactionFlowInter.setIdempotent(request.getKey());
        accountTransactionFlowInter.setAmount(request.getAmount());
        accountTransactionFlowInter.setType(AmountFlowEnum.MINUS.toNumbericValue());
        accountTransactionFlowInter.setTradeDate(request.getTradeDate());
        accountTransactionFlowInter.setVersion(account.getVersion()+1);

        boolean updateFlow = accountTransactionFlowInterManager.addFlow(accountTransactionFlowInter);
        if(!updateFlow){
            throw new RuntimeException("insert inter account flow fail");
        }

        return DataResult.success(accountTransactionFlowInter.getFlowNo());
    }

    @Override
    public DataResult<String> addInterBalance(BalanceRequest request) {

        if(!checkParam(request)){
            throw new IllegalArgumentException("check params ");
        }

        AccountInter accountInter = accountInterManager.getCollectAccountInter();
        if(accountInter==null){
            throw new RuntimeException(" user account locked/missing");
        }

        boolean update =accountInterManager.updateBalanceById(accountInter.getId(),request.getAmount());
        if(!update){
            AcountLock.unLock(accountInter.getAccountCode());
            throw new RuntimeException("update accountInter fail");
        }

        AccountTransactionFlowInterAdd accountTransactionFlowInter = new AccountTransactionFlowInterAdd();
        accountTransactionFlowInter.setUserId(AccountConstant.ACCOUNT_INTER_USER_ID);
        accountTransactionFlowInter.setAccountCode(accountInter.getAccountCode());
        accountTransactionFlowInter.setIdempotent(request.getKey());
        accountTransactionFlowInter.setAmount(request.getAmount());
       // accountTransactionFlowInter.setFlowNo(accountTransactionFlowInterAddManager.getFlowNo());
        accountTransactionFlowInter.setTradeDate(request.getTradeDate());

        boolean updateFlow = false;
            //accountTransactionFlowInterAddManager.addFlow(accountTransactionFlowInter);
        if(!updateFlow){
            AcountLock.unLock(accountInter.getAccountCode());
            throw new RuntimeException("insert inter account flow fail");
        }

        AcountLock.unLock(accountInter.getAccountCode());
        return DataResult.success(accountTransactionFlowInter.getFlowNo());
    }



    public PageResult<String> addUserBalance(BalanceRequest request) {

        //if(!checkParam(request)){
        //    throw new IllegalArgumentException("check params ");
        //}
        //
        //Account account = accountManager.getCollectAccount(request.getUserId());
        //if(account==null){
        //    AcountLock.unLock(account.getAccountCode());
        //    throw new RuntimeException("get user Account null");
        //}
        //
        //boolean updateBalance = accountManager.updateBalanceById(account.getId(),request.getAmount());
        //if(!updateBalance){
        //    AcountLock.unLock(account.getAccountCode());
        //    throw new RuntimeException("update balance fail");
        //}
        //
        ////insert flow
        //AccountTransactionFlowAdd accountTransactionFlow = new AccountTransactionFlowAdd();
        //accountTransactionFlow.setUserId(request.getUserId());
        //accountTransactionFlow.setAccountCode(account.getAccountCode());
        //accountTransactionFlow.setIdempotent(request.getKey());
        //accountTransactionFlow.setAmount(request.getAmount());
        //accountTransactionFlow.setFlowNo(accountTransactionFlowAddManager.getFlowNo());
        //accountTransactionFlow.setTradeDate(request.getTradeDate());
        //
        //boolean updateFlow = accountTransactionFlowAddManager.addFlow(accountTransactionFlow);
        //if(!updateFlow){
        //    AcountLock.unLock(account.getAccountCode());
        //    throw new RuntimeException("insert user account flow fail");
        //}
        //
        //AcountLock.unLock(account.getAccountCode());
        return PageResult.success(null);
    }

    @CommonLog
    @Override
    public Long getBalance(String userId) {

        if(StringUtils.isEmpty(userId)){
            return 0L;
        }

        return accountManager.getUserBalance(userId);
    }

    private boolean checkParam(BalanceRequest request){

        if(request == null || StringUtils.isEmpty(request.getUserId()) || request.getAmount()==null || request.getAmount()<=0
            || StringUtils.isEmpty(request.getKey())){
            return false;
        }

        return true;
    }

    @Override
    public Long getInterBalance() {

        return accountInterManager.getBalance(AccountConstant.ACCOUNT_INTER_USER_ID);
    }
}
