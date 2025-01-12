package api.Methods;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.Payloads.CreateContacts;
import api.Payloads.EditContact;
import api.Payloads.LoginContact;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Contact_CRUD {
	// method created for getting URL's from properties file
	static ResourceBundle getURL() {
		ResourceBundle routes = ResourceBundle.getBundle("routes"); // Load properties file // name of the properties
																	// file
		return routes;
	}

	public static Response userLogin(LoginContact Payload)// Provide the pojo payload

	{
		String login_URL = getURL().getString("login_URL");

		Response res = given().contentType(ContentType.JSON).body(Payload)

				.when().post(login_URL);

		return res;

	}

	public static Response createContact(CreateContacts userCreateContact, String token)

	{
		String AddContact = getURL().getString("AddContact");

		Response res = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + token)
				.body(userCreateContact)

				.when().post(AddContact);

		return res;

	}

	public static Response viewContact(String token)

	{
		String ViewContact = getURL().getString("ViewContact");
		Response res = given().contentType(ContentType.JSON).header("Authorization", "Bearer " + token)

				.when().get(ViewContact);

		return res;

	}

	public static Response editContact(EditContact editContact, String token, String userId)

	{
		String UpdateContact = getURL().getString("UpdateContact");

		Response res = given().contentType(ContentType.JSON).pathParam("id", userId)
				.header("Authorization", "Bearer " + token).body(editContact)

				.when().put(UpdateContact);

		return res;

	}

	public static Response deleteContact(String token, String userId)

	{
		String DeleteContact = getURL().getString("DeleteContact");
		Response res = given().contentType(ContentType.JSON).pathParam("id", userId)
				.header("Authorization", "Bearer " + token)

				.when().delete(DeleteContact);

		return res;

	}
}
