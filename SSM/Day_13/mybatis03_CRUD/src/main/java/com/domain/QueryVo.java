package com.domain;

import java.io.Serializable;

/**
 * pojo类
 */
public class QueryVo implements Serializable {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
