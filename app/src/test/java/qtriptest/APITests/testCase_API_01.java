package qtriptest.APITests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;



public class testCase_API_01 {



    @Test(groups = {"API Tests"})
    public void Register_Userand_Login() {
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath = "/api/v1/register";
        RequestSpecification httprequest = RestAssured.given().log().all();

        String uuid = UUID.randomUUID().toString();
        String randomemail = "user_" + uuid + "@example.com";
        JSONObject js = new JSONObject();
        // Map<Object, Object> hm = new HashMap<>();
        js.put("email", randomemail);
        js.put("password", "Password");
        js.put("confirmpassword", "Password");
        httprequest.header("Content-Type", "application/json");
        httprequest.body(js.toString());
        Response response = httprequest.request(Method.POST);
        System.out.println(response.getBody().prettyPeek());
        int registrationStatusCode = response.getStatusCode();
        Assert.assertEquals(registrationStatusCode, 201, "Failed to validate status code 201 for registration");

        RestAssured.basePath ="/api/v1/login";
        httprequest = RestAssured.given().log().all();
        
        js.remove("confirmpassword");

        httprequest.header("Content-Type", "application/json");
        httprequest.body(js.toString());
         response = httprequest.request(Method.POST);
         System.out.println(response.getBody().prettyPeek());
         registrationStatusCode = response.getStatusCode();
        Assert.assertEquals(registrationStatusCode, 201, "Failed to validate status code 201 for registration");

JsonPath jsonpath = response.jsonPath() ;
String  token =jsonpath.get("data.token").toString();

System.out.println(token);


    }


    


}
