package com.domain;

import java.io.Serializable;

public class User implements Serializable {
    private Integer uid;
    private String name;

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", name=" + name +
                '}';
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
