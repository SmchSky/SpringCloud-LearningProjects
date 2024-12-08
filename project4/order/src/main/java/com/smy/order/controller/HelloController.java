package com.smy.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    /**
     *  这个接口用于测试当对"/hello"资源增加规则后，MyBlockExceptionHandler类是否可以修改Sentinel默认的阻塞处理方法
     */
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
