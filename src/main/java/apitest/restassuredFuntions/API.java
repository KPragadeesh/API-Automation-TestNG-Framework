package apitest.restassuredFuntions;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertEquals;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.empty;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import org.hamcrest.core.IsNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import apitest.actions.HttpOperation;
import apitest.actions.ValidatorOperation;
import apitest.interfaces.IApi;
import apitest.utilities.Helper;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.anyOf;


public class API implements IApi{
	public static PrintStream log ;
	public static RequestSpecification reqSpec;
	HttpOperation method;
	String url;
	public static Response resp;
	
	public void url(String url, HttpOperation method) {
		this.url = url;
		this.method = method;
		reqSpec = given();
		try {
			if(log==null) {
			 log = new PrintStream("Logs.txt");
			}
			else {
				API.log = log;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		reqSpec.filter(RequestLoggingFilter.logRequestTo(log)).filter(ResponseLoggingFilter.logResponseTo(log));
	}
	

	public void baseUrl(String baseConst) {
		Helper getHelp = new Helper();
		getHelp.set_path("src/main/resources/Constants.properties");
		try {
			RestAssured.baseURI = getHelp.loadProperties(baseConst);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setHeader(String[][] head) {
		for (int row = 0; row < head.length; row++)
			for (int col = 0; col < head[row].length; col++)
				reqSpec.header(head[row][col], head[row][col + 1]);
	}

	public void setHeader(String head, String val) { 
		reqSpec.header(head, val);
		reqSpec.config(RestAssured.config().logConfig(LogConfig.logConfig().blacklistHeader("set-cookie")));
	}

	public void setBody(Object payload) { 
		reqSpec.body(payload); 
	}
	
	
	public void setContentType(String key) {reqSpec.contentType(key);}

	public void setFormParam(String key, String val) {reqSpec.formParam(key, val);}

	public void setQueryParam(String key, String val) {reqSpec.queryParam(key, val);}

	public String callIt() {
		if (method.toString().equalsIgnoreCase("get")) {
			resp = reqSpec.get(url);
			return resp.asString();
		} else if (method.toString().equalsIgnoreCase("post")) {
			resp = reqSpec.post(url);
			return resp.asString();
		} else if (method.toString().equalsIgnoreCase("patch")) {
			resp = reqSpec.patch(url);
			return resp.asString();
		} else if (method.toString().equalsIgnoreCase("put")) {
			resp = reqSpec.put(url);
			return resp.asString();
		} else if (method.toString().equalsIgnoreCase("delete")) {
			resp = reqSpec.delete(url);
			return resp.asString();
		}
		return "invalid method set for API";
	}


	public String extractString(String path) { return resp.jsonPath().getString(path);}
	
	public int extractInt(String path) { return resp.jsonPath().getInt(path);}

	public List<String> extractList(String path) { return resp.jsonPath().getList(path);}

	public Object extractIt(String path) { return resp.jsonPath().get(path); }

	public String extractHeader(String header_name) { return resp.header(header_name);}

	public String getResponseString() { return resp.asString();}

	public void fileUpload(String key, String path, String mime) {
		reqSpec.multiPart(key, new File(path), mime);
	}

	public void multiPartString(String key, String input) { reqSpec.multiPart(key,input);}

	public void printResp() { resp.print();}

	public String getCookieValue(String cookieName) { return resp.getCookie(cookieName); }

	public void ListResponseHeaders()
	{ 
		// Get all the headers. Return value is of type Headers.
		Headers allHeaders = resp.headers();
		// Iterate over all the Headers
		for(Header header : allHeaders)
		{
			System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
		}
	}

	public int getStatusCode() { return resp.getStatusCode(); }

	public Headers getAllHeaders() {return resp.getHeaders();}

	public API assertStatusCode(int code, int optionalCode) {
		resp.then().statusCode(anyOf(equalTo(code),equalTo(optionalCode)));
		return this;
	}

	
	public API assertIt(String key, Object val, ValidatorOperation operation) {
		switch (operation.toString()) {
		case "EQUALS":
			resp.then().body(key, equalTo(val));
			break;
			
		case "HAS_ALL":
			break;

		case "NOT_EQUALS":
			resp.then().body(key, not(equalTo(val)));
			break;

		case "EMPTY":
			resp.then().body(key, empty());
			break;

		case "NOT_EMPTY":
			resp.then().body(key, not(emptyArray()));
			break;

		case "NOT_NULL":
			resp.then().body(key, notNullValue());
			break;
			
		case "NULL":
			resp.then().body(key, IsNull.nullValue());
			break;

		case "HAS_STRING":
			resp.then().body(key, containsString((String)val));
			break;

		case "SIZE":
			resp.then().body(key, hasSize((int)val));
			break;
		}
		return this;
	}

	public API assertStatusCode(int code) {
		resp.then().statusCode(code);
		return this;
	}
	
	public API assertJsonObject(String key, ValidatorOperation operation) throws JSONException {
		JSONObject obj = new JSONObject(resp.asString());
		switch(operation.toString()) {
		case "KEY_PRESENTS": 
			if(obj.has(key)) {break;}
		}
		return this;
	}
	
	
	public static void assertJsonArrayObject(String key, ValidatorOperation operation) throws JSONException {		
		JSONArray jsonArray = new JSONArray(resp.asString());
		for(int i =0;i<jsonArray.length();i++) {
			JSONObject responseKey = jsonArray.getJSONObject(i);
			switch (operation.toString()) {
			case "KEY_PRESENTS":
				if(responseKey.has(key)) {break;}
			}
		}
	}

	
	public static void assertJsonArrayObject(String key, String value) throws JSONException {		
		JSONArray jsonArray = new JSONArray(resp.asString());
		for(int i =0;i<jsonArray.length();i++) {
			String responseKey = jsonArray.getJSONObject(i).getString(key);			
			if(responseKey.equals(value)) {
				assertString(responseKey,value);
				break;
			}
		}
	}
	
	
	public static void assertJsonArrayObject(String key, List<String> value) throws JSONException {		
		JSONArray jsonArray = new JSONArray(resp.asString());
		for(int i =0;i<jsonArray.length();i++) {
			JSONArray responseKey = jsonArray.getJSONObject(i).getJSONArray(key);
			if(responseKey.toString().equals(value.toString())) {
				assertString(responseKey.toString(), value.toString());
				break;
			}
		}
	}
	
	
	public void assertJsonPathStringFromJsonArray(String path, String value) throws JSONException { 
		JSONArray jsonArray = new JSONArray(resp.asString());
		for(int i =0;i<jsonArray.length();i++) {
			String jsonResponse = jsonArray.getJSONObject(i).toString();
			JsonPath jsonPath = new JsonPath(jsonResponse);
			String key = jsonPath.getString(path);
			if(key.equalsIgnoreCase(value)) {
				assertString(key, value);
				break;
			}
		}
	}
		
	
	public static void assertString(String input, String output) {
		assertEquals(input,output);
	}
	
	
	public static void assertObjects(Object input, Object output) {
		assertEquals(input, output);
	}	

}
