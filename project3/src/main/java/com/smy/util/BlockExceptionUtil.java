package com.smy.util;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.smy.pojo.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockExceptionUtil {
    /**
     * getUser资源的阻塞处理方法（用于资源处于"被流控"或"被熔断"时的处理）
     * 注意：
     * 1、方法一定要为public
     * 2、该方法的参数必须包含被保护接口方法的所有参数，且必须多一个BlockException类型的参数！（注意不是BlockedException！易错！）
     * 3、该方法的返回值类型必须为该接口方法的返回值类型（或者为其父类类型）
     * 4、如果本方法和被保护的接口不在一个类中定义，则此方法必须是 static的！
     * 遵循2、3步是为了保持接口的一致性！
     */
    public static User blockHandlerForGetUser(String id, BlockException e) {
        log.error("限流触发: {}", e.getMessage());
        return new User("smy-block-" + id);
    }
}
