package com.qa.apistesting;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import com.qa.payloads.AddPlacePayload;
import com.qa.utilities.ReusableMethods;


public class BasicApiTesting {

	public static void main(String[] args) {
		
		//valid Add Place
		
		//given,when,then
		//given - all input details
		// when - Submit the API - endpoint and http method will go with when
		//then - Validate
		
		
		//POST
	RestAssured.baseURI = "https://www.rahulshettyacademy.com";
	String res =  given().log().all().queryParam("key", "qaclick123").header("Content-Type" , "application/json")
		.body(AddPlacePayload.addPlace("Nitya Residence","7303709376")).when().post("/maps/api/place/add/json").then()
		.assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.41 (Ubuntu)"))
		.extract().response().asString();
		
		System.out.println(res);
		
		//Get place_id from response
		JsonPath js = ReusableMethods.rawToJson(res);
		String placeid = js.getString("place_id");
		System.out.println(placeid);
		
		
		//get the address which is just created
		
		String response1 = given().log().all().queryParam("key", "qaclick123")
				.queryParam("place_id", placeid).when().log().all().get("/maps/api/place/get/json")
				.then().log().all().extract().response().asString();
				
				
				String createdAddress = ReusableMethods.rawToJson(response1).getString("address");
				System.out.println("#####Created address is###### " + createdAddress);
		
		//update the address
		String updatedAddress = "Kalpana Residence";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type" , "application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeid+"\",\r\n" + 
				"\"address\":\""+updatedAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}").when().log().all().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		
		//get the updated address to validate
		
		String response = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeid).when().log().all().get("/maps/api/place/get/json")
		.then().log().all().extract().response().asString();
		
		System.out.println(response);
		
		String actualUpdatedAddress = ReusableMethods.rawToJson(response).getString("address");
		System.out.println("######Updated address fetched from GET command after updated is #### " + actualUpdatedAddress );
		Assert.assertEquals(actualUpdatedAddress, updatedAddress);

	}

}
