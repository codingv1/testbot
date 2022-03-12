package com.axess.history.service;

import com.axess.history.dto.TokenDto;


public interface VerifyService {
	
	
	Boolean verifyToken(TokenDto tokenDto);

}
