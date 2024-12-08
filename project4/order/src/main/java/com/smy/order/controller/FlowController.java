package com.smy.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.smy.order.util.SentinelUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/flow")
public class FlowController {
    /**
     * 这个接口用于测试在 DashBoard 设置的"QPS"流控规则是否生效
     * 注意@SentinelResource注解中的资源名称不要设置为"/flow"！因为Sentinel会自动根据@RequestMapping注解的值生成一个资源名称叫做"/flow"，如
     * 果这里也叫"/flow"的话，当资源被流控或被熔断时，依旧会触发Sentinel的默认阻塞处理方法，而不会触发自定义的flowBlockHandler方法！（需注意！）
     * 综上：在结合Sentinel DashBoard进行规则配置后，只有当需要手动指定阻塞处理方法时才需要使用 @SentinelResource 了（记住这个使用场景），而且
     * 只要使用 @SentinelResource 注解后，即使没有设置 blockHandler 参数，Sentinel的默认阻塞处理方法也不会对该资源生效了，！！！（易忘）
     */
    @SentinelResource(value = "flow-QPS", blockHandler = "flowBlockHandler", blockHandlerClass = SentinelUtil.class)
    @RequestMapping("/QPS")
    public String flowQPS() {
        return "正常";
    }
    
    /**
     * 这个接口用于测试在控制台设置的"线程数"流控规则是否生效
     * 可以将睡眠时间设置的稍长一点，然后开两个浏览器（或一个浏览器一个Postman）访问，观察第二个浏览器的请求是否会被限流
     */
    @SentinelResource(value = "flow-thread-count", blockHandler = "flowBlockHandler", blockHandlerClass = SentinelUtil.class)
    @RequestMapping("/threadCount")
    public String flowThreadCount() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return "正常";
    }
}
