package testCases;

import java.io.IOException;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import apitest.actions.ValidatorOperation;
import apitest.baseAPI.APIConstructor;
import apitest.utilities.Helper;
import testAPIs.APIs;

public class BearerToken extends APIConstructor {
	
	Helper helper = new Helper();
	@BeforeSuite
	@Test(groups = "tokenGeneration")
	public void loginToken() throws IOException {
		 getLoginToken(APIs.login.toString(),helper.loadProperties("client_id"), helper.loadProperties("client_secret"));
		 assertStatusCode(200);
		 assertIt("access_token",null,ValidatorOperation.NOT_EMPTY);
		 assertIt("access_token",null,ValidatorOperation.NOT_NULL);
		 assertIt("token_type", "bearer", ValidatorOperation.EQUALS);	 
	}
}
