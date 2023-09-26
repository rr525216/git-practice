package Basics;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import payLoad.Payload;

public class SumValidations {

	@Test
	public void sumOfCourse() {
		JsonPath js = new JsonPath(Payload.coursePrice());
		int count = js.getInt("courses.size()");
		int totalPrice = 0;
		for (int i = 0; i < count; i++) {
			String courseTitle = js.get("courses[" + i + "].title");
			int coursePrice = js.getInt("courses[" + i + "].price");
			totalPrice += coursePrice * js.getInt("courses[" + i + "].copies");
		}
		System.out.println(totalPrice);
		int toatlAmount = js.getInt("dashboard.purchaseAmount");
		
		Assert.assertEquals(totalPrice, toatlAmount);
	}
 
}
