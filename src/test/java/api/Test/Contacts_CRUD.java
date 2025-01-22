
package api.Test;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.github.javafaker.Faker;

import api.Methods.Contact_CRUD;
import api.Methods.Routes;
import api.Payloads.CreateContacts;
import api.Payloads.EditContact;
import api.Payloads.LoginContact;
import io.restassured.response.Response;

public class Contacts_CRUD {

	static Faker faker;
	static LoginContact loginPayload;
	String userid;
	public static Logger logger; // for logs
	static String token;
	CreateContacts userCreateContact;
	String firstName;
	static SoftAssert softassert = new SoftAssert();

	@BeforeTest
	public static void setUpData() {
		logger = LogManager.getLogger(Contacts_CRUD.class.getName());

		logger.debug("debugging.....");
		faker = new Faker();
		loginPayload = new LoginContact();
		loginPayload.setEmail(Routes.email);
		loginPayload.setPassword(Routes.password);
		logger.info(" Login with valid User");
		Response responce = Contact_CRUD.userLogin(loginPayload);
		responce.then().log().all().extract().asString();

		String id = responce.jsonPath().get("user._id");
		String firstName = responce.jsonPath().get("user.firstName");
		String lastName = responce.jsonPath().get("user.lastName");
		String email = responce.jsonPath().get("user.email");
		token = responce.jsonPath().get("token");

		logger.info(" Validating the User after login");
		softassert.assertEquals(responce.getStatusCode(), 200);
		softassert.assertEquals(firstName, Routes.firstName);
		softassert.assertEquals(lastName, Routes.lastName);
		softassert.assertEquals(email, Routes.email);
		softassert.assertAll();
	}

	@Test(priority = 1)

	public void createUser() {
		logger.info(" Creating the User Contact");
		userCreateContact = new CreateContacts();
		userCreateContact.setBirthdate("1986-10-08");
		userCreateContact.setCity("GZB");
		userCreateContact.setCountry("India");
		userCreateContact.setEmail(faker.internet().emailAddress());
		userCreateContact.setFirstName(faker.name().firstName());
		userCreateContact.setLastName(faker.name().lastName());
		// userCreateContact.setPhone(viewAccuracy);
		String remdomPhone = faker.number().digits(5);
		userCreateContact.setPhone("0" + remdomPhone);
		// int postalCode = Integer.parseInt(faker.address().zipCode());
		userCreateContact.setPostalCode(faker.number().digits(5));
		userCreateContact.setStateProvince("UttarPardesh");
		userCreateContact.setStreet1(faker.address().streetAddress());

		Response responce = Contact_CRUD.createContact(userCreateContact, token);
		responce.then().log().all().extract().asString();

		String firstName = responce.jsonPath().get("firstName");
		String lastName = responce.jsonPath().get("lastName");
		userid = responce.jsonPath().get("_id");
		String birthdate = responce.jsonPath().get("birthdate");
		String email = responce.jsonPath().get("email");
		String phoneNumber = responce.jsonPath().get("phone");

		softassert.assertEquals(responce.getStatusCode(), 201);
		softassert.assertEquals(firstName, userCreateContact.getFirstName());
		softassert.assertEquals(birthdate, userCreateContact.getBirthdate());
		softassert.assertEquals(email, userCreateContact.getEmail());
		softassert.assertEquals(phoneNumber, userCreateContact.getPhone());
		softassert.assertAll();
	}

	@Test(priority = 2, dependsOnMethods = "createUser", enabled = false)

