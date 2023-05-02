package com.qa.apistesting;

import com.qa.payloads.AddPlacePayload;

import io.restassured.path.json.JsonPath;

public class MockResponseExtract {

	public static void main(String[] args) {
		
	String mockRes =	 AddPlacePayload.mockResponse();
	
	JsonPath jp = new JsonPath(mockRes);
	int imgNum = jp.getInt("images.size()");
	String des = jp.getString("description");
	System.out.println(des);
	System.out.println(imgNum);

	}

}
