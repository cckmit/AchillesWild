package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.business.service.account.BalanceService;
import com.achilles.wild.server.common.aop.limit.annotation.CommonQpsLimit;
import com.achilles.wild.server.common.aop.log.annotation.IgnoreParams;
import com.achilles.wild.server.common.config.ConfigComplex;
import com.achilles.wild.server.common.config.ConfigProperties;
import com.achilles.wild.server.common.config.ConfigProperties1;
import com.achilles.wild.server.common.config.ConfigProperties2;
import com.achilles.wild.server.common.listener.event.MyApplicationEvent;
import com.achilles.wild.server.entity.account.Account;
import com.achilles.wild.server.model.request.BaseRequest;
import com.achilles.wild.server.other.design.proxy.cglib.CglibInterceptor;
import com.achilles.wild.server.other.design.proxy.cglib.ServiceClient;
import com.achilles.wild.server.other.design.proxy.jdk.JavaProxyInvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_VALUE)
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
    BalanceService balanceService;

    @Autowired
    ApplicationContext applicationContext;

    @Value("#{${test.map}}")
    private Map<String,String> map;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    HttpServletResponse httpServletResponse;

    @GetMapping(path = "/check/heartbeat")
    @CommonQpsLimit(permitsPerSecond = 0.2,code = "0",message = "checkHeartBeat too much")
    public String checkHeartBeat(){
        return "Everything is fine !";
    }

    @PostMapping(path = "/check1")
    @IgnoreParams
    public BaseRequest check1(@RequestBody BaseRequest request,HttpServletResponse httpServletResponse){

        String header = httpServletRequest.getHeader("header131");
        httpServletResponse.setHeader("header2222","headerva3333");
        return request;
    }


    @PostMapping(path = "/check")
    @IgnoreParams
    public BaseRequest check(@RequestBody BaseRequest request,HttpServletResponse httpServletResponse){

        String header = httpServletRequest.getHeader("header131");
        httpServletResponse.setHeader("header2222","AchillesWild");
        return request;
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
