package org.petstore;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.data.PetDetails;
public class Services {
public static void main(String[] args) {
	RestAssured.baseURI="https://petstore.swagger.io/v2";
	
	String postResponse = given().log().all().header("Content-Type","application/json").body(PetDetails.petData("twinkle",150))
	.when().post("/pet")
	.then().assertThat().statusCode(200).extract().response().asString();
	System.out.println("response :"+postResponse);
	JsonPath j=new  JsonPath(postResponse);
	int id=j.get("id");
	System.out.println("pet id :"+id);
	given().log().all().header("Content-Type","application/json").pathParam("id",id)
	.when().get("/pet/{id}")
	.then().log().all().assertThat().statusCode(200);
	
	
}
}
