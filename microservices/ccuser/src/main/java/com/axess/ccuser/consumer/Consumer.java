package com.axess.ccuser.consumer;

import com.axess.ccuser.model.CCUser;
import com.axess.ccuser.service.CCUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Autowired
    private CCUserService ccUserService;

    @SqsListener(value = "yaosqs",deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processMessage(CCUser ccUser) {
        System.out.println("Message from SQS {}"+ccUser.toString());

        try {
            ccUserService.saveUser(ccUser);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}