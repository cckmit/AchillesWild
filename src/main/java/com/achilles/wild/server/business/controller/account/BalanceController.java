package com.achilles.wild.server.business.controller.account;

import com.achilles.wild.server.business.biz.account.BalanceBiz;
import com.achilles.wild.server.business.service.account.BalanceService;
import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.common.aop.limit.annotation.RequestLimit;
import com.achilles.wild.server.model.request.account.BalanceRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.model.response.account.BalanceResponse;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import org.apache.commons.lang3.StringUtils;
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

//    @CommonQpsLimit(permitsPerSecond = 100)
    @GetMapping("/get/{userId}")
    public DataResult<BalanceResponse> getBalance(@PathVariable("userId") String userId){

        log.info("getBalance userId : {}  ",userId);

        BalanceResponse response = new BalanceResponse();

        Long balance = balanceService.getBalance(userId);

        response.setBalance(balance);

        return DataResult.success(response);
    }

    @PostMapping("/reduce")
    public DataResult<BalanceResponse> reduce(@RequestBody BalanceRequest request){

        if(!checkParam(request)){
            throw new BizException(BaseResultCode.ILLEGAL_PARAM);
        }

       BalanceResponse response = balanceBiz.reduce(request);

        if(response ==null){
            return DataResult.baseFail();
        }

        return DataResult.success(response);
    }

    private boolean checkParam(BalanceRequest request){

        if(request == null || StringUtils.isEmpty(request.getUserId()) || request.getAmount()==null || request.getAmount()<=0
                || StringUtils.isEmpty(request.getKey())){
            return false;
        }

        return true;
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
