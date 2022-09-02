package org.oath;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

import java.util.List;

import org.pojo.AppResponse;
import org.pojo.webAutomation;
public class Courses {
	public static void main(String[] args) {

		String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qiniKubQgp0ITY_bflkDTGfSwy7c3NQvLASgJRAGhGm_aFtndlbePTb8igbVwaeqg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
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
	
		AppResponse response =given().log().all().queryParam("access_token", token).header("Content-Type","application/json")
				.expect().defaultParser(Parser.JSON)
.when().get("https://rahulshettyacademy.com/getCourse.php")
	.then().assertThat().statusCode(200).extract().response().as(AppResponse.class);
		
	response.getInstructor();
	
	org.pojo.Courses c = response.getCourses();
	List<webAutomation> l=c.getWebAutomation();
	 for (int i = 0; i < l.size(); i++) {
		System.out.println(l.get(i).getCourseTitle());
	}
	}}
