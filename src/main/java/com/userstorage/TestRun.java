package com.userstorage;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.userstorage.dao.UserDAO;
import com.userstorage.dbcon.ConnectionFactory;
import com.userstorage.vo.SimpleCustomUser;

public class TestRun {

	private static final Logger logger = LoggerFactory.getLogger(TestRun.class);
	
	public static void main(String[] args) {
		
		logger.info("Test Run Started...");
		
		UserDAO userDAO = new UserDAO(ConnectionFactory.getSqlSessionFactory());
		
		List<SimpleCustomUser> users = new ArrayList<>();
		
		userDAO.selectAllUsers().stream().forEach(user -> {
    		String userId = user.get("USER_ID");
    		String emailAddr = user.get("EMAIL");
    		String firstName = user.get("FIRST_NAME");
    		String lastName = user.get("LAST_NAME");
    		
			users.add(new SimpleCustomUser(userId, emailAddr, firstName, lastName, user.toString()));	
			
			logger.info("User Name.. -> " + user.get("FIRST_NAME"));
		});		
		
		users.stream().forEach(user -> {
			logger.info("User E-mail.. -> " + user.getEmailAddr());
			
			logger.info("User info Map.. -> " + user.getUserDetailedInfo());
		});

	}

}
