package com.axess.ccuser.service;

import java.util.List;

import com.axess.ccuser.exception.RecordExistException;
import com.axess.ccuser.exception.RecordNotCreatedException;
import com.axess.ccuser.exception.RecordNotDeletedException;
import com.axess.ccuser.exception.RecordNotFoundException;
import com.axess.ccuser.exception.RecordNotUpdatedException;
import com.axess.ccuser.model.CCUser;


public interface CCUserService {
	
	CCUser getLoginDetails(String userId, String password) throws RecordNotFoundException;
	String saveUser(CCUser user) throws RecordExistException, RecordNotCreatedException;
	List<CCUser> getAllUsers() throws RecordNotFoundException;
	CCUser getUserById(long id) throws RecordNotFoundException;
	String deleteUser(long id) throws RecordNotDeletedException;
	void updateUser(String token, String userId) throws RecordNotUpdatedException, RecordExistException, RecordNotCreatedException;
	CCUser getUserbyIdandToken(String userId, String token) throws RecordNotFoundException;
	String saveAllUsers(List<CCUser> users) throws RecordNotCreatedException;

}
