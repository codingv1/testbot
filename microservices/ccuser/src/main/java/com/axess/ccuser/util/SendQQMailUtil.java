package com.axess.ccuser.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/*
 * @author yao
 * @date   2022-03-15
 */
@Slf4j
@Component
public class SendQQMailUtil {

        @Autowired
        private JavaMailSender jms;

        public void send(String sender,String receiver,String title,String text){
            //Create email message
            SimpleMailMessage mainMessage = new SimpleMailMessage();
            //sent
            log.info("Sender ---- The sender needs to be the same as the one configured with spring.mail.username or an error will be reported");
            mainMessage.setFrom(sender);
            log.info("recipient ------------------");
            //recipient
            mainMessage.setTo(receiver);
            //title
            mainMessage.setSubject(title);
            //text
            mainMessage.setText(text);
            jms.send(mainMessage);
        }


}
