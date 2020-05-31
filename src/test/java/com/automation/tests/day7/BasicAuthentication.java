package com.automation.tests.day7;

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class BasicAuthentication {

    @Test
    public void spartanAuthentication() {
        //in the given part, we provide request specifications
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        given().
                auth().basic("admin", "admin").
                when().
                get("/spartans").prettyPeek().
                then().
                statusCode(200);
    }

    @Test
    public void authorizationTest() {
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        Spartan spartan = new Spartan("Oraz", "Male", 654854548514L);
        given().
                auth().basic("user", "user").
                body(spartan).
                contentType(ContentType.JSON). //contenttype hokman gorkezmeli, server bulashmaz yali
        when().
                post("/spartans").prettyPeek().
        then().
                statusCode(403);
        /**
         * user - doesn't have rights to add, delete or edit users. Only read.
         * admin - has a permission to add new users.
         * Status code 403 - Forbidden access. You logged in, but you are trying to do something that are not allowed.
         * Authentication problem - you didn't login
         * Authorization problem - you logged in but cannot do some actions
         */
    }

    @Test
    public void authenticationTest() {
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        //if we don't provide credentials, we must get 401 status code
        get("/spartans").prettyPeek().then().statusCode(401);
    }

    @Test
    public void authenticationTest2() {
        baseURI = "http://practice.cybertekschool.com";

        given().
                auth().basic("admin", "admin").
        when().
                get("/basic_auth").prettyPeek().
        then().
                statusCode(200).
                contentType(ContentType.HTML);
    }

}
