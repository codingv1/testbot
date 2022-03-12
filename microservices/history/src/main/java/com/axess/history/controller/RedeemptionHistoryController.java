package com.axess.history.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axess.history.config.restapi.ApiSuccessResponse;
import com.axess.history.dto.UserRedeemptionHistoryDto;
import com.axess.history.exception.RecordExistException;
import com.axess.history.exception.RecordNotCreatedException;
import com.axess.history.exception.RecordNotFoundException;
import com.axess.history.service.RedeemptionHistoryService;

@RestController
@CrossOrigin
@RequestMapping("/history")
public class RedeemptionHistoryController {

	
	@Autowired
	private RedeemptionHistoryService historyService;


	
	@PostMapping("/")
	public ResponseEntity<?> saveHistory(@RequestBody UserRedeemptionHistoryDto historyDto) throws RecordNotFoundException, RecordExistException, RecordNotCreatedException {
		
		ApiSuccessResponse response = new ApiSuccessResponse();

		response.setMessage("Successfully added to history. ");
		response.setHttpStatus(String.valueOf(HttpStatus.CREATED));
		response.setHttpStatusCode(201);
		response.setBody(historyService.saveHistory(historyDto));
		response.setError(false);
		response.setSuccess(true);

		return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK))
				.body(response);


	}

	
	
}
