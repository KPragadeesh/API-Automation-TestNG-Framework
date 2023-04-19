package testCases;
import java.io.IOException;
import java.sql.SQLException;
import org.json.JSONException;
import org.testng.annotations.Test;

import apitest.actions.HttpOperation;
import apitest.actions.ValidatorOperation;
import apitest.baseAPI.APIConstructor;
import apitest.build.BuildAPIs;
import apitest.utilities.DbConnectionUtility;
import apitest.utilities.Helper;
import models.User;
import testAPIs.APIs;

public class UserTest extends APIConstructor {

	User user;
	DbConnectionUtility dbConnectionUtility = new DbConnectionUtility();
	Helper helper = new Helper() ;
	@Test
	public void createOauthUser() throws IOException, JSONException {
		user = BuildAPIs.user();
		
		constructAPICall(APIs.createUser.toString(), HttpOperation.POST,user);
		assertStatusCode(200);
		assertString(extractString("username"), user.getUsername());
		assertIt("tenantId", user.getTenantId(),ValidatorOperation.EQUALS);
		//add assertions as much as you can.
	}

	
	@Test
	public void fetchOauthUser() throws JSONException {
		constructAPICallWithoutPayload(APIs.fetchUser+extractString("id"), HttpOperation.GET);
		assertStatusCode(200);
		assertIt("username", user.getUsername() ,ValidatorOperation.EQUALS);
		assertIt("tenantId", user.getTenantId() ,ValidatorOperation.EQUALS);
		//add assertions as much as you can.
	}
	
	@Test(dependsOnMethods = {"createOauthUser","searchOauthUser","fetchOauthUser"})
	public void deleteOauthUser() throws ClassNotFoundException, SQLException {
		//sample delete db call
		dbConnectionUtility.user(helper.loadProperties("testEmailID"));
	}
	

	
	
	
	
	
}
