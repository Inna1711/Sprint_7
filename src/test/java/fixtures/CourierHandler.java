package fixtures;

import models.Constants;
import models.courier.create.Input;
import models.courier.delete.Response;

import static io.restassured.RestAssured.given;

public class CourierHandler {
    public static io.restassured.response.Response createCourierHandler(Input courierData){
        return  given().
                header("Content-type", "application/json").
                body(courierData).
                post(Constants.COURIER_ROUTE);
    }

    public static models.courier.login.Response loginCourier(models.courier.login.Input courierLoginData){
        return given().
                header("Content-type", "application/json").
                body(courierLoginData).
                post(Constants.LOGIN_ROUTE).body().as(models.courier.login.Response.class);
    }

    public static models.courier.login.Response loginCourier(models.courier.login.Input courierLoginData, int statusCode){
        return given().
                header("Content-type", "application/json").
                body(courierLoginData).
                post(Constants.LOGIN_ROUTE)
                .then().assertThat().statusCode(statusCode)
                .extract().as(models.courier.login.Response.class);
    }

    public static Response deleteCourierHandler(int courierId){
        return given().
                header("Content-type", "application/json").
                delete(Constants.COURIER_ROUTE + courierId).body().as(Response.class);
    }
}
