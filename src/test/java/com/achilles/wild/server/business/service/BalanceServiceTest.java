package com.achilles.wild.server.business.service;

import com.achilles.wild.server.StarterApplicationTests;
import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.business.service.account.BalanceService;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

public class BalanceServiceTest  extends StarterApplicationTests {


    @Resource
    private BalanceService balanceService;

    @Test
    public void reduceUserBalance(){
        BalanceRequest request = new BalanceRequest();
        request.setUserId("ACHILLES");
        request.setKey(GenerateUniqueUtil.getUuId());
        request.setAmount(1L);
        request.setTradeDate(new Date());

        //DataResult<String> dataResult = balanceService.reduceUserBalance(request);
        //
        //System.out.println(dataResult);
    }
}
