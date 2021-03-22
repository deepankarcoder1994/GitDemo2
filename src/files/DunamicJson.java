package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DunamicJson {
	
	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {
		
		RestAssured.baseURI="http://216.10.245.166";
		//Sending the parameters to payload ie. body(payload.AddBook("panama","64644")).
		String response = given().log().all().header("Content-Type", "application/json").body(payload.AddBook(isbn,aisle)).
		when().post("/Library/Addbook.php")
        .then().assertThat().statusCode(200)
        .extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	//Parameterizing the Test Cases.each array in this multidimensional array represents the data on which a test has to be executed.
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		//Array - Collection of elements
		//Multidimensional array = collection of arrays
		return new Object[][] {{"ojfwty","9363"},{"cwetee","4253"},{"okmfet","5434"}};
	}
}
