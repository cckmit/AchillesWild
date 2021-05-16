package com.achilles.wild.server.common.aop.limit.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
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
    public static String exception(String name, BlockException e) {
        log.info("==================BlockException ============"+name);
        return "exception DEGRADE";
    }

    public static String qps(String name, BlockException e) {
        log.info("==================qps limit ============"+name);
        return "qps limit";
    }
}
