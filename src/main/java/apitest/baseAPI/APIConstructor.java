package apitest.baseAPI;
import apitest.actions.HttpOperation;
import apitest.restassuredFuntions.API;
import apitest.utilities.Helper;



public class APIConstructor extends API{
    public static String token;
    private void createToken(String url, String clientId, String passWord) {
    	Helper helper = new Helper();
		baseUrl("baseUrl");
		url(url , HttpOperation.POST);
		setContentType("application/x-www-form-urlencoded");
		setQueryParam("tenantId",helper.loadProperties("tenantId"));
		setFormParam("grant_type", "client_credentials");
		setFormParam("client_id", clientId);
		setFormParam("client_secret", passWord);
	}
    
    public void constructAPICall(String url, HttpOperation operation, Object payload) {
    	baseUrl("baseUrl");
    	url(url , operation);
    	setHeader("Authorization","Bearer "+token);
    	setContentType("application/json");
    	setBody(payload);
    	callIt();
    }    
    
    
    public void constructAPICallWithoutPayload(String url, HttpOperation operation) {
    	baseUrl("baseUrl");
    	url(url , operation);
    	setHeader("Authorization","Bearer "+token);
    	setContentType("application/json");
    	callIt();
    }    
    
    
	public String getLoginToken(String url, String clientId, String passWord) {
		createToken(url, clientId, passWord);
		String response = callIt();
		token=extractString("access_token");
		return response;
	}	
}
