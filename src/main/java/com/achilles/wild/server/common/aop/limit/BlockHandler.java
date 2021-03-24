package com.achilles.wild.server.common.aop.limit;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockHandler {

    private final static Logger log = LoggerFactory.getLogger(BlockHandler.class);

    /**
     * 订单查询接口抛出限流或降级时的处理逻辑
     *
     * 注意: 方法参数、返回值要与原函数保持一致
     * @return
     */
    public static String block(String name, BlockException e) {
        e.printStackTrace();
        log.info("==================block ============"+name);
        return "block";
    }

    public static String degrade(String name, DegradeException e) {
        e.printStackTrace();
        log.info("==================Degrade ============"+name);
        return "degrade";
    }
}
