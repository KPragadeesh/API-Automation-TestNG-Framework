package testAPIs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public enum APIs{
	

	login("Your API"),
	createUser("your API"),
	fetchUser("your API");
	

	
	
	public String Url;

	APIs(String api) {
		this.Url = api;
	}
	
	
	public static String getGlobalValue(String key) {
		Properties prop = new Properties();
		FileInputStream file = null;
		try {
			file = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\Constants.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(file);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
	
	
	public String toString() {
        return Url;
    }
	

	

}
