package com.garbage.service;

import com.garbage.common.ServerResponse;
import com.garbage.pojo.User;

public interface IG_CountService {
    int CreateCounter(User user);

    ServerResponse  addCounter(String phone,String[] type,int[] counts,int n);
    ServerResponse addCounter(String phone,String type,int n);
}
