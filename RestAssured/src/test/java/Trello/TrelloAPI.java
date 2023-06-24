package Trello;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TrelloAPI {
	
	/* Creating the Variable */
	public static String BaseURL ="https://api.trello.com";
	public static String Key ="ffbf75f1ada9d939dbac3939765a6966";
	public static String Token ="ATTA4d04181387ab033ef2d967bbbe1f12bb7a49f9c89d7d83a92b0aa36625169af3A0889D9D";
	public static String ID;
	public static String createTable;
	public static String rowcount;
	
	@Test (priority =0)
	public void Boards() throws IOException
	{
		// The Below line will get the path the excel sheet and store in the variable called excel
		File excel = new File ("C:\\Users\\iftikhar\\Documents\\RestAssured\\RestAssured\\TrelloRest.xlsx");
		// The below line will help me to read the excel as input file
		FileInputStream fls = new FileInputStream(excel);
		// This below line will help me to go the workbook of the excel which am reading from the input stream
		XSSFWorkbook wb = new XSSFWorkbook(fls);
		// This below line will help me to go the specific sheet
		XSSFSheet sh = wb.getSheet("Sheet1");
		// The below line will tell me how many rows are there
		int rowcount = sh.getLastRowNum();
		for(int i=0;i<=rowcount;i++)
		{
			createTable = sh.getRow(i).getCell(0).getStringCellValue();
			System.out.println(createTable);
			CreateBoard();
			GetBoard();
			DeleteBoard();					
		}		
	}
	
	@Test (priority = 0, enabled = false)
	public void CreateBoard()
	{
		/* API */
		/* Request & Response */
		/* Authorization, Body, Validation, BaseURL, resource, query parameter */
		
	RestAssured.baseURI = BaseURL;
	
	// Rest Assured contains 3 methods are 
	// given:Pre-cond, its a method which can take body, headers, auth, content-type, query parameter(key-token)
	// when:action method, only one thing in when // HTTP method name are, GET, POST, PUT, PATCH, DELETE
	// then Post Cond, Status or response
	
	// Given is a intial method which carries the request and fetches the response for that given method, then and when will be part of it
	
	Response response = given()
	.queryParam("key", Key)
	.queryParam("token", Token)
	.queryParam("name", createTable)
	.header("Content-Type","application/json")
	
	.when()
	.post("/1/boards/")	
	
	.then()
	.assertThat().statusCode(200)
	.extract().response();
	// THis below line will print the response
	//System.out.println(response.asString());
	
	// Am String the response in a variable in the String Format
	String jsonresponse = response.asString();
	// Converting the response in string to response in json,
	JsonPath Js = new JsonPath(jsonresponse);
	
	ID = Js.get("id");
	System.out.println(ID);
	
	// Fetch the Response, but do remember the response will in a simple string format, so we need to convert into json
	}

	@Test (priority = 1, enabled = false)
	public void GetBoard()
	{
		RestAssured.baseURI = BaseURL;
		
		Response response = given()
		.queryParam("key", Key)
		.queryParam("token", Token)
		.header("Content-Type","application/json")
		
		.when()
		.get("/1/boards/"+ID)	
		
		.then()
		.assertThat().statusCode(200)
		.extract().response();
		
		System.out.println(response.asString());
	}
	
	@Test (priority = 2, enabled = false)
	public void DeleteBoard()
	{
		RestAssured.baseURI = BaseURL;
		
		Response response = given()
		.queryParam("key", Key)
		.queryParam("token", Token)
		.header("Content-Type","application/json")
		
		.when()
		.delete("/1/boards/"+ID)	
		
		.then()
		.assertThat().statusCode(200)
		.extract().response();
		
		System.out.println(response.asString());
	}
}

