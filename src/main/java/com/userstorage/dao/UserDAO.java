package com.userstorage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession; 
import org.apache.ibatis.session.SqlSessionFactory;

public class UserDAO {	
	
	private SqlSessionFactory sqlSessionFactory = null; 
	
	public UserDAO(SqlSessionFactory sqlSessionFactory) {
		
		this.sqlSessionFactory = sqlSessionFactory; 
	}
	
	public List<Map<String, String>> selectAllUsers() {
		List<Map<String, String>> users = null;
		SqlSession session = sqlSessionFactory.openSession();
		
		try {			 
			users = session.selectList("selectAllUsers");
		} 
		finally { 
			session.close(); 
		}
		
		return users;
	}
	
	public boolean validatePwd(@Param("userPwd") Map<String, String> userPwd) {		
		String validRst = "0";
		
		SqlSession session = sqlSessionFactory.openSession(); 
		
		try {			 
			validRst = session.selectOne("validatePwd", userPwd);
		} 
		finally { 
			session.close(); 
		} 		
		
		if (Integer.parseInt(validRst) == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	
}
