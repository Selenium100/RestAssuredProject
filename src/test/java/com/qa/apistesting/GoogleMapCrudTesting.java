package com.qa.apistesting;

import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.serializer.SerializeException;
import org.testng.annotations.Test;

import com.qa.pojo.AddPlace;
import com.qa.pojo.Location;
import com.qa.pojo.Product;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapCrudTesting {

	@Test
	public void googleMapCrud() throws SerializeException {
		
		
		JsonSerializer jsonSerializer = JsonSerializer.DEFAULT_READABLE;
		
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		
		List<String> types = new ArrayList<String>();
		types.add("shoes park");
		types.add("shoes");
		

		AddPlace place = new AddPlace(location, 50, "Viswakalyan Nayak", "9938151837", types, "Viswa house", "www.viswa.com", "odia");

		String json = jsonSerializer.serialize(place);
		System.out.println(json);
		

		RestAssured.baseURI = "https://www.rahulshettyacademy.com";
		String res = given().log().all().queryParam("key", "qaclick123").body(json).when().log().all()
				.post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asPrettyString();
		
		System.out.println(res);

	}

}
