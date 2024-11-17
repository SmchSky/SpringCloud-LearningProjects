package com.smy.util;

import com.smy.pojo.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FallbackUtil {
    /**
     * getUser资源出现异常时的处理方法（和降级、流控的触发机制独立，只是处理异常的一种简便方式）
     * 注意：
     * 1、方法一定要为public
     * 2、该方法的参数必须包含被保护接口方法的所有参数，且可以选择多一个 Throwable类型的参数（注意这里是可选！）
     * 3、该方法的返回值类型必须为该接口方法的返回值类型（或者为其父类类型）
     * 4、如果本方法和被保护的接口不在一个类中定义，则此方法必须是 static的！
     * 遵循2、3步是为了保持接口的一致性！
     */
    public static User fallbackForGetUser(String id, Throwable e) {
        log.error("异常触发: {}", e.getMessage());
        return new User("smy-fallback-" + id);
    }
}
