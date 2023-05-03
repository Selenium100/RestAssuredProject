package com.qa.apistesting;

import org.testng.annotations.Test;

import com.qa.payloads.AddPlacePayload;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadJsonDataFromExternalFile {
	
	@Test
	public void createPlace() throws IOException {
		
		RestAssured.baseURI = AddPlacePayload.baseURI;
		
		String res = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\nitya\\TDDFrameword\\RestAssuredProject\\GoogleMap.json"))))
		.when().log().all().post("/maps/api/place/add/json").then().statusCode(200).extract()
		.response().asPrettyString();
		
		System.out.println(res);
		
		
	}

}
