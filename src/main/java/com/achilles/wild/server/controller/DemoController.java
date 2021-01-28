package com.achilles.wild.server.controller;

import com.achilles.wild.server.common.annotations.ControllerLog;
import com.achilles.wild.server.common.config.ConfigComplex;
import com.achilles.wild.server.common.config.ConfigProperties;
import com.achilles.wild.server.common.config.ConfigProperties1;
import com.achilles.wild.server.common.config.ConfigProperties2;
import com.achilles.wild.server.design.proxy.cglib.CglibInterceptor;
import com.achilles.wild.server.design.proxy.cglib.ServiceClient;
import com.achilles.wild.server.design.proxy.jdk.JavaProxyInvocationHandler;
import com.achilles.wild.server.entity.account.Account;
import com.achilles.wild.server.service.account.BalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping(value = "/demo",produces = {"application/json;charset=UTF-8"})
public class DemoController {

    private final static Logger log = LoggerFactory.getLogger(DemoController.class);
    
    @Autowired
    ConfigProperties configProperties;

    @Autowired
    ConfigProperties1 configProperties1;

    @Autowired
    ConfigProperties2 configProperties2;

    @Autowired
    ConfigComplex configComplex;

    @Autowired
    Environment environment;

    @Resource
    private BalanceService balanceService;

    @ControllerLog
    @GetMapping(path = "/{id}")
    public Long getConfig(@PathVariable("id") Long id,
                             @RequestParam(name="name",defaultValue = "Achilles") String name,
                             @RequestParam(name="limit",defaultValue = "10") Integer limit,
                             @RequestHeader(name = "traceId",required = false) String traceId){

        log.info("=====configProperties ===="+configProperties.getUsername());
        log.info("=====configProperties1 ===="+configProperties1.getName());
        log.info("=====configProperties2 ===="+configProperties2.getHouse());
        log.info("=====configComplex ===="+configComplex.dbConfig().getUrl());
        log.info("=====environment ==house=="+environment.getProperty("house"));
        log.info("=====environment ==spring.datasource.url=="+environment.getProperty("spring.datasource.url"));

        BalanceService proxyInstance = (BalanceService)new JavaProxyInvocationHandler(balanceService).newProxyInstance();
        Long balance = proxyInstance.getBalance(id.toString());

        ServiceClient serviceClient = (ServiceClient) new CglibInterceptor().newProxyInstance(ServiceClient.class);
        serviceClient.doIt();

        Account account = new Account();
        account.setId(id);
        account.setAccountCode(traceId);
        account.setBalance(balance);
        return id;
    }

    @PostMapping(path = "/save")
    public Account save(@RequestBody(required = true) Account account){

        log.info("=====params ==========save========account:"+account);

        return account;
    }

    @PostConstruct
    public void getDBUserName(){
        log.info("PostConstruct ---------test UUID:"+ UUID.randomUUID());
    }
}
