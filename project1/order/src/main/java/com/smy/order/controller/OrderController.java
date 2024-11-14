package com.smy.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {
    
    private final RestTemplate restTemplate;
    
    @Autowired
    OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @RequestMapping("/create")
    public String create() {
        String msg = restTemplate.getForObject("http://stock-service/stock/reduce", String.class);
        return "Create order success and " + msg;
    }
}
