package com.axess.history.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    @Resource
    private RestTemplate restTemplate;

    private static HttpUtil httpUtil;

    @PostConstruct
    public void init() {
        httpUtil = this;
        httpUtil.restTemplate = this.restTemplate;
    }

    public ResponseEntity<String> httpRequest(String url, HttpMethod method, HttpEntity<?> entity,Class user) {
        try {
            //发起一个POST请求
            ResponseEntity<String> result = httpUtil.restTemplate.exchange(url, method, entity, user);
            return result;
        } catch (Exception e) {
            logger.error("请求失败： " + e.getMessage());
        }
        return null;
    }
}