package com.smy.order.util;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SentinelUtil {
    
    public static String flowBlockHandler(BlockException e){
        log.info("{}",e.getMessage());
        return "限流啦";
    }
}
