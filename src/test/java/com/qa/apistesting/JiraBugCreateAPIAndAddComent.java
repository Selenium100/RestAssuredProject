package com.qa.apistesting;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.utilities.ReusableMethods;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JiraBugCreateAPIAndAddComent {

	@Test
	public void createBug() throws IOException {

		RestAssured.baseURI = "http://localhost:8080";

		// Login with create sessionfilter
		SessionFilter session = new SessionFilter();
		String res = given().log().all().header("Content-Type", "application/json")
				.body(new String(Files
						.readAllBytes(Paths.get("C:\\Users\\nitya\\TDDFrameword\\RestAssuredProject\\Login.json"))))
				.filter(session).when().log().all().post("/rest/auth/1/session").then().assertThat().statusCode(200)
				.extract().response().asPrettyString();

		System.out.println(res);

		String sessionID = ReusableMethods.rawToJson(res).getString("session.value");
		System.out.println(sessionID);

		// Create Bug
		String createBugRes = given().log().all().header("Content-Type", "application/json")
				.body(new String(Files
						.readAllBytes(Paths.get("C:\\Users\\nitya\\TDDFrameword\\RestAssuredProject\\CreateBug.json"))))
				.filter(session).when().post("/rest/api/2/issue").then().statusCode(201).extract().response()
				.asPrettyString();

		System.out.println(createBugRes);

		String id = ReusableMethods.rawToJson(createBugRes).getString("id");
		System.out.println("id generated is " + id);

		// Add Comment
   String givenMessage = "This comment is from Nitya";
   System.out.println(">>>>>>>>>>>>>>Expected message is>>>>>>>>>>>>>>>> " + givenMessage);
	String commentResponse =	given().pathParam("key", id).log().all().header("Content-Type", "application/json")
				.body("{\r\n" + "  \"body\": \""+givenMessage+"\" , \r\n" + "   \"visibility\":{\r\n"
						+ "     \"type\": \"role\",\r\n" + "      \"value\": \"Administrators\"\r\n" + "   }\r\n" + "}")
				.filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat()
				.statusCode(201).extract().response().asPrettyString();
	
     int commentId =	ReusableMethods.rawToJson(commentResponse).getInt("id");
     System.out.println("Comment id is " + commentId);

		// Add Attachment(Not working)

//		String attachmentRes = given().log().all().header("X-Atlassian-Token", "no-check")
//				.filter(session).pathParam("key", id)
//				.header("Content-Type", "multipart/form-data").multiPart("file", new File("C:\\Users\\nitya\\TDDFrameword\\RestAssuredProject\\nitya.txt"))
//				.when().log().all().post("/rest/api/2/issue/{key}/attachments")
//				.then().assertThat().statusCode(200).extract().response().asPrettyString();
//
//		System.out.println(attachmentRes);
		
		//Get Issue
	String issueRes =	 given().log().all().filter(session).pathParam("key", id).queryParam("fields", "comment")
			.when().log().all().get("/rest/api/2/issue/{key}")
		.then().log().all().extract().response().asPrettyString();
	
	System.out.println(issueRes);
	
	int size = ReusableMethods.rawToJson(issueRes).getInt("fields.comment.comments.size()");
	System.out.println("Size is " + size);
	
	for(int i =0;i<size;i++) {
		
	
		JsonPath js = new JsonPath(issueRes);
		int commentIdReal =	js.getInt("fields.comment.comments["+i+"].id");
		
		if(commentIdReal == commentId) {
		String message =	js.getString("fields.comment.comments["+i+"].body");
		System.out.println(">>>>>>>>>>>>Real message is>>>>>>>>> " + message);
		Assert.assertEquals(givenMessage, message);
		}
		
		
		
		
		
	}
	}

	}


