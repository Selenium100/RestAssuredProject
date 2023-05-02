package com.qa.payloads;

public class AddPlacePayload {
	
	public static final String baseURI = "https://rahulshettyacademy.com";
	
	public static String addPlace(String place,String phoneno) {
		return "{\r\n" + 
				"  \"location\": {\r\n" + 
				"    \"lat\": -38.383494,\r\n" + 
				"    \"lng\": 33.427362\r\n" + 
				"  },\r\n" + 
				"  \"accuracy\": 50,\r\n" + 
				"  \"name\": \"Frontline house\",\r\n" + 
				"  \"phone_number\": \""+phoneno+"\",\r\n" + 
				"  \"address\": \""+place+"\",\r\n" + 
				"  \"types\": [\r\n" + 
				"    \"shoe park\",\r\n" + 
				"    \"shop\"\r\n" + 
				"  ],\r\n" + 
				"  \"website\": \"http://google.com\",\r\n" + 
				"  \"language\": \"French-IN\"\r\n" + 
				"}";
	}
	
	public static String mockResponse() {
		return "{\r\n" + 
				"  \"id\": 1,\r\n" + 
				"  \"title\": \"iPhone 9\",\r\n" + 
				"  \"description\": \"An apple mobile which is nothing like apple\",\r\n" + 
				"  \"price\": 549,\r\n" + 
				"  \"discountPercentage\": 12.96,\r\n" + 
				"  \"rating\": 4.69,\r\n" + 
				"  \"stock\": 94,\r\n" + 
				"  \"brand\": \"Apple\",\r\n" + 
				"  \"category\": \"smartphones\",\r\n" + 
				"  \"thumbnail\": \"https://i.dummyjson.com/data/products/1/thumbnail.jpg\",\r\n" + 
				"  \"images\": [\r\n" + 
				"    \"https://i.dummyjson.com/data/products/1/1.jpg\",\r\n" + 
				"    \"https://i.dummyjson.com/data/products/1/2.jpg\",\r\n" + 
				"    \"https://i.dummyjson.com/data/products/1/3.jpg\",\r\n" + 
				"    \"https://i.dummyjson.com/data/products/1/4.jpg\",\r\n" + 
				"    \"https://i.dummyjson.com/data/products/1/thumbnail.jpg\"\r\n" + 
				"  ]\r\n" + 
				"}\r\n" + 
				"Yay!";
	}
	
	public static String updateAddress(String placeid,String updatedAddress) {
		return "{\r\n" + 
				"\"place_id\":\""+placeid+"\",\r\n" + 
				"\"address\":\""+updatedAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}";
	}

}
