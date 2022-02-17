package com.garbage.controller;

import com.garbage.common.ServerResponse;
import com.garbage.service.IG_CountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/count/")
public class GCountController {
    @Resource
    IG_CountService gCountService;

    @RequestMapping("/get_sort")
    @ResponseBody
    public ServerResponse getSort(String phone,String type,int n){
        return gCountService.addCounter(phone,type,n);
    }
}
