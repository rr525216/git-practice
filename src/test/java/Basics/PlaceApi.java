package Basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import payLoad.Payload;
import payLoad.ReUsableMethods;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

public class PlaceApi {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		validate if Add place API is working as expected

		/*
		 * given -all input details when- Submit the API - resource,http method Then -
		 * Validate the response
		 */

//		

//		Set base URL
		RestAssured.baseURI = "https://rahulshettyacademy.com";

//		String response = given().log().all().queryParam("key", "qaclick123").header("Content-type", "application/json")
//				.body(Payload.addPlace()).when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
//				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response()
//				.asString();
		
		/*		Passing Json file as payload
		 * 1.convert content of the file to String jave have some methods which convert
		 * content file into Byte-> and Byte into String
		 */
		
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\RAJESCHI\\OneDrive - Capgemini\\Documents\\addPlace.json")))).when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response()
				.asString();

//		Add place->Update place with new address -> Get place to validate if new address is present in
		System.out.println("Response :" + response);
		JsonPath js = ReUsableMethods.rawToJson(response);
		// for parsing Json
		String placeId = js.getString("place_id");
		System.out.println("Place id : " + placeId);
		System.out.println("reference : " + js.getString("reference"));

		String newAddress = "GowliDoddi,SSR Pg,500075";
		given().log().all().queryParam("key", "qaclick123").header("content-Type", "application/json")
				.body(Payload.putPlace(placeId, newAddress)).when().put("maps/api/place/update/json").then()
				.assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract()
				.asString();

		JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println("Actual address " + actualAddress);
		Assert.assertEquals(newAddress, actualAddress);

	}

}
