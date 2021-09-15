package com.userstorage.vo;

import lombok.Getter;

@Getter
public class SimpleCustomUser {
	
	private String userId;    
    private String emailAddr;
    private String firstName;
    private String lastName;
    private String userName;
    
    private String userDetailedInfo;
    
    public SimpleCustomUser(String userId, String emailAddr, String firstName, String lastName, String userDetailedInfo) {
        this.userId = userId;
        this.emailAddr = emailAddr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userId;
        this.userDetailedInfo = userDetailedInfo;        
    }
}