package org.oath;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
public class Courses {
	public static void main(String[] args) {

		String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qgsG_qApB3mLekbYqWQ180GFNOjV9x4PguW_Ok4sok6gpUpamLp6tFuQVv-1WDB8Q&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
	String[]s=	url.split("code=");
	String[]s1=	s[1].split("&scope=");

		String tokenResponse =	given().log().all()
		.queryParam("code", s1[0])	
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type","authorization_code").urlEncodingEnabled(false)
		.when().post("https://www.googleapis.com/oauth2/v4/token")
		.then().assertThat().statusCode(200).extract().response().asString();

		JsonPath j=new JsonPath(tokenResponse);
		String token=j.get("access_token");
	
String response =given().log().all().queryParam("access_token", token).header("Content-Type","application/json")
.when().get("https://rahulshettyacademy.com/getCourse.php")
	.then().assertThat().statusCode(200).extract().response().asString();
	System.out.println(response);
	
	JsonPath j1 = new JsonPath(response);
	
	String instructor = j1.get("instructor");
	System.out.println("instructor name:"+instructor);
	
	String URL = j1.get("url");
	System.out.println("url:"+URL);

	String services = j1.get("services");
	System.out.println("service:"+services);
	
	String expertise = j1.get("expertise");
	System.out.println("expertise:"+expertise);
	
	 int  size1 = j1.get("courses.webAutomation.size()");
	 int  size2 = j1.get("courses.api.size()");
	 int  size3 = j1.get("courses.mobile.size()");
	 
	for (int i = 0; i <size1; i++) {
		System.out.println( j1.get("courses.webAutomation["+i+"].courseTitle"));
		System.out.println( j1.get("courses.webAutomation["+i+"].price"));
		}
	for (int i = 0; i <size2; i++) {
		System.out.println( j1.get("courses.api["+i+"].courseTitle"));
		System.out.println(j1.get("courses.api["+i+"].price"));
	}
	for (int i = 0; i <size3; i++) {
		System.out.println(j1.get("courses.mobile["+i+"].courseTitle"));
	    System.out.println( j1.get("courses.mobile["+i+"].price"));
}
	System.out.println( j1.get("linkedIn"));
	
	
	}}
