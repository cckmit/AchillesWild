package com.achilles.wild.server.business.controller;

import com.achilles.wild.server.business.entity.account.Account;
import com.achilles.wild.server.business.service.account.BalanceService;
import com.achilles.wild.server.common.config.ConfigComplex;
import com.achilles.wild.server.common.config.ConfigProperties;
import com.achilles.wild.server.common.config.ConfigProperties1;
import com.achilles.wild.server.common.config.ConfigProperties2;
import com.achilles.wild.server.common.aop.listener.event.EventListenerConfig;
import com.achilles.wild.server.common.aop.listener.event.EventListenerConfigEvent;
import com.achilles.wild.server.common.aop.listener.event.MyApplicationEvent;
import com.achilles.wild.server.other.design.proxy.cglib.CglibInterceptor;
import com.achilles.wild.server.other.design.proxy.cglib.ServiceClient;
import com.achilles.wild.server.other.design.proxy.jdk.JavaProxyInvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private ApplicationListener myApplicationListener;

    @Autowired
    private EventListenerConfig eventListenerConfig;

//    @ControllerLog
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

    @GetMapping(path = "/get/{name}")
    public String getName(@PathVariable("name") String name){

        Long.parseLong(name);

        return "AchillesWild";
    }

    @PostConstruct
    public void getDBUserName(){
        log.info("PostConstruct ---------test UUID:"+ UUID.randomUUID());
    }

    @GetMapping(path = "/event")
    public String invokeEvent(){

        Account account = new Account();
        account.setId(23L);
        myApplicationListener.onApplicationEvent(new MyApplicationEvent(account));
        eventListenerConfig.handleEvent(new EventListenerConfigEvent(account));
        return "invokeEvent ok";
    }
}
