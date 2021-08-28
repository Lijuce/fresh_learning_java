package com.itcast.domain;

import java.io.Serializable;

/**
 *
 */
public class Account implements Serializable {  //1、存储对象在存储介质中，以便在下次使用的时候，可以很快捷的重建一个副本；2、便于数据传输，尤其是在远程调用的时候。
    private Integer id;
    private String name;
    private Float money;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}
