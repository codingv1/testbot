package com.axess.ccuser.controller;

import java.util.List;
import java.util.Objects;

import com.axess.ccuser.JwtTokenUtils;
import com.axess.ccuser.dto.CCUserDto;
import com.axess.ccuser.dto.TokenDto;
import com.axess.ccuser.exception.RecordExistException;
import com.axess.ccuser.exception.RecordNotCreatedException;
import com.axess.ccuser.exception.RecordNotUpdatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.axess.ccuser.config.restapi.ApiSuccessResponse;
import com.axess.ccuser.dto.LoginDto;
import com.axess.ccuser.exception.RecordNotFoundException;
import com.axess.ccuser.model.CCUser;
import com.axess.ccuser.service.CCUserService;

@RestController
@RequestMapping("/ccuser")
@CrossOrigin
@Slf4j
public class CCUserController {
	
	@Autowired
	private CCUserService ccUserService;
	
	@PostMapping("/login")
	public ResponseEntity<?> verifyLogin(@RequestBody LoginDto loginDto) throws RecordNotFoundException, RecordNotCreatedException, RecordExistException {
		
		ApiSuccessResponse response = new ApiSuccessResponse();

		CCUser loggedInUser = this.ccUserService.getLoginDetails(loginDto.getUserId(), loginDto.getPassword());
		//generate the token for user
		String token = JwtTokenUtils.createToken(loggedInUser, 0);
		loggedInUser.setToken(token);
		ccUserService.saveUser(loggedInUser);
		response.setMessage("Login Verified successfully. " );
		response.setHttpStatus(String.valueOf(HttpStatus.FOUND));
		response.setHttpStatusCode(200);
		response.setBody(loggedInUser);
		response.setError(false);
		response.setSuccess(true);


		return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK))
				.body(response);

	}

	/*
         logout method
	 */

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody TokenDto tokenDto) throws RecordNotCreatedException, RecordExistException, RecordNotUpdatedException {

		ApiSuccessResponse response = new ApiSuccessResponse();

		if(Objects.isNull(tokenDto) || StringUtils.isEmpty(tokenDto.getUserId())||StringUtils.isEmpty(tokenDto.getToken())){
			response.setMessage("UserId and  Token can not be null!" );
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("status", String.valueOf(HttpStatus.BAD_REQUEST))
					.body(response);
		}

		ccUserService.updateUser("",tokenDto.getUserId());
		response.setMessage("logout Verified successfully. " );
		response.setHttpStatus(String.valueOf(HttpStatus.FOUND));
		response.setHttpStatusCode(200);
		response.setError(false);
		response.setSuccess(true);

		return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK))
				.body(response);

	}

	@PostMapping("/verifyToken")
	public ResponseEntity<?> verifyToken(@RequestBody TokenDto tokenDto) throws RecordNotFoundException, RecordNotCreatedException, RecordExistException {

		ApiSuccessResponse response = new ApiSuccessResponse();

		CCUser tokenUser = JwtTokenUtils.checkToken(tokenDto.getToken());
		if(tokenUser==null){
			log.info("token not exist or expire,{}",tokenDto.getToken());
			response.setHttpStatus(String.valueOf(HttpStatus.FOUND));
			response.setHttpStatusCode(400);
			response.setError(true);
			response.setMessage("token not exist or expire");
			response.setSuccess(false);
			return ResponseEntity.status(response.getHttpStatusCode()).header("status", String.valueOf(HttpStatus.OK))
					.body(response);
		}
		CCUser loggedInUser = this.ccUserService.getUserbyIdandToken(tokenUser.getUserId(),tokenDto.getToken());
		//verifyToken
		if(loggedInUser!=null){
			log.info("token Verified successfully,{}",tokenDto.getToken());
			response.setMessage("Login Verified successfully. " );
			response.setHttpStatus(String.valueOf(HttpStatus.FOUND));
			response.setHttpStatusCode(200);
			response.setError(false);
			response.setSuccess(true);
		}else {
			log.info("token Verified fail,{}",tokenDto.getToken());
			response.setHttpStatus(String.valueOf(HttpStatus.FOUND));
			response.setHttpStatusCode(400);
			response.setError(true);
			response.setSuccess(false);
		}
		return ResponseEntity.status(response.getHttpStatusCode()).header("status", String.valueOf(HttpStatus.OK))
				.body(response);

	}
	
	@GetMapping("/")
	public ResponseEntity<?> getUsers() throws RecordNotFoundException {
		
		ApiSuccessResponse response = new ApiSuccessResponse();

		List<CCUser> users = this.ccUserService.getAllUsers();

		response.setMessage("No. Of users -  "+users.size() );
		response.setHttpStatus(String.valueOf(HttpStatus.FOUND));
		response.setHttpStatusCode(302);
		response.setBody(users);
		response.setError(false);
		response.setSuccess(true);

		return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK))
				.body(response);


	}

	@PostMapping("/getUserById")
	public ResponseEntity<?> getUserById(@RequestParam String userId) throws RecordNotFoundException {

		ApiSuccessResponse response = new ApiSuccessResponse();

		CCUser user = this.ccUserService.getUserById(Long.valueOf(userId));
		response.setHttpStatus(String.valueOf(HttpStatus.FOUND));
		response.setHttpStatusCode(302);
		response.setBody(user);
		response.setError(false);
		response.setSuccess(true);
		return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK))
				.body(response);


	}

	@PostMapping("/saveUser")
	public ResponseEntity<?> saveUser(@RequestBody CCUser ccUser) throws RecordNotCreatedException, RecordExistException {

		ApiSuccessResponse response = new ApiSuccessResponse();

		String res  = this.ccUserService.saveUser(ccUser);
		response.setHttpStatus(String.valueOf(HttpStatus.FOUND));
		response.setHttpStatusCode(302);
		response.setBody(res);
		response.setError(false);
		response.setSuccess(true);
		return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(HttpStatus.OK))
				.body(response);

	}

}
