package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class testCase_API_04 {

@Test(groups ={"API Tests"})
    public void Reregistered_withSameCredential ()
    {
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


        httprequest = RestAssured.given().log().all();
        httprequest.header("Content-Type", "application/json");
        httprequest.body(js.toString());

          response = httprequest.request(Method.POST);
           System.out.println(response.getBody().prettyPeek());
          registrationStatusCode = response.getStatusCode();
        Assert.assertEquals(registrationStatusCode, 400, "Failed to validate status code 400 for Reregistration");

         JsonPath jason = response.jsonPath();
         String message = jason.get("message").toString();
         Assert.assertEquals(message,"Email already exists");

    }
    }

  

