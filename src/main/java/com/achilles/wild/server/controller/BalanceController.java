package com.achilles.wild.server.controller;

import javax.annotation.Resource;

import com.achilles.wild.server.biz.BalanceBiz;
import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.model.response.ResultCode;
import com.achilles.wild.server.model.response.account.BalanceResponse;
import com.achilles.wild.server.service.account.BalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/balance")
public class BalanceController {

    private final static Logger LOG = LoggerFactory.getLogger(BalanceController.class);


    @Resource
    private BalanceBiz balanceBiz;

    @Resource
    private BalanceService balanceService;


    @RequestMapping("/reduce")
    public DataResult<BalanceResponse> reduce(BalanceRequest request){

        DataResult<BalanceResponse> dataResult;

        try {
            dataResult = balanceBiz.reduce(request);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("theVeryIncome error",e);
            return DataResult.baseFail(ResultCode.EXCEPTION);
        }

        if(dataResult==null || !dataResult.isSuccess()){
            return DataResult.baseFail();
        }

        return dataResult;
    }


    @RequestMapping("/add")
    public DataResult<BalanceResponse> add(BalanceRequest request){

        DataResult<String> dataResult;

        try {
            dataResult = balanceBiz.add(request);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("theVeryIncome error",e);
            return DataResult.baseFail(ResultCode.EXCEPTION);
        }

        if(dataResult==null || !dataResult.isSuccess()){
            return DataResult.baseFail();
        }

        BalanceResponse response = new BalanceResponse();
        response.setFlowNo(dataResult.getData());
        Long balance = balanceService.getBalance(request.getUserId());
        response.setBalance(balance);

        return DataResult.success(response);
    }
}
