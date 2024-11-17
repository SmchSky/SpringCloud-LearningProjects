package com.smy.pojo;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    private String id;
    private String name;
    
    public User(String name) {
        // 生成一个长度为8的随机字符串
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.name = name;
    }
}
