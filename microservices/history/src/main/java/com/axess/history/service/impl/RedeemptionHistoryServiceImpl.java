package com.axess.history.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.axess.history.Publisher.Publisher;
import com.axess.history.config.HttpUtil;
import com.axess.history.dto.UserRedeemptionHistoryDto;
import com.axess.history.exception.RecordExistException;
import com.axess.history.exception.RecordNotCreatedException;
import com.axess.history.exception.RecordNotFoundException;
import com.axess.history.model.CCUser;
import com.axess.history.model.UserRedeemptionHistory;
import com.axess.history.repository.RedeemptionHistoryRepository;
import com.axess.history.service.RedeemptionHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RedeemptionHistoryServiceImpl implements RedeemptionHistoryService {

	@Autowired
	private RedeemptionHistoryRepository historyRepo;


	@Value("${userapi.url}")
	private String userApiUrl;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private HttpUtil httpUtil;

	@Autowired
	private Publisher publisher;




	@Override
	public String saveHistory(UserRedeemptionHistoryDto historyDto) throws RecordExistException, RecordNotCreatedException {
		String message ="Item saved in history";
		CCUser user = callUserServiceWithSQS(historyDto);
		historyDto.getItemsRedeemed().forEach(item ->{

			UserRedeemptionHistory historyData = new UserRedeemptionHistory();
			historyData.setCatalogue(item);
			historyData.setOrderdate(LocalDate.now());
			historyData.setQuantity(historyDto.getQuantity());
			historyData.setCcUser(user);

			historyRepo.save(historyData);

		});

		return message;
	}

	@Override
	public List<UserRedeemptionHistory> getAll() throws RecordNotFoundException {
		return historyRepo.findAll();
	}

	@Override
	public UserRedeemptionHistory getByUser(long id) throws RecordNotFoundException {
		return null;
	}

	public CCUser callUserServiceWithHttp(UserRedeemptionHistoryDto historyDto){
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();;
		paramMap.add("userId", String.valueOf(historyDto.getCcNumber()));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,headers);
		ResponseEntity<String> responseEntity = httpUtil.httpRequest(userApiUrl+"/getUserById", HttpMethod.POST,httpEntity,String.class);
		log.info("call CCuser service getUserById response{}",responseEntity.getBody());
		JSONObject userJson = JSONObject.parseObject(responseEntity.getBody());
		CCUser user = JSONObject.parseObject(userJson.get("body").toString(),CCUser.class);

		user.setAvailableRedeemPoints(user.getAvailableRedeemPoints() - historyDto.getTotalPointsRedeemed());
		user.setTotalRewardsGained(user.getTotalRewardsGained() + historyDto.getTotalAmountGained());
		Map<String,String> map = restTemplate.postForObject(userApiUrl+"/saveUser", user, Map.class);
		log.info("call CCuser service saveUser response{}",map.toString());
		return user;
	}

	public CCUser callUserServiceWithSQS(UserRedeemptionHistoryDto historyDto){
		log.info("start push the data to CCUSER with SQS");
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();;
		paramMap.add("userId", String.valueOf(historyDto.getCcNumber()));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,headers);
		ResponseEntity<String> responseEntity = httpUtil.httpRequest(userApiUrl+"/getUserById", HttpMethod.POST,httpEntity,String.class);
		log.info("call CCuser service getUserById response{}",responseEntity.getBody());
		JSONObject userJson = JSONObject.parseObject(responseEntity.getBody());
		CCUser user = JSONObject.parseObject(userJson.get("body").toString(),CCUser.class);

		user.setAvailableRedeemPoints(user.getAvailableRedeemPoints() - historyDto.getTotalPointsRedeemed());
		user.setTotalRewardsGained(user.getTotalRewardsGained() + historyDto.getTotalAmountGained());
		log.info("SQS user{}",user.toString());
		publisher.scheduleFixedRateTask(user);
		return user;
	}

}
