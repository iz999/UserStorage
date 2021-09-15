package com.userstorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.userstorage.dao.UserDAO;
import com.userstorage.dbcon.ConnectionFactory;
import com.userstorage.vo.SimpleCustomUser;

public class SimpleCustomUserRepository {

	private List<SimpleCustomUser> users = new ArrayList<>();
    
    private UserDAO userDAO = new UserDAO(ConnectionFactory.getSqlSessionFactory());

    public SimpleCustomUserRepository() {
            	
    	userDAO.selectAllUsers().stream().forEach(user -> {
    		String userId = user.get("USER_ID");
    		String emailAddr = user.get("EMAIL");
    		String firstName = user.get("FIRST_NAME");
    		String lastName = user.get("LAST_NAME");
    		
			users.add(new SimpleCustomUser(userId, emailAddr, firstName, lastName, user.toString()));			
		});
    }

    public List<SimpleCustomUser> getAllUsers() {
    	
        return users;
    }

    public int getUsersCount() {
        return users.size();
    }

    public SimpleCustomUser findUserById(String userId) {    	
        return users.stream()
        		.filter(user -> user.getUserId().equals(userId))
        		.findFirst().orElse(null);
    }

    public SimpleCustomUser findUserByUserName(String userName) {
        return users.stream()
                .filter(user -> user.getUserName().equalsIgnoreCase(userName) || user.getEmailAddr().equalsIgnoreCase(userName))
                .findFirst().orElse(null);
    }
    
    public SimpleCustomUser findUserByEmailAddr(String userName) {
        return users.stream()
                .filter(user -> user.getUserName().equalsIgnoreCase(userName) || user.getEmailAddr().equalsIgnoreCase(userName))
                .findFirst().orElse(null);
    }

    public List<SimpleCustomUser> findUsers(String query) {    	
        return users.stream()
                .filter(user -> user.getUserName().contains(query) || user.getEmailAddr().contains(query))
                .collect(Collectors.toList());
    }

    public boolean validateCredentials(String userId, String pwd) {
    	Map<String, String> userPwd = new HashMap<>();
    	
    	userPwd.put("userId", userId);
    	userPwd.put("pwd", pwd);
    	
        return userDAO.validatePwd(userPwd);
    }
    
}