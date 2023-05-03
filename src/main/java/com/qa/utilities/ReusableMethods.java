package com.qa.utilities;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	public static JsonPath rawToJson(String response) {
		JsonPath jp = new JsonPath(response);
		return jp;
	}
	
	public static JsonPath rawToJsonInt(String response) {
		JsonPath jp = new JsonPath(response);
		return jp;
	}

}
