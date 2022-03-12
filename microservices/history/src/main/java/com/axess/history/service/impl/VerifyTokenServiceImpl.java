package com.axess.history.service.impl;

import com.axess.history.dto.TokenDto;
import com.axess.history.service.VerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 *  VerifyTokenServiceImpl
 *  @author  Yao
 *
 */
@Service
@Slf4j
public class VerifyTokenServiceImpl implements VerifyService {


	@Value("${userapi.url}")
	private String userApiUrl;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public Boolean verifyToken(TokenDto tokenDto) {
		Map<String,Object> resMap = new HashMap<>();
		boolean flag =true;
		try {
			resMap = restTemplate.postForObject(userApiUrl+"/verifyToken", tokenDto, Map.class);
			log.info("call CCuser service saveUser response{}",resMap.toString());

		}catch (HttpClientErrorException e){
			// resMap
			flag = false;
			log.error("HttpClientErrorException{}",e);
		}

		return flag;
	}
}
