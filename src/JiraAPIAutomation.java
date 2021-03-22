import static io.restassured.RestAssured.*;

import java.io.File;

import org.mozilla.javascript.ast.NewExpression;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

public class JiraAPIAutomation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="http://localhost:8080";
		
		//Login Scenario
		SessionFilter session = new SessionFilter();
		
		
		//getting the session credentials
		String response = given().header("Content-Type","application/json").
		body("{ \"username\": \"deepankarsharma1205\", \"password\": \"Abhinav@!19942\" }").log().all().filter(session).
		when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		
		//Add a comment to a Bug in JIRA
		given().pathParam("key", "10026").log().all().header("Content-Type","application/json").body("{\r\n"
				+ "    \"body\": \"This is my first comment\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all()
		               .assertThat().statusCode(201);
		
		//Add an attachment to an existing Issue. multipart() method helps in achieving this
		given().header("X-Atlassian-Token","no-check").filter(session)
		.pathParam("key", "10026").header("Content-Type","multipart/form-data")
		.multiPart("file", new File("jira.txt"))
		.when()
		.post("/rest/api/3/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
		
		//Get Issue
		String issueDetails = given().filter(session).pathParam("key", "10026").when().
		get("/rest/api/2/issue/{key}").then().log().all().extract().response().asString();
		
		System.out.println(issueDetails);
		
	}

}
