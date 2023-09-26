package Basics;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payLoad.Payload;
import payLoad.ReUsableMethods;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class DynamicJson {
	

	@Test(dataProvider="BooksData")
	public void addBookApi(String ibsn,String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json").body(Payload.addBook(ibsn,aisle)).when()
				.post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added")).extract().response().asString();

		JsonPath js = ReUsableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println("Book id :" +id);
		
//		deleteBook
		String deleteResponse = given().log().all().header("Content-Type", "application/json").body(Payload.deleteBook(id)).when()
				.post("/Library/DeleteBook.php").then().log().all().assertThat().statusCode(200).body("msg", equalTo("book is successfully deleted")).extract().response().asString();

		
	}
	
	

	@DataProvider(name="BooksData")
	public Object[][] getData() {
		//array =collection of homogenous elements 
		// multidimentional array = collection of arrays
		return new Object[][] {{"azby","1092"},{"cxdw","2876"},{"terw","4653"}};
	}
	
}
