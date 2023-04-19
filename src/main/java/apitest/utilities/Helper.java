package apitest.utilities;


import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import static org.testng.Assert.assertEquals;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@SuppressWarnings("deprecation")
public class Helper {
	String  path;

	public String loadProperties(String property) {
		Properties prop = new Properties();
		InputStream input;
		set_path("src/main/resources/Constants.properties");
		try {
			input = new FileInputStream(path);
			
			// load a properties file
			
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return prop.getProperty(property);
	}

	public void set_path(String path2) {
		path = path2;
		
	}

	public static List<String[]> ReadCSV(String file) throws Exception {
		FileReader filereader = new FileReader(file);
		CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
		List<String[]> allData = csvReader.readAll();

		return allData;
	}
	
	//https://jsonassert.skyscreamer.org/cookbook.html

	public static void assertAll(String input, String output) {
		try {
			JSONAssert.assertEquals(input, output, false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void assertAll(String input, String output, String strict) {
		try {
			JSONAssert.assertEquals(input, output, true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	

	
	public static void assertString(String actual, String expected, String error_message)
	{
		assertEquals(actual,expected,error_message);
	}
	
	public static void assertInt(int actual, int expected, String error_message)
	{
		assertEquals(actual,expected,error_message);
	}
		
	public static String getValue(String input, String array, int pos, String key) throws JSONException {		
			JSONObject inputJson = new JSONObject(input);
			JSONArray jsonArray = inputJson.getJSONArray(array);
			JSONObject job = jsonArray.getJSONObject(pos);
			String Key = job.get(key).toString();
			return Key;
	}
	
	
	public static String getValue(String input, String key) throws JSONException {

		JSONObject inputJson = new JSONObject(input);
		String value = inputJson.get(key).toString();
		return value;		
	}
	
	public static String getRandomValue() {
		return RandomStringUtils.randomAlphanumeric(8).toUpperCase();
	}

	public static long getRandomNumber() {
		return ThreadLocalRandom.current().nextLong(9999999999l);
	}
	

}