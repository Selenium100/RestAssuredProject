package com.qa.apistesting;

import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.serializer.SerializeException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.pojo.LoginEcommResponse;
import com.qa.pojo.LoginPojoEcomm;
import com.qa.pojo.OrderDetails;
import com.qa.pojo.Orders;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class EcommApiE2eTest {

	@Test
	public void E2ETest() throws SerializeException {
		
		//Login
	RequestSpecification reqSpec =	new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
	
	LoginPojoEcomm login = new LoginPojoEcomm("nityaranjan190592@gmail.com", "Selenium@123");
	JsonSerializer jsonserializer = JsonSerializer.DEFAULT_READABLE;
	String loginJsonBody = jsonserializer.serialize(login);
	
	RequestSpecification reqLogin = given().log().all().spec(reqSpec).body(loginJsonBody);
	
	
	String loginRes = reqLogin.log().all().when().post("/api/ecom/auth/login").then()
	.log().all().extract().response().asPrettyString();
	
	JsonPath path = new JsonPath(loginRes);
	String tokenid = path.getString("token");
	String userId =path.getString("userId");
	System.out.println("Token is ------> " + tokenid.trim());
	System.out.println("UserId is -----> " + path.getString("userId"));
	
	Assert.assertEquals(path.getString("message"), "Login Successfully" , "Login is not Successful. Something went wrong!!");
	
	
	//Add Product
	RequestSpecification reqAddProductSpec =	new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com")
			.addHeader("authorization", tokenid.trim()).build();
	
//	Map<String, String> formDataMap = new HashMap<String, String>();
//	formDataMap.put("productName", "Nitya Product");
//	formDataMap.put("productAddedBy", userId);
//	formDataMap.put("productCategory", "fashion");
//	formDataMap.put("productSubCategory", "shirts");
//	formDataMap.put("productPrice", "11500");
//	formDataMap.put("productDescription", "women");
//	formDataMap.put("productFor", "Addidas Originals");
	
	RequestSpecification reqAddProduct = given().log().all().spec(reqAddProductSpec).param("productName","Nitya Product").
			param("productAddedBy",userId).
			param("productCategory","fashion").
			param("productSubCategory","shirts").
			param("productPrice","11500").
			param("productDescription","women").
			param("productFor","Addidas Originals").
			multiPart("productImage",new File("C:\\Users\\nitya\\TDDFrameword\\RestAssuredProject\\logo.png"));
	
	String addProductRes = reqAddProduct.log().all().when().post("/api/ecom/product/add-product")
	.then().log().all().extract().response().asPrettyString();
	
	JsonPath productPath = new JsonPath(addProductRes);
	String productId= productPath.getString("productId");
	String addProductMessage = productPath.getString("message");
	
	System.out.println("Product id is ------> " + productId);
	
	Assert.assertEquals(addProductMessage, "Product Added Successfully" , "Product is not added successfully. Something went wrong!!");
	
	
	//Create Order
	
	RequestSpecification reqCreateOrderSpec =	new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com")
			.addHeader("authorization", tokenid.trim()).setContentType(ContentType.JSON).build();
	
	OrderDetails orderDetails = new OrderDetails("India", productId);
	List<OrderDetails> orderList = new ArrayList<OrderDetails>();
	orderList.add(orderDetails);
	Orders order = new Orders(orderList);
	String createOrderBody = jsonserializer.serialize(order);
	RequestSpecification createOrderSpec = given().log().all().spec(reqCreateOrderSpec).body(createOrderBody);
	
	
	String createOrderRes = createOrderSpec.when().log().all().post("/api/ecom/order/create-order")
	.then().log().all().extract().response().asPrettyString();
	
	JsonPath createOrderJsonPath = new JsonPath(createOrderRes);
	System.out.println(createOrderJsonPath.getString("message"));
	
	Assert.assertEquals(createOrderJsonPath.getString("message"), "Order Placed Successfully");
	
	//Delete Product
	
	RequestSpecification reqDeleteProductSpec =	new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com")
			.addHeader("authorization", tokenid.trim()).setContentType(ContentType.JSON).build();
	
	String deleteRes = given().log().all().spec(reqDeleteProductSpec).when()
	.log().all().delete("/api/ecom/product/delete-product/"+productId)
	.then().log().all().extract().response().asPrettyString();
	
	JsonPath deleteJsonPath = new JsonPath(deleteRes);
	 deleteJsonPath.getString("message");
	
	Assert.assertEquals(deleteJsonPath.getString("message"), "Product Deleted Successfully", "Product is not deleted successfully. Something went wrong!!");
	
	
	
	}
}
