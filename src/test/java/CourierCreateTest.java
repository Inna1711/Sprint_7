import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import models.Constants;
import models.CourierCreate.Input;
import models.CourierCreate.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class CourierCreateTest
{
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step("Send a login request as courier")
    private int getCourierId(models.CourierLogin.Input courierLoginData){
        models.CourierLogin.Response response = given().
                header("Content-type", "application/json").
                body(courierLoginData).
                post("/api/v1/courier/login").body().as(models.CourierLogin.Response.class);
        assertNull("Message should be empty!", response.getMessage());
        assertNotEquals("Message id shouldn't be 0", 0, response.getId());
        return response.getId();
    }

    @Step("Send a create request for Courier")
    private Response createCourier(Input courierData){
        Response response = given().
                header("Content-type", "application/json").
                body(courierData).
                post("/api/v1/courier").body().as(Response.class);
        MatcherAssert.assertThat(response, notNullValue());
        return response;
    }

    @Step("Send a de request for Courier")
    private models.CourierDelete.Response deleteCourier(int courierId){
        models.CourierDelete.Response response = given().
                header("Content-type", "application/json").
                delete("/api/v1/courier/" + courierId).body().as(models.CourierDelete.Response.class);
        MatcherAssert.assertThat(response, notNullValue());
        return response;
    }

    @Step
    private void cleanupCourier(String login, String password){
        var courierId = getCourierId(new models.CourierLogin.Input(login, password));
        var deleteStatus = deleteCourier(courierId);
        assertTrue("Courier delete failed ", deleteStatus.isOk());
        assertNull("Message should be null", deleteStatus.getMessage());
    }

    @Test
    @Description("Successful create courier test")
    public void createCourierTest(){
        Input courierCreateData = new Input(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD, Constants.COURIER_NAME);
        Response response = createCourier(courierCreateData);
        assertTrue("Courier is not created!", response.getOk());
        cleanupCourier(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD);
    }
}
