import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		//Query 1 : print Number of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		//Query 2: Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		
		//Query 3: Print Title of First Course
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);
		
		//Print all Course Titles and their Respective Prices
		for(int i=0;i<count;i++) {
			String courseTitles = js.get("courses["+i+"].title");
			System.out.println(js.get("courses["+i+"].price").toString());
			System.out.println(courseTitles);
		}
		
		//Print No of Copies sold by RPA Course
		System.out.println("Printing Number of copies sold by RPA course");
			
		for(int i=0;i<count;i++) {
			String courseTitles = js.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA")) {
				//copies sold
				int copies = js.get("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
	}

}
