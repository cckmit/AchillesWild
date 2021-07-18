package com.achilles.wild.server.business.service.account.impl;

import com.achilles.wild.server.business.manager.account.AccountInterManager;
import com.achilles.wild.server.business.manager.account.AccountManager;
import com.achilles.wild.server.business.manager.account.AccountTransactionFlowInterManager;
import com.achilles.wild.server.business.manager.account.AccountTransactionFlowManager;
import com.achilles.wild.server.business.service.account.BalanceService;
import com.achilles.wild.server.common.cache.AccountLock;
import com.achilles.wild.server.common.constans.AccountConstant;
import com.achilles.wild.server.entity.account.*;
import com.achilles.wild.server.enums.account.AmountFlowEnum;
import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    private Lock lock = new ReentrantLock();

    private final Cache<String,Long> balanceCache = Caffeine.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).initialCapacity(10).maximumSize(1000).build();

    private final Cache<String,List<Account>> subBalanceCache = Caffeine.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).initialCapacity(10).maximumSize(1000).build();


    public Long getBalance2w(String userId) {

        List<Account> consumeAccounts = subBalanceCache.getIfPresent(userId);

        Long sumBalance = 0L;
        for (Account account : consumeAccounts) {
            Long balance = account.getBalance();
            sumBalance += balance;
        }

        Long sumBalance2 = balanceCache.getIfPresent(userId);
        //查余额，查流水
        if (sumBalance2 == null) {
        }
        return 0l;
    }

    public String consumeUserBalance2(String userId,BalanceRequest request) {

        List<Account> consumeAccounts = new ArrayList<>();
        for (Account account : consumeAccounts) {
            Long amount = request.getAmount();
            if (account.getBalance() < amount) {
                continue;
            }
            lock.tryLock();
            try {
                if (account.getBalance() < amount) {
                    continue;
                }
                account.setBalance(account.getBalance() - amount);

                //insert flow
                AccountTransactionFlow accountTransactionFlow = new AccountTransactionFlow();
                accountTransactionFlow.setUserId(request.getUserId());
                accountTransactionFlow.setAccountCode(account.getAccountCode());
                accountTransactionFlow.setIdempotent(request.getKey());
                accountTransactionFlow.setBalance(account.getBalance());
                accountTransactionFlow.setAmount(request.getAmount());
                accountTransactionFlow.setTradeTime(request.getTradeTime());
                accountTransactionFlow.setVersion(account.getVersion());
                accountTransactionFlow.setFlowType(AmountFlowEnum.MINUS.toNumbericValue());

                boolean insertFlow = accountTransactionFlowManager.addFlow(accountTransactionFlow);
                if(!insertFlow){
                    throw new RuntimeException("consumeUserBalance insert user account reduce flow fail");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }


        }


        return null;
    }

    @Override
    public String consumeUserBalance(Account account,BalanceRequest request) {

        Assert.state(account != null,"account can not be null !");
        if(!checkParam(request)){
            throw new IllegalArgumentException("consumeUserBalance check params ");
        }

        boolean reduceAccount = accountManager.reduceUserBalance(account,request.getAmount());
        if(!reduceAccount){
            throw new RuntimeException("reduce User Balance fail null");
        }

        //insert flow
        AccountTransactionFlow accountTransactionFlow = new AccountTransactionFlow();
        accountTransactionFlow.setUserId(request.getUserId());
        accountTransactionFlow.setAccountCode(account.getAccountCode());
        accountTransactionFlow.setIdempotent(request.getKey());
        accountTransactionFlow.setBalance(account.getBalance());
        accountTransactionFlow.setAmount(request.getAmount());
        accountTransactionFlow.setTradeTime(request.getTradeTime());
        accountTransactionFlow.setVersion(account.getVersion());
        accountTransactionFlow.setFlowType(AmountFlowEnum.MINUS.toNumbericValue());

        boolean insertFlow = accountTransactionFlowManager.addFlow(accountTransactionFlow);
        if(!insertFlow){
            throw new RuntimeException("consumeUserBalance insert user account reduce flow fail");
        }

        return accountTransactionFlow.getFlowNo();
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
//        accountTransactionFlowInter.setTradeDate(request.getTradeDate());
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
            AccountLock.unLock(accountInter.getAccountCode());
            throw new RuntimeException("update accountInter fail");
        }

        AccountTransactionFlowInterAdd accountTransactionFlowInter = new AccountTransactionFlowInterAdd();
        accountTransactionFlowInter.setUserId(AccountConstant.ACCOUNT_INTER_USER_ID);
        accountTransactionFlowInter.setAccountCode(accountInter.getAccountCode());
        accountTransactionFlowInter.setIdempotent(request.getKey());
        accountTransactionFlowInter.setAmount(request.getAmount());
       // accountTransactionFlowInter.setFlowNo(accountTransactionFlowInterAddManager.getFlowNo());
//        accountTransactionFlowInter.setTradeDate(request.getTradeDate());

//        boolean updateFlow = accountInterManager.addFlow(accountTransactionFlowInter);
//        if(!updateFlow){
//            AcountLock.unLock(accountInter.getAccountCode());
//            throw new RuntimeException("insert inter account flow fail");
//        }

        AccountLock.unLock(accountInter.getAccountCode());
        return DataResult.success(accountTransactionFlowInter.getFlowNo());
    }

    @Override
    public Long getBalance(String userId) {

        Assert.state(StringUtils.isNotEmpty(userId),"userId can not be null");

        return accountManager.getUserBalance(userId);
    }

    @Override
    public Long getBalance2(String userId) {

        Assert.state(StringUtils.isNotEmpty(userId),"userId can not be null");

        Long balance = accountManager.getUserBalance(userId);
        Long todayIncome = accountTransactionFlowManager.getUserTodayIncome(userId);

        balance += todayIncome;

        return balance;
    }

    private boolean checkParam(BalanceRequest request){

        if(request == null || StringUtils.isEmpty(request.getUserId()) || request.getAmount()==null || request.getAmount()<=0
            || StringUtils.isEmpty(request.getKey())){
            return false;
        }

        return true;
    }

}
