package com.achilles.wild.server.controller.account;

import com.achilles.wild.server.biz.BalanceBiz;
import com.achilles.wild.server.common.annotations.CommonLog;
import com.achilles.wild.server.common.annotations.RequestLimit;
import com.achilles.wild.server.design.proxy.cglib.CglibInterceptor;
import com.achilles.wild.server.design.proxy.cglib.ServiceClient;
import com.achilles.wild.server.design.proxy.jdk.JavaProxyInvocationHandler;
import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.model.response.ResultCode;
import com.achilles.wild.server.model.response.account.BalanceResponse;
import com.achilles.wild.server.service.account.BalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/balance",produces = {"application/json;charset=UTF-8"})
public class BalanceController {

    private final static Logger log = LoggerFactory.getLogger(BalanceController.class);

    @Resource
    private BalanceBiz balanceBiz;

    @Resource
    private BalanceService balanceService;

    @Autowired
    private CglibInterceptor cglibInterceptor;

    @RequestLimit
    @CommonLog
    @GetMapping("/get/{userId}")
    public DataResult<BalanceResponse> getBalance(@PathVariable("userId") String userId){

        BalanceResponse response = new BalanceResponse();

        BalanceService proxyInstance = (BalanceService)new JavaProxyInvocationHandler(balanceService).newProxyInstance();
        Long balance = proxyInstance.getBalance(userId);

        ServiceClient serviceClient = (ServiceClient) cglibInterceptor.newProxyInstance(ServiceClient.class);
        serviceClient.doIt();
//        Long balance = null;
//        try {
//            balance = balanceService.getBalance(userId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return DataResult.baseFail(ResultCode.FAIL.code,e.getMessage());
//        }
        response.setBalance(balance);

        return DataResult.success(response);
    }

    @RequestLimit
    @CommonLog
    @PostMapping("/reduce")
    public DataResult<BalanceResponse> reduce(@RequestBody(required = true)BalanceRequest request){

        DataResult<BalanceResponse> dataResult;

        try {
            dataResult = balanceBiz.reduce(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("theVeryIncome error",e);
            return DataResult.baseFail(ResultCode.EXCEPTION);
        }

        if(dataResult ==null){
            return DataResult.baseFail();
        }

        return dataResult;
    }

    @RequestLimit
    @CommonLog
    @PostMapping(path = "/add")
    public DataResult<BalanceResponse> add(@RequestBody(required = true) BalanceRequest request){

        DataResult<String> dataResult;

        try {
            dataResult = balanceBiz.add(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("theVeryIncome error",e);
            return DataResult.baseFail(ResultCode.EXCEPTION);
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
