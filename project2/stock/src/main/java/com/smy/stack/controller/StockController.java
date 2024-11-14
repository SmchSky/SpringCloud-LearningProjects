package com.smy.stack.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {
    
    @Value("${server.port}")
    String port;
    
    /**
     * 减少库存（处理类似于 /stock/reduce/5 的请求）
     *
     * @param amount 数量
     * @return 提示信息
     */
    @RequestMapping("/reduce/{amount}")
    public String reduce(@PathVariable("amount") Integer amount) {
        System.out.println("Stock reduce " + amount + " kg!");
        return "Stock reduce " + amount + " kg on port " + port + "!";
    }
    
    /**
     * 添加库存（处理类似于 /stock/add?amount=5 的请求）
     *
     * @param amount 数量
     * @return 提示信息
     */
    @RequestMapping("/add")
    public String add(@RequestParam(name = "amount", required = false, defaultValue = "1") Integer amount) {
        System.out.println("Stock add " + amount + " kg!");
        return "Stock add " + amount + " kg on port " + port + "!";
    }
}
