package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
public class testCase_API_02 {



   @Test(groups = {"API Tests"})
    public void VerifySearchAPI()
    {

   RestAssured.baseURI="https://content-qtripdynamic-qa-backend.azurewebsites.net";
   RestAssured.basePath="/api/v1/cities";
   RequestSpecification httprequest =RestAssured.given().log().all();
   httprequest.queryParam("q","beng");

   Response response = httprequest.request(Method.GET);
   int status = response.getStatusCode();
   Assert.assertEquals(status,200);
   System.out.println(response.body().prettyPeek());

    
    JsonPath  jsonpath = response.jsonPath();

    String description = jsonpath.get("description").toString();

     Assert.assertEquals(description, "[100+ Places]");

      List responseList = response.jsonPath().getList("");

  
      Assert.assertEquals(responseList.size(),  1);
   
    
   File schemaFile = new File("/home/crio-user/workspace/shubhamnakade-ME_API_TESTING_PROJECT/app/src/test/resources/ExpectedJasonSchema.jason");
   JsonSchemaValidator jasonvalidator = JsonSchemaValidator.matchesJsonSchema(schemaFile);
   response.then().assertThat().body(jasonvalidator);

   
    




    }





}
