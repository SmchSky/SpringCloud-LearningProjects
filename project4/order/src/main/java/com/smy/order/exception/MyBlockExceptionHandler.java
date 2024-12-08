package com.smy.order.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MyBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws IOException {
        log.info("触发自定义统一阻塞处理方法！规则信息为：{}", e.getRule());
        String result;
        if (e instanceof FlowException) {
            result = "接口限流了";
        } else if (e instanceof DegradeException) {
            result = "服务降级了";
        } else if (e instanceof ParamFlowException) {
            result = "热点参数限流了";
        } else if (e instanceof SystemBlockException) {
            result = "触发系统保护规则了";
        } else if (e instanceof AuthorityException) {
            result = "授权规则不通过";
        } else {
            result = "未知的异常";
        }
        // 返回 json 数据
        response.setStatus(500);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), result);
    }
}
