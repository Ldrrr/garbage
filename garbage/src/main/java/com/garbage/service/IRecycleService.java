package com.garbage.service;

import com.garbage.common.ServerResponse;
import com.garbage.pojo.Recycle;

public interface IRecycleService {
    ServerResponse addBin(Recycle bin);
    ServerResponse getBin(String location);
    ServerResponse uploadBin(Recycle bin);
    ServerResponse DelBin(Recycle bin);
    ServerResponse findBin(String jd,String wd);
}
