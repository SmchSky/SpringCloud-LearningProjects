package com.smy.order.controller;

import com.smy.order.feign.StockFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor // 为final修饰的字段生成构造方法，由于StockFeignService为Bean，所以这种方式等价于@Autowired注解的构造器注入，只是更简洁
public class OrderController {
    
    private final StockFeignService stockFeignService;
    
    @RequestMapping("/create")
    public String create() {
        String reduceMsg = stockFeignService.reduce(20);
        return "Create order success and " + reduceMsg;
    }
}