	public void viewContact() {
		logger.info(" Vewing  the User Contact Details");

		Response responce = Contact_CRUD.viewContact(token);
		responce.then().log().all().extract().asString();

		List<Map<String, Object>> contactList = responce.jsonPath().getList("$");

		// Print the extracted list
		boolean status = false;
		for (Map<String, Object> userContacts : contactList) {
			if (userContacts.get("_id").equals(userid)) {
				status = true;
				System.out.println("Found the Valid user Id");
				break;

			}
		}
		// Assert.assertEquals(responce.getStatusCode(), 200);
		// Assert.assertEquals(status, true);
		// String userFirstName = userContacts.get("firstName"));
		// System.out.println("ID: " + userContacts.get("_id"));
		// System.out.println("Name: " + userContacts.get("firstName"));

		softassert.assertEquals(responce.getStatusCode(), 200);
		softassert.assertEquals(status, true);
		softassert.assertAll();
		// String userFirstName = (String) userContacts.get("firstName");
		/// System.out.println("Name345: " + userFirstName);
		// System.out.println("ID: " + userContacts.get("_id"));
		// System.out.println("Name: " + userContacts.get("firstName"));
	}

	@Test(priority = 3, dependsOnMethods = "createUser")

	public void editContactDeatils() {
		logger.info("Edit the User Contact Details");
		EditContact editContact = new EditContact();
		editContact.setBirthdate("1986-10-10");
		editContact.setCity("Lahor");
		editContact.setCountry("Pak");
		editContact.setEmail(faker.internet().emailAddress());
		editContact.setFirstName(faker.name().firstName());
		editContact.setLastName(faker.name().lastName());
		// userCreateContact.setPhone(viewAccuracy);
		String remdomPhone = faker.number().digits(5);
		editContact.setPhone("0" + remdomPhone);
		// int postalCode = Integer.parseInt(faker.address().zipCode());
		editContact.setPostalCode(faker.number().digits(5));
		editContact.setStateProvince("Islamabad");
		editContact.setStreet1(faker.address().streetAddress());
		// editContact.setBirthdate(token);
		Response responce = Contact_CRUD.editContact(editContact, token, userid);
		responce.then().log().all().extract().asString();

		String firstName = responce.jsonPath().get("firstName");
		String lastName = responce.jsonPath().get("lastName");
		userid = responce.jsonPath().get("_id");
		String birthdate = responce.jsonPath().get("birthdate");
		String email = responce.jsonPath().get("email");
		String phoneNumber = responce.jsonPath().get("phone");

		softassert.assertEquals(responce.getStatusCode(), 200);
		softassert.assertEquals(firstName, editContact.getFirstName());
		// Assert.assertEquals(birthdate, editContact.getBirthdate());
		softassert.assertEquals(email, editContact.getEmail());
		softassert.assertEquals(phoneNumber, editContact.getPhone());
		softassert.assertAll();
	}

	@Test(priority = 4, dependsOnMethods = { "createUser", "editContactDeatils" }, alwaysRun = true)

	public void viewContactAfterUpdate() {
		logger.info(" Viewing the User Contact Details after update");

		Response responce = Contact_CRUD.viewContact(token);
		responce.then().log().all().extract().asString();

		List<Map<String, Object>> contactList = responce.jsonPath().getList("$");

		// Print the extracted list
		boolean status = false;
		for (Map<String, Object> userContacts : contactList) {
			if (userContacts.get("_id").equals(userid)) {
				status = true;
				System.out.println("Found the Valid user Id");
				break;

			}

		}
		softassert.assertEquals(responce.getStatusCode(), 200);
		softassert.assertEquals(status, true);
		softassert.assertAll();
	}

	@Test(priority = 5, dependsOnMethods = { "createUser", "editContactDeatils", "viewContactAfterUpdate" })

	public void deleteContact() {
		logger.info(" Deleting the User Contact deatils");

		Response responce = Contact_CRUD.deleteContact(token, userid);
		responce.then().log().all().extract().asString();
		softassert.assertEquals(responce.getStatusCode(), 200);
		softassert.assertAll();
	}

	@Test(priority = 6)

	public void viewContactAfterDelete() {
		logger.info(" Viwing the User Contact Details after delete");

		Response responce = Contact_CRUD.viewContact(token);
		responce.then().log().all().extract().asString();

		System.out.println("Deleted the user Contact details");

		softassert.assertEquals(responce.getStatusCode(), 206);
		softassert.assertAll();
	}

}
