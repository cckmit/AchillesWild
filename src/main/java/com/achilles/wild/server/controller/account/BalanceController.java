package com.achilles.wild.server.controller.account;

import com.achilles.wild.server.biz.BalanceBiz;
import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.model.response.PageResult;
import com.achilles.wild.server.model.response.ResultCode;
import com.achilles.wild.server.model.response.account.BalanceResponse;
import com.achilles.wild.server.service.account.BalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/balance",produces = {"application/json;charset=UTF-8"})
public class BalanceController {

    private final static Logger log = LoggerFactory.getLogger(BalanceController.class);

    @Resource
    private BalanceBiz balanceBiz;

    @Resource
    private BalanceService balanceService;


    @GetMapping("/get/{userId}")
    public DataResult<BalanceResponse> getBalance(@PathVariable("userId") String userId){

        log.info("----------------------------userId:"+userId+"--------------------------------");

        BalanceResponse response = new BalanceResponse();
        Long balance = balanceService.getBalance(userId);
        response.setBalance(balance);

        return DataResult.success(response);
    }

    @RequestMapping("/reduce")
    public PageResult<BalanceResponse> reduce(BalanceRequest request){

        PageResult<BalanceResponse> pageResult;

        try {
            pageResult = balanceBiz.reduce(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("theVeryIncome error",e);
            return PageResult.baseFail(ResultCode.EXCEPTION);
        }

        if(pageResult ==null || !pageResult.isSuccess()){
            return PageResult.baseFail();
        }

        return pageResult;
    }


    @RequestMapping("/add")
    public PageResult<BalanceResponse> add(BalanceRequest request){

        PageResult<String> pageResult;

        try {
            pageResult = balanceBiz.add(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("theVeryIncome error",e);
            return PageResult.baseFail(ResultCode.EXCEPTION);
        }

        if(pageResult ==null || !pageResult.isSuccess()){
            return PageResult.baseFail();
        }

        BalanceResponse response = new BalanceResponse();
        response.setFlowNo(pageResult.getData());
        Long balance = balanceService.getBalance(request.getUserId());
        response.setBalance(balance);

        return PageResult.success(response);
    }
}
