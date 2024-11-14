package com.smy.order.feign;

import com.smy.order.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FeignClient注解用法：
 * 首先，FeignClient会对请求采用客户端负载均衡策略（当前项目使用的是 Spring Cloud LoadBalancer）
 * 1、name属性填写服务提供者的服务名
 * 2、path属性填写服务提供者的请求路径前缀（即服务提供者的接口所在的Controller类上的RequestMapping("/stock")中的内容）
 * 3、configuration属性填写Feign配置类，用于定制化Feign客户端，例如：超时时间、日志级别、请求头、请求参数等
 */
@FeignClient(name = "stock-service", path = "/stock", configuration = FeignConfig.class)
public interface StockFeignService {
    
    @RequestMapping("/reduce/{amount}")
    String reduce(@PathVariable("amount") Integer amount);
    
    @RequestMapping("/add")
    String add(@RequestParam(name = "amount", required = false, defaultValue = "1") Integer amount);
    
}

/* 下面的代码用于比较
    @RequestMapping("/reduce/{amount}")
    public String reduce(@PathVariable Integer amount) {
        System.out.println("Stock reduce " + amount + " kg!");
        return "Stock reduce on port " + port + "!";
    }
    @RequestMapping("/add")
    public String add(@RequestParam(name = "amount", required = false, defaultValue = "1") Integer amount) {
        System.out.println("Stock add " + amount + " kg!");
        return "Stock add on port " + port + "!";
    }
*/