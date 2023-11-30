package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
  
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.UUID;

public class testCase_API_03 {

    @Test(groups = {"API Tests"})
    public void Trip_Reservation ()
    {
        RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath = "/api/v1/register";
        RequestSpecification httprequest = RestAssured.given() ;
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
        // System.out.println(response.getBody().prettyPeek());
        int StatusCode = response.getStatusCode();
        Assert.assertEquals(StatusCode, 201, "Failed to validate status code 201 for registration");

        RestAssured.basePath ="/api/v1/login";
        httprequest = RestAssured.given() ;
        
        js.remove("confirmpassword");

        httprequest.header("Content-Type", "application/json");
        httprequest.body(js.toString());
         response = httprequest.request(Method.POST);
        //  System.out.println(response.getBody().prettyPeek());
         StatusCode = response.getStatusCode();
        Assert.assertEquals(StatusCode, 201, "Failed to validate status code 201 for registration");

         JsonPath jasonpath=  response.jsonPath();

         String token =  jasonpath.get("data.token").toString();
         String id = jasonpath.get("data.id").toString();


           JSONObject js1 = new JSONObject();
           Random random = new Random() ;

           String name = "shubham"+random.nextInt();
         js1.put("userId", id);
         js1.put("name", name);
         js1.put("date","2023-15-12");
         js1.put("person","1");
         js1.put("adventure","2447910730");

          RestAssured.basePath = "/api/v1/reservations/new";
          httprequest = RestAssured.given().log().all() ;
          httprequest.header("Content-Type", "application/json");
          httprequest.queryParam("q","beng");
          httprequest.header("Authorization", "Bearer " + token );
          httprequest.body(js1.toString());

          response = httprequest.request(Method.POST);

          StatusCode =  response.getStatusCode() ;
          System.out.println(StatusCode);
          System.out.println(response.prettyPeek());


          RestAssured.basePath = "/api/v1/reservations" ;
          httprequest = RestAssured.given().log().all() ;
          httprequest.queryParam("id",id);
          httprequest.header("Authorization", "Bearer " + token );

          response = httprequest.request(Method.GET);

          StatusCode =  response.getStatusCode() ;
          System.out.println(response.prettyPeek());
          System.out.println(StatusCode);







         
    }
}
