import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import models.Constants;
import models.CourierCreate.Input;
import models.CourierCreate.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static fixtures.CourierHandler.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import static utils.Initializer.Initialize;

public class CourierCreateTest
{
    @BeforeClass
    public static void setUp() {
        Initialize();
    }

    @Step("Send a login request as courier")
    private int getCourierId(models.CourierLogin.Input courierLoginData){
        models.CourierLogin.Response response = loginCourier(courierLoginData);

        return response.getId();
    }

    @Step("Send a create request for Courier")
    private Response createCourier(Input courierData, int statusCode){
        io.restassured.response.Response apiResponse = createCourierHandler(courierData);
        Response response = apiResponse.then().assertThat().statusCode(statusCode).extract().as(Response.class);
        MatcherAssert.assertThat(response, notNullValue());
        return response;
    }

    @Step("Send a delete request for Courier")
    private void cleanupCourier(String login, String password){
        var courierId = getCourierId(new models.CourierLogin.Input(login, password));
        var response = deleteCourierHandler(courierId);
        MatcherAssert.assertThat(response, notNullValue());
    }

    @Test
    @DisplayName("successfully create a courier")
    @Description("Successful create courier test")
    public void createCourierTest(){
        Input courierCreateData = new Input(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD, Constants.COURIER_NAME);
        Response response = createCourier(courierCreateData, HttpStatus.SC_CREATED);
        assertTrue("Courier is not created!", response.getOk());
        assertNull("Message should be empty!", response.getMessage());
    }

    @Test
    @DisplayName("try to create a courier with less params than expected!")
    @Description("Test create with less params")
    public void failCreateCourier(){
        Input courierCreateData = new Input();
        courierCreateData.setLogin(Constants.COURIER_LOGIN);
        courierCreateData.setFirstName(Constants.COURIER_NAME);
        Response response = createCourier(courierCreateData, HttpStatus.SC_BAD_REQUEST);
        assertEquals("Error message is not correct!", Constants.NOT_ENOUGH_DATA_ERROR,response.getMessage());
        assertFalse("Courier shouldn't be created!", response.getOk());
    }

    @Test
    @DisplayName("try to create a courier duplicate")
    @Description("Test create courier for second time")
    public void createCourierTwice(){
        Input courierCreateData = new Input(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD, Constants.COURIER_NAME);
        Response response = createCourier(courierCreateData, HttpStatus.SC_CREATED);
        assertTrue("Courier is not created!", response.getOk());
        assertNull("Message should be empty!", response.getMessage());
        Response failResponse = createCourier(courierCreateData, HttpStatus.SC_CONFLICT);
        assertFalse("Courier shouldn't be created!", failResponse.getOk());
        assertEquals("Error message is not valid!", Constants.DUPLICATE_ERROR, failResponse.getMessage());
    }

    @After
    public void cleanup(){
        cleanupCourier(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD);
    }
}
