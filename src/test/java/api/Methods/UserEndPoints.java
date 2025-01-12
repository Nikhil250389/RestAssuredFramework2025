package api.Methods;

import static io.restassured.RestAssured.given;

import api.Payloads.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints {

	public static Response createUser(User payload)

	{

		Response res = given().contentType(ContentType.JSON).body(payload)

				.when().post(Routes.post_URL);

		return res;

	}

	public static Response getUser(String id)

	{

		Response res = given().contentType(ContentType.JSON).pathParam("id", id)

				.when().get(Routes.get_URL);

		return res;

	}

	public static Response updateUser(String id, User payload)

	{

		Response res = given().contentType(ContentType.JSON).pathParam("id", id).body(payload)

				.when().put(Routes.update_URL);

		return res;

	}
}
