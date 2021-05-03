package com.achilles.wild.server.business.controller.account;

import com.achilles.wild.server.business.biz.account.BalanceBiz;
import com.achilles.wild.server.business.service.account.BalanceService;
import com.achilles.wild.server.common.aop.interceptor.NoCheckToken;
import com.achilles.wild.server.common.aop.limit.annotation.CommonQpsLimit;
import com.achilles.wild.server.common.aop.limit.annotation.RequestLimit;
import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.model.response.account.BalanceResponse;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
public class BalanceController {

    private final static Logger log = LoggerFactory.getLogger(BalanceController.class);

    @Resource
    private BalanceBiz balanceBiz;

    @Resource
    private BalanceService balanceService;

    @NoCheckToken
    @CommonQpsLimit(key="getBalance",permitsPerSecond = 0.1)
    @GetMapping("/get/{userId}")
    public DataResult<BalanceResponse> getBalance(@PathVariable("userId") String userId){

        BalanceResponse response = new BalanceResponse();

        Long balance = balanceService.getBalance(userId);

        response.setBalance(balance);

        return DataResult.success(response);
    }

    @RequestLimit
    @PostMapping("/reduce")
    public DataResult<BalanceResponse> reduce(@RequestBody(required = true)BalanceRequest request){

        DataResult<BalanceResponse> dataResult = balanceBiz.reduce(request);

        if(dataResult ==null){
            return DataResult.baseFail();
        }

        return dataResult;
    }

    @RequestLimit
    @PostMapping(path = "/add")
    public DataResult<BalanceResponse> add(@RequestBody(required = true) BalanceRequest request){

        DataResult<String> dataResult;

        try {
            dataResult = balanceBiz.add(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("theVeryIncome error",e);
            return DataResult.baseFail(BaseResultCode.EXCEPTION);
        }

        if(dataResult ==null || !dataResult.isSuccess()){
            return DataResult.baseFail();
        }

        BalanceResponse response = new BalanceResponse();
//        response.setFlowNo(dataResult.getData());
        Long balance = balanceService.getBalance(request.getUserId());
        response.setBalance(balance);

        return DataResult.success(response);
    }
}
