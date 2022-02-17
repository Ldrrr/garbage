package com.garbage.vo;

import com.garbage.pojo.Gcount;
import com.garbage.pojo.User;
import com.google.common.eventbus.AllowConcurrentEvents;


public class UserDetail {
    User user;
    Gcount gcountl;

    public UserDetail(User user, Gcount gcountl) {
        this.user = user;
        this.gcountl = gcountl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Gcount getGcountl() {
        return gcountl;
    }

    public void setGcountl(Gcount gcountl) {
        this.gcountl = gcountl;
    }
}
