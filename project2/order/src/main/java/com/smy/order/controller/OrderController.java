package com.smy.order.controller;

import com.smy.order.feign.StockFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor // 为final修饰的字段生成构造方法，由于StockFeignService为Bean，所以这种方式等价于@Autowired注解的构造器注入，只是更简洁
@RefreshScope // 加了这个注解之后，当Nacos配置文件发生变更时，使用@Value注解方式读取配置的配置变量会自动刷新，无需重启服务（还没试过，可以再试试）
public class OrderController {
    
    private final StockFeignService stockFeignService;
    
    // 可以动态刷新了
    @Value("${user.age}")
    private Integer age;
    
    @RequestMapping("/create")
    public String create() {
        String reduceMsg = stockFeignService.reduce(20);
        return "Create order success and " + reduceMsg;
    }
    
    @RequestMapping("/test")
    public String test() {
        // 返回动态刷新后的age
        return "User's age is:" + age;
    }
}
