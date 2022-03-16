package com.axess.ccuser;

import com.axess.ccuser.model.CCUser;
import com.axess.ccuser.service.CCUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CCuserApplication implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CCuserApplication.class);

	@Autowired
	private CCUserService userService;
	@Autowired

	public static void main(String[] args) {
		SpringApplication.run(CCuserApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		
		LOGGER.info(this.userService.saveAllUsers(this.loadUsersData()));

	}
	

	private List<CCUser> loadUsersData() {
		List<CCUser> users = new ArrayList<CCUser>();

		CCUser user = new CCUser();
		user.setUserId("axess1");
		user.setPassword("abc@123");
		user.setUserName("Peter Hanks");
		user.setCcName("Smart Bank Credit Card");
		user.setCcNumber(123456789);
		user.setTotalRewardsGained(0);
		user.setAvailableRedeemPoints(10000);
		user.setToken("");
		user.setMailAdress("tmqhu2@gmail.com");
		users.add(user);
		
		user = new CCUser();
		user.setUserId("axess2");
		user.setPassword("abc@123");
		user.setUserName("Harry Simson");
		user.setCcName("Smart Bank Credit Card");
		user.setCcNumber(123456799);
		user.setTotalRewardsGained(0);
		user.setAvailableRedeemPoints(10000);
		user.setToken("");
		user.setMailAdress("tmqhu2@gmail.com");

		users.add(user);
		
		user = new CCUser();
		user.setUserId("axess3");
		user.setPassword("abc@123");
		user.setUserName("Tom Willis");
		user.setCcName("Smart Bank Credit Card");
		user.setCcNumber(123456889);
		user.setTotalRewardsGained(0);
		user.setAvailableRedeemPoints(10000);
		user.setMailAdress("jack136317@gmail.com");
		user.setToken("");
		users.add(user);

		return users;
	}

}
