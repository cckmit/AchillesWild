package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.common.aop.limit.BlockHandler;
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

    @GetMapping(path = "/{name}")
    @SentinelResource(value = "limit_test",
            blockHandlerClass = BlockHandler.class,blockHandler = "block")
    public String flow(@PathVariable("name") String name){

        // 资源名
        log.info("==================name ============"+name);

        Long.parseLong(name);

        try {
            Thread.sleep(101L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "AchillesWild";
    }
}
