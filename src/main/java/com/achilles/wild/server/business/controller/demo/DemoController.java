package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.business.service.account.BalanceService;
import com.achilles.wild.server.common.config.ConfigComplex;
import com.achilles.wild.server.common.config.ConfigProperties;
import com.achilles.wild.server.common.config.ConfigProperties1;
import com.achilles.wild.server.common.config.ConfigProperties2;
import com.achilles.wild.server.common.listener.event.MyApplicationEvent;
import com.achilles.wild.server.entity.account.Account;
import com.achilles.wild.server.other.design.proxy.cglib.CglibInterceptor;
import com.achilles.wild.server.other.design.proxy.cglib.ServiceClient;
import com.achilles.wild.server.other.design.proxy.jdk.JavaProxyInvocationHandler;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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
    public String flow(@PathVariable("name") String name){

        Entry entry = null;
        // 资源名
        String resourceName = "limit_test";
        try {
            // entry可以理解成入口登记
            entry = SphU.entry(resourceName);
            // 被保护的逻辑, 这里为订单查询接口
            log.info("==================name ============"+name);
        } catch (BlockException blockException) {
            // 接口被限流的时候, 会进入到这里
            log.warn("---flow 接口被限流了---, exception: ", blockException);
            return "接口限流, 返回空";
        } finally {
            // SphU.entry(xxx) 需要与 entry.exit() 成对出现,否则会导致调用链记录异常
            if (entry != null) {
                entry.exit();
            }
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
