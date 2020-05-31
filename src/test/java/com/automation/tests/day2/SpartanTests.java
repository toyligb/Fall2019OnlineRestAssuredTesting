package com.automation.tests.day2;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;

public class SpartanTests {

    String BASE_URL = "http://3.90.112.152:8000";

    //URI (Uniform Resource Identification) = URL + URN = http://www.google.com/index.html
    //URL (Uniform Resource Locator) = http://www.google.com
    //URN (Uniform Resource Name) = /index.html

    @Test
    @DisplayName("Get list of all spartans")
    public void getAllSpartans() {
        //401 - unauthorized
        //how ro provide credentials?
        //there are different types of authentication: basic, oauth 1.0, oauth 2.0, api key, bearer token, etc...
        //spartan app requires basic authentication: username and password
        given().
                baseUri(BASE_URL).
                auth().basic("admin", "admin").
        when().
                get("/api/spartans").prettyPeek().
        then().
                statusCode(200);
    }

    @Test
    @DisplayName("Add new spartan")
    public void addSpartanTest() {
        //JSON supports different data types: string, integer, boolean
        String body = "{\"gender\": \"Male\", \"name\": \"Kimay Bu\", \"phone\": 99365505050}";
        //instead of string variable, we can use external JSON file
        //use File class to read JSON and pass it into body
        //provide path to the JSON as a parameter
        File jsonFile = new File(System.getProperty("user.dir") + "/spartan.json");

        //to create new item, we perform POST request
        //contentType(ContentType.JSON) - to tell web service what kind of media type we send
        given().
                contentType(ContentType.JSON).
                auth().basic("admin", "admin").
                body(jsonFile).
                baseUri(BASE_URL).
        when().
                post("/api/spartans").prettyPeek().
        then().
                statusCode(201);
    }

    @Test
    @DisplayName("Delete some spartan and verify that status code is 204")
    public void deleteSpartanTest() {
        // {id} - path parameter
        // YOU CANNOT DELETE SOMETHING TWICE
        // we use delete() method to delete something
        // 204 - No content, most common status code for successful delete action
        // authentication - who you are? you need to tell to the server who you are before getting any data
        // 200 - always after successful GET request
        // 201 - always after successful POST request
        // 204 - always after successful DELETE request
        // 4xx - always after unsuccessful request and it was YOUR FAULT
        given().
                auth().basic("admin", "admin").
                baseUri(BASE_URL).
        when().
                delete("/api/spartans/{id}", 816).prettyPeek().
        then().
                statusCode(204);
    }

}
