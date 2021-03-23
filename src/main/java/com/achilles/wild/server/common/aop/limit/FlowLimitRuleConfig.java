package com.achilles.wild.server.common.aop.limit;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class FlowLimitRuleConfig {

    String key = "limit_test";

//    @PostConstruct
    public void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource(key);
        // QPS控制在2以内
        rule1.setCount(1);
        // QPS限流
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    //@PostConstruct
    public void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(key);
        // 5s内调用接口出现异常次数超过3的时候, 进行熔断
        rule.setCount(3);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        rule.setTimeWindow(5);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

    //RuleConstant.DEGRADE_GRADE_RT
    //平均相应时间,当1s内持续进入5个请求,对应时刻平均时间(秒级)均超过阈值,那么接下来的timewindow 秒之内,对这个方法熔断
    //DEGRADE_GRADE_EXCEPTION_RATIO
    //当资源每秒请求超过5次,且每秒异常总数占通过量的比值超过阈值之后,资源在接下来的timewindow秒进入降级,异常比率[0.0,1.0]
    //DEGRADE_GRADE_EXCEPTION_COUNT
    //当资源近1分钟的异常数目超过阈值之后会进行熔断,因为是1分钟级别,若timewindow小于60s,则结束熔断妆后仍可能进入熔断

    @PostConstruct
    public void initDegradeRuleRt() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(key);
        // set threshold rt, 200 ms
        rule.setCount(200);
        // 设置降级规则RT, 平均响应时间
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        // 设置时间窗口
        rule.setTimeWindow(5);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

//    @PostConstruct
    public void initParamFlowRule() {

        // 定义热点限流的规则，对第一个参数设置 qps 限流模式，阈值为1
        ParamFlowRule rule = new ParamFlowRule(key)
                .setParamIdx(0)
                .setGrade(RuleConstant.FLOW_GRADE_QPS)
                .setCount(1);
        ParamFlowRuleManager.loadRules(Collections.singletonList(rule));
    }

}
