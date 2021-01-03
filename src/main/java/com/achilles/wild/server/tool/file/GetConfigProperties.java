package com.achilles.wild.server.tool.file;

import java.io.InputStream;
import java.util.Properties;

public class GetConfigProperties {
	
	public static void main(String[] args) throws Exception {
		new GetConfigProperties();
	}
	public static String afterGetServicesKillTimerDuration;
	public static String URL_QueryAllWebservices;
	public static String LOCAL_IP;
//	public static String DB_NAME;
	
	static {
	    InputStream in = GetConfigProperties.class.getClassLoader().getResourceAsStream("config_datas/deployConfig.properties");
	    Properties properties = new Properties();
	    try {
			properties.load(in);
			afterGetServicesKillTimerDuration = properties.getProperty("afterGetServicesKillTimerDuration");
			URL_QueryAllWebservices =  properties.getProperty("URL_QueryAllWebservices");
			LOCAL_IP =  properties.getProperty("LOCAL_IP");
//			DB_NAME =  properties.getProperty("DB_NAME");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	
	
	

	
	

}
