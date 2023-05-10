package com.qa.apistesting;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.payloads.AddPlacePayload;
import com.qa.utilities.ReusableMethods;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;

import static io.restassured.RestAssured.*;

public class E2ETesting {
	
	//Nitya is modifing
	
	@Test(dataProvider = "getdata")
	public void createData(String place,String phoneno) {
		
		RestAssured.baseURI = AddPlacePayload.baseURI;
		
		//POST
	String response =	given().log().all().queryParam("key", "qaclick123").header("Content-Type" , "application/json")
		.body(AddPlacePayload.addPlace(place,phoneno))
		.when().log().all().post("/maps/api/place/add/json").then().log().all()
		.assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.41 (Ubuntu)"))
		.extract().response().asString();
	
	System.out.println(response);
	
	JsonPath js = ReusableMethods.rawToJson(response);
	String placeid = js.getString("place_id");
	System.out.println(placeid);
	
	//GET
	String getRes = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid).when().log().all().get("/maps/api/place/get/json").then()
	.assertThat().statusCode(200).extract().response().asString();
	
	System.out.println(getRes);
	
	String address = ReusableMethods.rawToJson(getRes).getString("address");
	String phone = ReusableMethods.rawToJson(getRes).getString("phone_number");
	System.out.println(address + " <<<<<<<  is created with phone no >>>>> " + phone);
	
	
	//PUT
	
	
	String updatedResponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type" , "application/json")
	.body(AddPlacePayload.updateAddress(placeid, "Virat1 Kohli1")).when().log().all()
	.put("/maps/api/place/update/json").then().assertThat().statusCode(200)
	.extract().response().asString();
	
	System.out.println(updatedResponse);
	
	//GET
	String getRes1 = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid).when().log().all().get("/maps/api/place/get/json").then()
			.assertThat().statusCode(200).extract().response().asString();
			
			System.out.println(getRes1);
			
			String address1 = ReusableMethods.rawToJson(getRes1).getString("address");
			String phone1 = ReusableMethods.rawToJson(getRes1).getString("phone_number");
			System.out.println(address1 + " <<<<<<<  is created with phone no >>>>> " + phone1);
			
			
     //DELETE
			
		String deleteRes =	 given().log().all().queryParam("key", "qaclick123").body("{\r\n" + 
					"    \"place_id\":\""+placeid+"\"\r\n" + 
					"}").when().log().all().delete("/maps/api/place/delete/json")
			.then().assertThat().extract().response().asString();
		
		System.out.println(deleteRes);
		
	//GET after Delete
		
		String getRes2 = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid).when().log().all().get("/maps/api/place/get/json").then()
				.assertThat().statusCode(404).extract().response().asString();
				
				System.out.println(getRes2);
				
				String msg = ReusableMethods.rawToJson(getRes2).getString("msg");
				System.out.println(msg);
	
	
	
   
   
	
	}
	
	@DataProvider(name = "getdata")
	public Object[][] getData (){
		Object[][] obj = {{"Virat Kohli","9777186318"},{"Sourav Ganguli","9938151837"},{"Sachine Tendulkar","9437271478"}};
		return obj;
	}
	

}
