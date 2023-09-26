package Basics;

import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath; 

public class OAuth2_0 {
	public static void main(String[] args) throws InterruptedException {
//		WebDriver driver=new ChromeDriver();
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		Thread.sleep(3000);
//		driver.findElement(By.cssSelector("input[type=email]")).sendKeys("rr525216@gmail.com");
//		driver.findElement(By.xpath("//span[text()='Next']")).click();
//		driver.findElement(By.cssSelector("input[type=password]")).sendKeys("Darlingdad1@");
//		driver.findElement(By.xpath("//span[text()='Next']")).click();
		String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AZEOvhVdVAYyM2fWAuVmbNi8YNruwewkyaVYT1QQPbUoEQNsUluUe-o58Wh756KH2avYKg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialCode=url.split("code=")[1];
		String code=partialCode.split("&scope")[0];
		
		
		String tokenResponse=given().urlEncodingEnabled(false).queryParam("code",code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code").when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		System.out.println(tokenResponse);
		
		JsonPath js=new JsonPath(tokenResponse);
		String accessToken=js.getString("access_token");
		
		String finalresponse=given().queryParam("access_token", accessToken).when().log().all().get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println("finalresponse"+finalresponse);
		
		
	}

}
