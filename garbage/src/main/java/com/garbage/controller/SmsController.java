package com.garbage.controller;

import com.garbage.pojo.Sms;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;

@RestController
@RequestMapping(value = "/sms/")
public class SmsController {

    @RequestMapping("sendCode")
    public void sms(Sms sms){
        int appid =1400515724;
        String appkey="a1fe65d3768ebcbb0bc746520b51a022";
        int templateId=955306;
        String smsSign="我的易分网";
        try {
            String[] params={sms.getCode()};
            SmsSingleSender ssender=new SmsSingleSender(appid,appkey);
            SmsSingleSenderResult result=ssender.sendWithParam("86",sms.getPhone(),templateId,params,smsSign,"","");
            System.err.println(result);
        }catch (HTTPException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (com.github.qcloudsms.httpclient.HTTPException e) {
            e.printStackTrace();
        }

    }

}
