package com.axess.catalogue.service;

import com.axess.catalogue.dto.TokenDto;


public interface VerifyService {
	
	
	Boolean verifyToken(TokenDto tokenDto);

}
