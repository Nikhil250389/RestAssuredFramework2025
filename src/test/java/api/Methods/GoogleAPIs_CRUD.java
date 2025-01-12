package api.Methods;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.Payloads.UpdatePlace;
import api.Payloads.User;
import api.Payloads.location;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GoogleAPIs_CRUD {
	// method created for getting URL's from properties file
	static ResourceBundle getURL() {
		ResourceBundle routes = ResourceBundle.getBundle("routes"); // Load properties file // name of the properties
																	// file
		return routes;
	}

	public static Response createPlace(location userPayload)

	{
		String add_Place = getURL().getString("add_Place");

		Response res = given().contentType(ContentType.JSON).body(userPayload)

				.when().post(add_Place);

		return res;

	}

	public static Response getPlace(String place_id)

	{
		String get_place = getURL().getString("get_place");
		Response res = given().contentType(ContentType.JSON).pathParam("place_id", place_id)

				.when().get(get_place);

		return res;

	}

	public static Response updatePlace(String place_id, UpdatePlace update_userPayload)

	{
		String update_Place = getURL().getString("update_Place");
		Response res = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.pathParam("place_id", place_id).body(update_userPayload)

				.when().put(update_Place);

		return res;

	}

	public static Response deletePlace(User payload)

	{
		String delete_Place = getURL().getString("delete_Place");
		Response res = given().contentType(ContentType.TEXT.HTML).body(payload)

				.when().put(delete_Place);

		return res;

	}

}
