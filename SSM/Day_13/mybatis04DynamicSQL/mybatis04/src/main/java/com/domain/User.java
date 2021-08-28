package com.domain;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String name;
    private String password;

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
