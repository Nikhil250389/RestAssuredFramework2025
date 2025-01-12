package api.Methods;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.Payloads.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints3 {
	// method created for getting URL's from properties file
	static ResourceBundle getURL() {
		ResourceBundle routes = ResourceBundle.getBundle("routes"); // Load properties file // name of the properties
																	// file
		return routes;
	}

	public static Response createUser(User payload)

	{
		String post_url = getURL().getString("post_url");

		Response res = given().contentType(ContentType.JSON).body(payload)

				.when().post(post_url);

		return res;

	}

	public static Response getUser(String id)

	{
		String get_url = getURL().getString("get_url");
		Response res = given().contentType(ContentType.JSON).pathParam("id", id)

				.when().get(get_url);

		return res;

	}

	public static Response updateUser(String id, User payload)

	{
		String update_url = getURL().getString("update_url");
		Response res = given().contentType(ContentType.JSON).pathParam("id", id).body(payload)

				.when().put(update_url);

		return res;

	}
}
