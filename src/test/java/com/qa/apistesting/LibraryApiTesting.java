package com.qa.apistesting;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class LibraryApiTesting {
	
	@Test
	public void AddBook() {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//given().log().all().header("Content-Type" , "application/json").body()
	}

}
