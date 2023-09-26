package Basics;

import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import payLoad.Payload;

public class ComplexJsonParse {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(Payload.coursePrice());

//		Print No of course returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);

//		Print purchase amount
		int toatlAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Total Amount : " + toatlAmount);

//	Print all the title in nested JSon response
		List<String> titles = js.getList("courses.title");
		System.out.println(titles);

//	print title at index 0
		String title = js.getString("courses[0].title");
		System.out.println(title);

//	print title and respective price
		int totalPrice = 0;
		for (int i = 0; i < count; i++) {
			String courseTitle = js.get("courses[" + i + "].title");
			int coursePrice = js.getInt("courses[" + i + "].price");

			System.out.println(courseTitle + " = " + coursePrice);
			totalPrice += js.getInt("courses[" + i + "].price") * js.getInt("courses[" + i + "].copies");
		}
		System.out.println("Total Price : " + totalPrice);

//		To extract particular resource
		for (int i = 0; i < count; i++) {
			String courseTitle = js.get("courses[" + i + "].title");
			if (courseTitle.equalsIgnoreCase("RPA")) {
				int copies = js.get("courses[" + i + "].copies");
				System.out.println("courseTitle" + " : " + courseTitle);
				System.out.println("copies" + " : " + copies);
				break;
			}
		}

//		verify sum of all courses price
		Assert.assertEquals(totalPrice, toatlAmount);
	}

}
