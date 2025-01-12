
package api.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.Methods.UserEndPoints;
import api.Payloads.User;
import io.restassured.response.Response;

public class UserTest {

	static Faker faker;
	static User userPayload;
	String userid;
	public static Logger logger; // for logs

	@BeforeClass
	public static void setUpData() {
		faker = new Faker();
		userPayload = new User();
		// userPayload.setId(faker.idNumber().hashCode());
		// userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setJob(faker.name().firstName());
		userPayload.setName(faker.name().firstName());
		// userPayload.setPassword(faker.internet().password(5, 10));
		// logs
		logger = LogManager.getLogger(UserTest.class.getName());

		logger.debug("debugging.....");
	}

	@Test(priority = 1)

	public void createUser() {
		logger.info("************* Creating the User************");
		Response responce = UserEndPoints.createUser(userPayload);
		responce.then().log().all().extract().asString();

		String Username = responce.jsonPath().get("name");
		String job = responce.jsonPath().get("job");
		userid = responce.jsonPath().get("id");

		logger.info("************* Validating the User after creation************");
		Assert.assertEquals(responce.getStatusCode(), 201);
		Assert.assertEquals(Username, this.userPayload.getName());
		Assert.assertEquals(job, this.userPayload.getJob());
	}

	@Test(priority = 2)

	public void updateUser() {

		logger.info("************* Updating the User************");
		userPayload.setJob(faker.name().firstName());
		userPayload.setName(faker.name().firstName());
		Response responce = UserEndPoints.updateUser(userid, userPayload);
		responce.then().log().all().extract().asString();
		// System.out.println("Update Name Is>>>" + responce.jsonPath().get("Name"));

		logger.info("************* Validating the user after update user info************");
		String updatedUsername = responce.jsonPath().get("name");
		String updatedJob = responce.jsonPath().get("job");
		Assert.assertEquals(responce.getStatusCode(), 200);
		Assert.assertEquals(updatedUsername, this.userPayload.getName());
		Assert.assertEquals(updatedJob, this.userPayload.getJob());
	}

	// @Test(priority = 3)

	public void getUser() {
		logger.info("************* Validating by view User info************");

		Response responce = UserEndPoints.getUser(userid);
		responce.then().log().all().extract().asString();
		String id = responce.jsonPath().get("data.id");
		Assert.assertEquals(id, userid);
	}
}