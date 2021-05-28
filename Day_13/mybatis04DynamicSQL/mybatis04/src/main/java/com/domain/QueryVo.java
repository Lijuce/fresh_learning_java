package com.domain;

import java.io.Serializable;
import java.util.List;

/**
 * pojo类
 */
public class QueryVo implements Serializable {
    private User user;

    private List<Integer> ids;

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
