import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializationDemo {

	public static void main(String[] args) {
		
		//Base URL
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Serialization Code to convert a java object to request body.
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("https://rahulshettyacademy.com");
		p.setName("Frontline house");
		//Creating a list and passing it to setTypes() method
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList);
		//Create a location class object and passing it to setLocation() method.
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);
		
		
		//passing the object of main POJO class in body() method.
	   String response = given().queryParam("key", "qaclick123")
	    .body(p)
	    .when().post("/maps/api/place/add/json").then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(response);
	}

}
