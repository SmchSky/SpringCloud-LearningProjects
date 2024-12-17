package com.smy.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * 下面的例子展示的是使用代码来进行Sentinel流量配置（而不是使用DashBoard和注解方式），可以一定程度上提供更灵活的配置，但不太推荐使用此方式
 * 这种方式的缺点如下：
 * 1、业务侵入性很强，需要在controller中写入非业务代码
 * 2、配置麻烦，若需要添加新的受保护资源，则需要手动添加@PostConstruct注解的方法来添加流控规则
 * 所以引入了第二种方式进行配置————使用@SentinelResource注解！详见UserController.java
 */
@RestController
@Slf4j
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        Entry entry = null;
        try {
            // 此语句表示 SphU.entry() 到 entry.exit() 之间的代码块是一个需要进行流量控制的资源，并为此资源设置一个"资源唯一标识符"叫“hello”
            // 此语句就表示将"hello"这个资源标识符注册到了Sentinel中！
            entry = SphU.entry("hello");
            // 被保护的业务逻辑
            String str = "Hello,world!";
            log.info("========={}========= ", str);
            return str;
        } catch (BlockException e) {
            // 资源被限流时的处理操作（即限流逻辑）
            // 对应 @SentinelResource 注解中的 blockHandler 属性！！！
            log.info("Blocked!");
            return "Blocked!";
        } catch (Exception ex) {
            // 资源内部出现异常时的处理操作（即降级逻辑）
            // 对应 @SentinelResource 注解中的 fallback 属性！！！
            Tracer.traceEntry(ex, entry);
            return "Fallback！";
        } finally {
            // 将资源关闭，注意务必保证每个 entry 与 exit 配对！
            if (entry != null) {
                entry.exit();
            }
        }
    }
    
    /**
     * Bean创建前初始化限流规则
     */
    @PostConstruct
    private void initFlowRules() {
        // 新建流控规则
        FlowRule rule = new FlowRule();
        // 设置为哪个资源进行控制，此处填资源的唯一标识符
        rule.setResource("hello");
        // 设置流控规则为QPS（Queries Per Second）
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置QPS为每秒1次
        rule.setCount(1);
        // 加载配置好的规则
        FlowRuleManager.loadRules(Collections.singletonList(rule));
    }
    
}
