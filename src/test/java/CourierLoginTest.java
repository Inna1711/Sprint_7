import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import models.Constants;
import models.CourierCreate.Input;
import models.CourierCreate.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CourierLoginTest {
    @Before
    public void createCourier(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        Response response = given().
                header("Content-type", "application/json").
                body(new Input(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD, Constants.COURIER_NAME)).
                post("/api/v1/courier").body().as(Response.class);
        MatcherAssert.assertThat(response, notNullValue());
    }

    @After
    public void DeleteCourier(){
        models.CourierLogin.Response response = given().
                header("Content-type", "application/json").
                body(new models.CourierLogin.Input(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD)).
                post("/api/v1/courier/login").body().as(models.CourierLogin.Response.class);

        given().
                header("Content-type", "application/json").
                delete("/api/v1/courier/" + response.getId()).body().as(models.CourierDelete.Response.class);
        MatcherAssert.assertThat(response, notNullValue());
    }

    @Test
    @DisplayName("check if can login correctly")
    public void testCorrectLogin(){
        models.CourierLogin.Response response = given().
                header("Content-type", "application/json").
                body(new models.CourierLogin.Input(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD)).
                post("/api/v1/courier/login")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(models.CourierLogin.Response.class);
        assertNull("Message is not null", response.getMessage());
        assertNotEquals("Id shouldn't be 0", 0, response.getId());
    }

    @Test
    @DisplayName("check if can't login correctly without all needed data!")
    public void testIncorrectLogin(){
        models.CourierLogin.Input input = new models.CourierLogin.Input(Constants.COURIER_LOGIN, "");
        models.CourierLogin.Response response = given().
                header("Content-type", "application/json").
                body(input).
                post("/api/v1/courier/login").
                then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST).
                extract().as(models.CourierLogin.Response.class);
        assertEquals("Id should be 0", 0, response.getId());
        assertEquals("Error message is incorrect", Constants.NOT_ENOUGH_DATA_FOR_LOGIN_ERROR, response.getMessage());
    }

    @Test
    @DisplayName("check if can't login with wrong credentials")
    public void testWrongCredentialsLogin(){
        models.CourierLogin.Response response = given().
                header("Content-type", "application/json").
                body(new models.CourierLogin.Input(Constants.COURIER_LOGIN + "Salt", Constants.COURIER_PASSWORD + "Salt")).
                post("/api/v1/courier/login")
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .extract().as(models.CourierLogin.Response.class);
        assertEquals("Id should be 0", 0, response.getId());
        assertEquals("Error message is incorrect", Constants.NOT_FOUND_LOGIN_ERROR, response.getMessage());
    }
}
