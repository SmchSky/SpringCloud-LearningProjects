package com.smy.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.smy.pojo.User;
import com.smy.util.BlockExceptionUtil;
import com.smy.util.FallbackUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Slf4j
public class UserController {
    /**
     * SentinelResource注解作用于方法上时，表示该方法将成为一个Sentinel资源，Sentinel将对其进行保护！
     * 使用前提：
     * 1、增加 sentinel-annotation-aspectj 依赖
     * 2、配置Bean——SentinelResourceAspect
     * 使用方法：
     * 1、value：资源的唯一标识符，它可以是任意字符串，但通常为了方便管理，建议使用有意义的命名，比如和被保护的方法名相同的名称。
     * 其作用是供 Sentinel 进行限流、熔断、热点参数限流等保护机制，是实现这些机制的一种关键标识符，可以通过这个资源名称在 Sentinel 控制台中查看和配置相关
     * 的保护规则。
     * 设置为"getUser"就表示将"getUser"这个资源标识符注册到了Sentinel中！
     * 2、blockHandler：阻塞处理方法名，即用于指定"流控"或"熔断"的处理方法，即这两种情况出现时都会调用此方法！！！
     * 3、blockHandlerClass：如果阻塞处理方法和此资源不在一个类中定义，则需要指定阻塞处理方法所在的类
     * 4、fallback：异常处理方法名，用于指定接口出现异常时的处理方法，注意！这个方法只是在资源出现异常时才会被调用，当资源被熔断时将不会再执行此方法！它和降级、
     * 流控的触发机制是独立的，它只是处理业务逻辑异常的一种简便方式！
     * 5、fallbackClass：如果异常处理方法和此资源不在一个类中定义，则需要指定异常处理方法所在的类
     */
    @RequestMapping("/getUser/{id}")
    @SentinelResource(value = "getUser", blockHandler = "blockHandlerForGetUser", blockHandlerClass = BlockExceptionUtil.class, fallback = "fallbackForGetUser", fallbackClass = FallbackUtil.class)
    public User getUser(@PathVariable("id") String id) {
        // 模拟正常的业务逻辑
        if (id.equals("4")) {
            throw new RuntimeException("id为4的User不存在！");
        }
        return new User("smy-" + id);
    }
    
    /**
     * 为"getUser"资源配置"流控规则"
     * 目的：用于限制资源的并发访问量或请求速率，防止资源被过度访问导致系统过载。
     * 行为：当请求超过设定的阈值时，超出的请求会被"立即拒绝"（即进入流控处理逻辑）！
     * 持续时间：流控规则没有一个固定的 "熔断窗口时间"，它的限制是基于每秒或每个时间片的请求量。所以一般意义上，我们认为流控规则不会导致资源进入"熔断"状态！
     */
    @PostConstruct
    private void initFlowRules() {
        // 新建流控规则
        FlowRule rule = new FlowRule();
        // 设置为哪个资源进行控制，此处填资源的唯一标识符
        rule.setResource("getUser");
        // 设置流控规则为QPS（Queries Per Second）
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置QPS为每秒1次，1秒内超出1次的请求会被"立即拒绝"（即进入流控处理逻辑）！
        rule.setCount(1);
        // 加载配置好的规则
        FlowRuleManager.loadRules(Collections.singletonList(rule));
    }
    
    /**
     * 为"getUser"资源配置"降级规则"或"熔断规则"（其实"降级"和"熔断"是一回事！！！）
     * 目的：降级规则是为了应对异常情况（如异常数、异常比例、响应时间等），主动熔断资源，使该资源在一段时间内不可用，保护整个系统不被异常资源拖垮。
     * 行为：某资源进入"熔断"状态后，可以认为该资源进入不可用状态，在设定的时间窗口内，所有请求都会被直接拒绝（即进入流控处理逻辑），不论当前的请求量是多少。
     * 持续时间：资源的"熔断"状态会持续一段固定的时间（timeWindow），熔断结束后，系统会恢复对该资源的正常访问。所以可以认为：降级规则会导致资源进入"熔断"状态！
     * 补充：如果资源在结束"熔断"状态后，收到的第1个请求再次触发异常，那么该资源将会立即重新进入"熔断"状态，此时不遵循配置的"降级规则"！！！
     */
    @PostConstruct
    private void initDegradeRules() {
        // 新建降级规则
        DegradeRule rule = new DegradeRule();
        // 设置为哪个资源进行控制，此处填资源的唯一标识符
        rule.setResource("getUser");
        // 设置降级规则为"异常出现次数"
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 当一个统计周期内总异常数量超过（所以实际要触发3次异常资源才会进入熔断状态）2次时触发"熔断"
        rule.setCount(2);
        // 最小请求数量，只有当一个请求周期内的请求总量超过（所以实际要请求3次资源才会进入熔断状态）这个值时，才会触发"熔断"
        rule.setMinRequestAmount(2);
        // 设置统计时间窗口为60秒，即60秒作为一个统计周期
        rule.setStatIntervalMs(60 * 1000);
        // 设置单次"熔断"的时间为10秒，即在这10秒内到达的所有请求都会被"流控"处理！！！注意不是"降级"处理！！！
        rule.setTimeWindow(10);
        // 加载配置好的规则
        DegradeRuleManager.loadRules(Collections.singletonList(rule));
    }
}
