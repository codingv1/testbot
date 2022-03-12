package com.axess.history.service;

import java.util.List;

import com.axess.history.dto.UserRedeemptionHistoryDto;
import com.axess.history.exception.RecordExistException;
import com.axess.history.exception.RecordNotCreatedException;
import com.axess.history.exception.RecordNotFoundException;
import com.axess.history.model.UserRedeemptionHistory;


public interface RedeemptionHistoryService {
	
	
	String saveHistory(UserRedeemptionHistoryDto history) throws RecordExistException, RecordNotCreatedException;
	List<UserRedeemptionHistory> getAll() throws RecordNotFoundException;
	UserRedeemptionHistory getByUser(long id) throws RecordNotFoundException;
	
	

}
