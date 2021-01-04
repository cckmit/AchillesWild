package com.service;

import java.util.Date;

import javax.annotation.Resource;

import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.service.account.BalanceService;
import com.tool.BaseSpringJUnitTest;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import org.junit.Test;

public class BalanceServiceTest  extends BaseSpringJUnitTest {


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
