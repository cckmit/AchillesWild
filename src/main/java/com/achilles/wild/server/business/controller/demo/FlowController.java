package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.common.aop.limit.BlockHandler;
import com.achilles.wild.server.common.aop.limit.FallBackHandler;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/flow",produces = {"application/json;charset=UTF-8"})
public class FlowController {

    private final static Logger log = LoggerFactory.getLogger(FlowController.class);

    @GetMapping(path = "/fallback/{name}")
    @SentinelResource(value = "limit_test",
            fallbackClass = FallBackHandler.class,fallback = "fallback")
    public String fallback(@PathVariable("name") String name){

        log.info("==================name ============"+name);

        Long.parseLong(name);

        try {
            if ("2".equals(name)){
                Thread.sleep(101L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "AchillesWild";
    }

    @GetMapping(path = "/block/{name}")
    @SentinelResource(value = "limit_test",
            blockHandlerClass = BlockHandler.class,blockHandler = "block")
    public String block(@PathVariable("name") String name){

        log.info("==================name ============"+name);

        Long.parseLong(name);

        try {
            if ("2".equals(name)){
                Thread.sleep(101L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "AchillesWild";
    }
}
