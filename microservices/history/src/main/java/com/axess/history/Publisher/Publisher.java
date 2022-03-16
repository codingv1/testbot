package com.axess.history.Publisher;

import com.axess.history.model.CCUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    public void scheduleFixedRateTask(CCUser ccUser) {

        try {
            queueMessagingTemplate.convertAndSend(endpoint, ccUser);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
