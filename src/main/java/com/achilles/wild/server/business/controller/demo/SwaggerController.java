package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.entity.account.Account;
import com.achilles.wild.server.model.request.account.AccountParamRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.model.response.account.BalanceResponse;
import com.achilles.wild.server.tool.json.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/swagger", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "swagger测试")
public class SwaggerController {

    private final static Logger log = LoggerFactory.getLogger(SwaggerController.class);

    @ApiOperation(value = "增加账户",notes = "返回账户余额")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK", response = BalanceResponse.class) })
    @PostMapping("/add")
    public BalanceResponse getName(@RequestBody AccountParamRequest request){

        log.info("-------------------request:"+ JsonUtil.toJsonString(request));

        BalanceResponse response = new BalanceResponse();
        response.setFlowNo("2021_flow");
        response.setBalance(100l);

        return response;
    }
}
