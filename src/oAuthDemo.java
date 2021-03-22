
import static io.restassured.RestAssured.*;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class oAuthDemo {

	public static void main(String[] args) throws InterruptedException {
		String[] courseTitles = {"Selenium WebDriver Java","Cypress","Protactor"};
				
		//GetAuthorization Code Request
		System.setProperty("webdriver.chrome.driver", "D:\\Personal_docs\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("deepankarsharma1205@gmail.com");
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("*********");
		Thread.sleep(4000);
		String url = driver.getCurrentUrl();
		
		//splitting the URL based on code keyword
		String partialcode = url.split("code=")[1];
		String code =  partialcode.split("&scope")[0];
		System.out.println(code);
		
		
		
		//GetAccessToken Request
		String accessTokenResponse = given().urlEncodingEnabled(false).
		queryParams("code",code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		//getting the access token
		String accessToken = js.getString("access_token");
		
		
		//Getting the response in a class type variable and attaching the response to a POJO class.
		//asking rest assured to read the response as a JSON by defaultParser method.
		
		GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		//Deserialization Codes in Rest assured using POJO classes.
		
		System.out.println("Linkedin property = " + gc.getLinkedIn());
		System.out.println("Instructor name is : " + gc.getInstructor());
		
		
		//fetching the course title with name 'SoapUI webservices testing' which is present in Inner JSON's.
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		//Getting the price for title with name 'SoapUI webservices testing'.
		List<Api> apicourses = gc.getCourses().getApi();
		for(int i=0;i<apicourses.size();i++) {
			if(apicourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI webservices testing"))
             {
				System.out.println(apicourses.get(i).getPrice());
			}
		}
		
		
		//Print all the course titles of webAutomation.
		ArrayList<String> a =  new ArrayList<String>();
		List<WebAutomation> wa = gc.getCourses().getWebAutomation();
		
		for(int i=0;i<wa.size();i++) {
			a.add(wa.get(i).getCourseTitle());
		}
		
		//Converting originally declared array to list
		List<String> expectedList=Arrays.asList(courseTitles);
		//Comparing the new list with the expected list
		Assert.assertTrue(a.equals(expectedList));
		
	}

}
