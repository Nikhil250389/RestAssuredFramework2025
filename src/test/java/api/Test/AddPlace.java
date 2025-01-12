
package api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.github.javafaker.Faker;

import api.Methods.GoogleAPIs_CRUD;
import api.Payloads.GooglePlace;
import api.Payloads.UpdatePlace;
import api.Payloads.location;
import io.restassured.response.Response;

public class AddPlace {
	static ResourceBundle getURL() {
		ResourceBundle routes = ResourceBundle.getBundle("routes"); // Load properties file // name of the properties
																	// file
		return routes;
	}

	static Faker faker;
	static location userPayload;
	static UpdatePlace update_userPayload;
	String place_id;
	String id;
	String update_Place = getURL().getString("update_Place");
	public static Logger logger; // for logs
	SoftAssert softAssert = new SoftAssert();

	@BeforeClass
	public static void setUpData() {
		faker = new Faker();
		userPayload = new location();
		// userPayload.setId(faker.idNumber().hashCode());
		// userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setAccuracy(faker.number().randomDigit());
		userPayload.setName(faker.name().firstName());
		userPayload.setPhone_number(faker.phoneNumber().phoneNumber());
		userPayload.setAddress(faker.address().streetAddress());
		userPayload.setWebsite("http://google.com");
		userPayload.setLanguage("French-IN");
		GooglePlace ls = new GooglePlace();
		ls.setLat(-38.383494);
		ls.setLng(-33.427362);
		userPayload.setLocation(ls);
		// logs
		logger = LogManager.getLogger(AddPlace.class.getName());

		logger.debug("debugging.....");
	}

	@Test(priority = 1)

	public void createPlace() {
		logger.info("************* Creating the User************");
		Response responce = GoogleAPIs_CRUD.createPlace(userPayload);
		responce.then().log().all().extract().asString();

		String status = responce.jsonPath().get("status");
		String scope = responce.jsonPath().get("scope");
		String reference = responce.jsonPath().get("reference");
		place_id = responce.jsonPath().get("place_id");
		id = responce.jsonPath().get("id");

		logger.info("************* Validating the User after creation************");
		Assert.assertEquals(responce.getStatusCode(), 200);
		Assert.assertEquals(status, "OK");
		softAssert.assertAll();
	}

	@Test(priority = 2)

	public void viewPlace() {
		logger.info("************* Validating by view User info************");

		Response responce = GoogleAPIs_CRUD.getPlace(place_id);
		responce.then().log().all().extract().asString();
		String viewLatitude = responce.jsonPath().get("location.latitude");

		String viewLongitude = responce.jsonPath().get("location.longitude");
		String viewaccuracy = responce.jsonPath().get("accuracy");
		int viewAccuracy = Integer.parseInt(viewaccuracy);
		String viewName = responce.jsonPath().get("name");
		String viewPhoneNumber = responce.jsonPath().get("phone_number");
		String viewAddress = responce.jsonPath().get("address");
		String viewWebsite = responce.jsonPath().get("website");
		String viewLanguage = responce.jsonPath().get("language");

		Assert.assertEquals(viewLatitude, "-38.383494");

		Assert.assertEquals(viewLongitude, "-33.427362");
		Assert.assertEquals(viewAccuracy, this.userPayload.getAccuracy());

		Assert.assertEquals(viewName, this.userPayload.getName());
		Assert.assertEquals(viewPhoneNumber, this.userPayload.getPhone_number());

		Assert.assertEquals(viewAddress, this.userPayload.getAddress());

		Assert.assertEquals(viewWebsite, this.userPayload.getWebsite());
		Assert.assertEquals(viewLanguage, this.userPayload.getLanguage());
		System.out.println("Place id++++" + place_id);
		// Assert.assertEquals(id, place_id);

		// System.out.println("999999_________" + viewaccuracy);

		// System.out.println("999999_________" + viewLongitude);
		softAssert.assertAll();
	}

	// @Test(priority = 3)
	public void updatePlace2() {

		logger.info("************* Creating the User with pojo************");
		update_userPayload = new UpdatePlace();
		update_userPayload.setPlace_id(place_id);
		update_userPayload.setAddress(faker.address().streetAddress());
		Response responce = GoogleAPIs_CRUD.updatePlace(place_id, update_userPayload);
		responce.then().log().all().extract().asString();

		String msg = responce.jsonPath().get("msg");
		System.out.println("**********" + msg);
	}

	@Test(priority = 3)

	public void updatePlace() {
		logger.info("************* Updating the User************");
		String putResponce = given().log().all().queryParam("Key", "qaclick123")
				.header("Content-Type", "application/json")
				.body(api.Payloads.JsonFullPayload.JsonPayloads.updatePlace(place_id)).when().put(update_Place).then()
				.assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated")).extract()
				.response().asString();
		System.out.println(putResponce);
	}
}
