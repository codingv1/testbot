package com.axess.ccuser.consumer;

import com.alibaba.fastjson.JSONObject;
import com.axess.ccuser.model.CCUser;
import com.axess.ccuser.service.CCUserService;
import com.axess.ccuser.util.SendQQMailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Slf4j
public class Consumer {

    @Value("${spring.mail.username}")
    private String sentmail;

    @Autowired
    private CCUserService ccUserService;

    @Autowired
    private SendQQMailUtil sendQQMailUtil;

    //@Transactional
    @SqsListener(value = "jupitersqs",deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processMessage(String str) {
        log.info("Message from SQS {}", str);
        try {
            CCUser ccUser = JSONObject.parseObject(str,CCUser.class);
            ccUserService.saveUser(ccUser);
            sendQQMailUtil.send(sentmail,ccUser.getMailAdress(),"Smartbank feedback",generate(ccUser));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String generate(CCUser ccUser) {
       String msg="Hi "+ccUser.getUserName()+",\n" +
               "\n" +
               "Congratulations. \n" +
               "You have successfully redeemed your points.\n" +
               "Please click the followling link to activate your voucher:\n" +
               "www.activate_vourcher.com\n" +
               "\n" +
               "You total available redeem points is "+ccUser.getAvailableRedeemPoints()+", \n" +
               "your total rewards gained is +"+ccUser.getTotalRewardsGained()+".\n" +
               "\n" +
               "Thanks,\n" +
               "\n" +
               "SmartBankCreditCardJupiterGroup";
       return msg;
    }
}