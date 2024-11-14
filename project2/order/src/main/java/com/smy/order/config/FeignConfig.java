package com.smy.order.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

// 注意！如果此处配置@Configuration注解，则本配置类就会对项目中所有的FeignClient接口生效，如果只想对特定的接口生效，就不能在此加@Configuration注解！
// @Configuration
public class FeignConfig {
    @Bean
    public Logger.Level feignLoggerLevel() {
        // 默认日志级别为NONE，即不输出日志
        // NONE、BASIC、HEADERS、FULL日志级别越来越详细
        // 注意，此处虽然配置了日志级别为FULL，但是Spring默认的日志级别为INFO，无法输出属于DEBUG级别的FULL级别日志，故需要在yml文件中更改日志级别！
        return Logger.Level.FULL;
    }
}
