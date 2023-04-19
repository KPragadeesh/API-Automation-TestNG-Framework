package apitest.build;
import java.io.IOException;

import apitest.utilities.Helper;
import io.restassured.specification.RequestSpecification;
import models.EmailInfo;
import models.User;

public class BuildAPIs extends Helper{

	static RequestSpecification req;       

	
	public static User user() throws IOException {
		Helper helper = new Helper();
		User user = new User();
		user.setUsername("testOauthUser"+getRandomValue());
		user.setTenantId(helper.loadProperties("tenantId"));
		user.setContextId(helper.loadProperties("testContext"));
		user.setEmail(getRandomValue()+helper.loadProperties("testEmailID"));
		user.setProfileId(getRandomValue());
		user.setPassword(helper.loadProperties("client_secret"));
		EmailInfo email = new EmailInfo(helper.loadProperties("redirectUrl"));
		user.setEmailInfo(email);
		return user;
	}

	
	
	
	
	
	
	
}
