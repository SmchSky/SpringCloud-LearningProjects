package com.smy.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这个控制器用于测试配置"流控规则"时的指定的高级选项——"流控模式"为"关联"时的情况
 * 场景描述：在订单系统中，包括新增订单和查询订单2个接口，这2个接口都会请求数据库，所以当新增订单的接口请求量过大时，应该限制查询订单的接口请求量，即所谓
 * "关联模式"（因为肯定要先保证下单接口的可用性，不然会造成亏损的！）
 * 实现步骤：
 *    1、在 Sentinel DashBoard 上对"/order/query"资源配置流控规则，因为最终被限制的是它，然后设置QPS为2，并选择"关联"模式，配置关联资源为：
 *    "/order/create"，表示对该资源应用上述流控规则，但触发规则后的承受者为"/order/query"
 *    2、在 Postman 中打开"Runner"界面，对"/order/create"接口实施1000次的连续访问，每次访问的间隔为300ms（每秒4次多），这样就可以触发流控规则了
 *    3、访问"/order/query"接口，观察结果（由于上述设置的请求频率比较适中，此时"正常"和"被限流"的情况都会出现，可以自己多试几次）
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    
    @RequestMapping("/create")
    public String create() {
        return "success";
    }
    
    @RequestMapping("/query")
    public String query() {
        return "success";
    }
    
}
