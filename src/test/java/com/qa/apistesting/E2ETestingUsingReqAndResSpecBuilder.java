package com.qa.apistesting;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.payloads.AddPlacePayload;
import com.qa.utilities.ReusableMethods;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

public class E2ETestingUsingReqAndResSpecBuilder {

	// Nitya is modifing

	@Test(dataProvider = "getdata")
	public void createData(String place, String phoneno) {

		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri(AddPlacePayload.baseURI)
				.addQueryParam("key", "qaclick123").addHeader("Content-Type", "application/json").build();

		ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectBody("scope", equalTo("APP")).expectHeader("Server", equalTo("Apache/2.4.41 (Ubuntu)")).build();

		// POST by Nitya
		String response = given().log().all().spec(reqSpec).body(AddPlacePayload.addPlace(place, phoneno)).when().log()
				.all().post("/maps/api/place/add/json").then().log().all().assertThat().spec(resSpec).extract().response()
				.asString();

		System.out.println(response);

		JsonPath js = ReusableMethods.rawToJson(response);
		String placeid = js.getString("place_id");
		System.out.println(placeid);

		// GET
		String getRes = given().log().all().spec(reqSpec).queryParam("place_id", placeid).when().log()
				.all().get("/maps/api/place/get/json").then().assertThat().statusCode(200).extract().response()
				.asString();

		System.out.println(getRes);

		String address = ReusableMethods.rawToJson(getRes).getString("address");
		String phone = ReusableMethods.rawToJson(getRes).getString("phone_number");
		System.out.println(address + " <<<<<<<  is created with phone no >>>>> " + phone);

		// PUT

		String updatedResponse = given().log().all().spec(reqSpec)
				.body(AddPlacePayload.updateAddress(placeid, "Virat1 Kohli1")).when().log().all()
				.put("/maps/api/place/update/json").then().assertThat().statusCode(200).extract().response().asString();

		System.out.println(updatedResponse);

		// GET
		String getRes1 = given().log().all().spec(reqSpec).queryParam("place_id", placeid).when()
				.log().all().get("/maps/api/place/get/json").then().assertThat().statusCode(200).extract().response()
				.asString();

		System.out.println(getRes1);

		String address1 = ReusableMethods.rawToJson(getRes1).getString("address");
		String phone1 = ReusableMethods.rawToJson(getRes1).getString("phone_number");
		System.out.println(address1 + " <<<<<<<  is created with phone no >>>>> " + phone1);

		// DELETE

		String deleteRes = given().log().all().spec(reqSpec)
				.body("{\r\n" + "    \"place_id\":\"" + placeid + "\"\r\n" + "}").when().log().all()
				.delete("/maps/api/place/delete/json").then().assertThat().extract().response().asString();

		System.out.println(deleteRes);

		// GET after Delete

		String getRes2 = given().log().all().spec(reqSpec).queryParam("place_id", placeid).when()
				.log().all().get("/maps/api/place/get/json").then().assertThat().statusCode(404).extract().response()
				.asString();

		System.out.println(getRes2);

		String msg = ReusableMethods.rawToJson(getRes2).getString("msg");
		System.out.println(msg);

	}

	@DataProvider(name = "getdata")
	public Object[][] getData() {
		Object[][] obj = { { "Virat1 Kohli", "9717186318" }, { "Sourav1 Ganguli", "9931151837" },
				{ "Sachine Tendulkar1", "9437171478" } };
		return obj;
	}

}
