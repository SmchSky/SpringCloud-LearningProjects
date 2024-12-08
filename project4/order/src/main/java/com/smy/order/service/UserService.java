package com.smy.order.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.smy.order.util.SentinelUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    /**
     * 注意这里必须要配置 blockHandler 属性和 blockHandlerClass 属性，否则被流控时触发的是 Sentinel 默认的阻塞处理器（即前端显示 Whitelabel
     * Error Page）
     */
    @SentinelResource(value = "getUser", blockHandler = "flowBlockHandler", blockHandlerClass = SentinelUtil.class)
    public String getUser() {
        return "user";
    }
}
