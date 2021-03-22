import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.payload;

public class APIAutomationBasics {

	public static void main(String[] args) {
		// Our Goal => Add Place -> Update Place with New Address and -> Get Place to validate if
		// new address is correct
		
		// given -- all input details
		// When -- Submit the API -- resource,http method
		// Then -- Validate the Response
		
		// Validate if Add Place API is working as Expected
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat()
		.statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		//Parsing the Json Response Body using JsonPath Class
		
		JsonPath js = new JsonPath(response); //for parsing Json
		String placeId = js.getString("place_id");
		
		System.out.println(placeId);
		
		
		//Validate the Put API(Update) is working as Expected
		String newAddress="Summer Walk, Africa";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		//Get Place API
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		//Validating the response using JsonPath Class
		
		JsonPath jsonPath = new JsonPath(getPlaceResponse);
		String actualAddress = jsonPath.getString("address");
		System.out.println(actualAddress);
		//Asserting through TestNg
		Assert.assertEquals(actualAddress, newAddress);
		

	}
}
