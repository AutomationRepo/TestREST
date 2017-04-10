package service.test;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Helper methods for web tester object
 * @author Shweta
 *
 */
public class Utility {
	
	/**
	 * Get value of given key
	 * @param configFilePath
	 * @param key
	 * @return
	 */
	public static String getPropertyValue(String configFilePath, String key) 
	{
		Properties prop = new Properties();
		try {
			//InputStream ipStream = Utilities.class.getClassLoader().getResourceAsStream(configFilePath);	
			//prop.load(ipStream);
			prop.load(new FileInputStream(configFilePath));			
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return prop.getProperty(key);		
	}

}
