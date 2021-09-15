package com.userstorage.dbcon;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory; 
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ConnectionFactory { 
	private static SqlSessionFactory sqlSessionFactory; 
	
	static { 
		try { 
			String resource = "config/mybatis-config.xml";			
			Reader reader = Resources.getResourceAsReader(resource); 
			if (sqlSessionFactory == null) { 
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);				
			}			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
		
	public static SqlSessionFactory getSqlSessionFactory() { 
		return sqlSessionFactory; 
	} 
}


