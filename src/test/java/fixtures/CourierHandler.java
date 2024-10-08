package fixtures;

import models.Constants;
import models.CourierCreate.Input;

import static io.restassured.RestAssured.given;

public class CourierHandler {
    public static io.restassured.response.Response createCourierHandler(Input courierData){
        return  given().
                header("Content-type", "application/json").
                body(courierData).
                post(Constants.COURIER_ROUTE);
    }

    public static models.CourierLogin.Response loginCourier(models.CourierLogin.Input courierLoginData){
        return given().
                header("Content-type", "application/json").
                body(courierLoginData).
                post(Constants.LOGIN_ROUTE).body().as(models.CourierLogin.Response.class);
    }

    public static models.CourierLogin.Response loginCourier(models.CourierLogin.Input courierLoginData, int statusCode){
        return given().
                header("Content-type", "application/json").
                body(courierLoginData).
                post(Constants.LOGIN_ROUTE)
                .then().assertThat().statusCode(statusCode)
                .extract().as(models.CourierLogin.Response.class);
    }

    public static models.CourierDelete.Response deleteCourierHandler(int courierId){
        return given().
                header("Content-type", "application/json").
                delete(Constants.COURIER_ROUTE + courierId).body().as(models.CourierDelete.Response.class);
    }
}
