package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.business.service.account.BalanceService;
import com.achilles.wild.server.common.aop.limit.BlockHandler;
import com.achilles.wild.server.common.aop.limit.FallBackHandler;
import com.achilles.wild.server.common.config.ConfigComplex;
import com.achilles.wild.server.common.config.ConfigProperties;
import com.achilles.wild.server.common.config.ConfigProperties1;
import com.achilles.wild.server.common.config.ConfigProperties2;
import com.achilles.wild.server.common.listener.event.MyApplicationEvent;
import com.achilles.wild.server.entity.account.Account;
import com.achilles.wild.server.other.design.proxy.cglib.CglibInterceptor;
import com.achilles.wild.server.other.design.proxy.cglib.ServiceClient;
import com.achilles.wild.server.other.design.proxy.jdk.JavaProxyInvocationHandler;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    private BalanceService balanceService;

    @Autowired
    private ApplicationContext applicationContext;

    @Value("#{${test.map}}")
    private Map<String,String> map;

    @GetMapping(path = "/flow/{name}")
    @SentinelResource(value = "limit_test",
            blockHandlerClass = BlockHandler.class,blockHandler = "degrade",
            fallbackClass = FallBackHandler.class,fallback = "fallback")
    public String flow(@PathVariable("name") String name){

        // 资源名
        log.info("==================name ============"+name);

        Long.parseLong(name);

        try {
            Thread.sleep(201L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "AchillesWild";
    }

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

    @GetMapping(path = "/get/{name}")
    public String getName(@PathVariable("name") String name){

        Long.parseLong(name);

        return "AchillesWild";
    }

    @GetMapping(path = "/event")
    public String invokeEvent(){

        Account account = new Account();
        account.setId(23L);
        applicationContext.publishEvent(new MyApplicationEvent(account));
        return "invokeEvent ok";
    }
}
