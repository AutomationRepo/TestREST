package com.emp.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadDB {
	
	/**
	 * Read values form properties file
	 * @param filePath
	 * @param Key
	 * @return
	 */
	public static String getValue(String filePath, String Key)
	{
		Properties prop = new Properties();						
		InputStream ip = ReadDB.class.getClassLoader().getResourceAsStream(filePath);
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop.getProperty(Key);		
	}
	
}
