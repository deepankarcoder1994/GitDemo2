package pojo;

public class GetCourse {
	
	private String url;
	private String services;
	private String expertise;
	private Courses Courses;
	private String instructor;
	private String linkedIn;
	
	
	public String getUrl() {
		return url;
	}
	public String getServices() {
		return services;
	}
	public String getExpertise() {
		return expertise;
	}
	
	//Injecting the inner Json to parent Json
	public Courses getCourses() {
		return Courses;
	}
	public void setCourses(Courses courses) {
		Courses = courses;
	}
	
	public String getInstructor() {
		return instructor;
	}
	public String getLinkedIn() {
		return linkedIn;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

}
