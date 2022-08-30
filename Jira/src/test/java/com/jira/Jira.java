package com.jira;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
public class Jira {
	
	public static void main(String[] args) {
	RestAssured.baseURI="http://localhost:8080";
	 SessionFilter session=new SessionFilter();
	String issueResponse=	given().log().all().header("Content-Type","application/json")
			.body("{ \"username\": \"vinithsundar360\", \"password\": \"9629903231\" }").filter(session)
.when().post("/rest/auth/1/session")
.then().log().all().assertThat().statusCode(200).extract().response().asString();
	
	given().log().all().header("Content-Type","application/json").filter(session) 
	.body("{\r\n" + 
			"    \"fields\": {\r\n" + 
			"       \"project\":\r\n" + 
			"       {\r\n" + 
			"          \"key\": \"RES\"\r\n" + 
			"       },\r\n" + 
			"       \"summary\": \"Session 03 is not created.\",\r\n" + 
			"       \"description\": \"Creating of an issue using ids for projects and issue types using the REST API\",\r\n" + 
			"       \"issuetype\": {\r\n" + 
			"          \"name\": \"Bug\"\r\n" + 
			"       }\r\n" + 
			"   }\r\n" + 
			"}")
	.when().post("/rest/api/2/issue")
	.then().log().all().assertThat().statusCode(201).extract().response().asString();
	
	JsonPath j=new JsonPath(issueResponse);
	String jira_id=j.get("Key");
	
	given().log().all().pathParam("key",jira_id).header("Content-Type","application/json").filter(session) 
	.body("{\r\n" + 
			"    \"body\": \"First comment03\",\r\n" + 
			"    \"visibility\": {\r\n" + 
			"        \"type\": \"role\",\r\n" + 
			"        \"value\": \"Administrators\"\r\n" + 
			"    }\r\n" + 
			"}")
	.when().post("/rest/api/2/issue/{key}/comment")
	.then().log().all().assertThat().statusCode(201);
	
	 
	
	
	
	
	
	}}
