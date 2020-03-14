package com.times.server.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String userName;
    private String name;
    public User(){

    }
    public User(Integer id,String userName,String name){
        this.id = id;
        this.userName = userName;
        this.name = name;
    }
}
