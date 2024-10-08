import io.qameta.allure.junit4.DisplayName;
import models.Constants;
import models.CourierCreate.Input;
import models.CourierCreate.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static fixtures.CourierHandler.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static utils.Initializer.Initialize;

public class CourierLoginTest {

    @BeforeClass
    public static void initialize(){
        Initialize();
    }

    @Before
    public void createCourier(){
        Input inputData = new Input(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD, Constants.COURIER_NAME);
        Response response = createCourierHandler(inputData).body().as(Response.class);
        MatcherAssert.assertThat(response, notNullValue());
    }

    @After
    public void deleteCourier(){
        models.CourierLogin.Input courierCredentials = new models.CourierLogin.Input(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD);

        models.CourierLogin.Response response = loginCourier(courierCredentials);
        MatcherAssert.assertThat(response, notNullValue());
        deleteCourierHandler(response.getId());
    }

    @Test
    @DisplayName("check if can login correctly")
    public void testCorrectLogin(){
        models.CourierLogin.Input loginData = new models.CourierLogin.Input(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD);
        models.CourierLogin.Response response = loginCourier(loginData, HttpStatus.SC_OK);
        assertNull("Message is not null", response.getMessage());
        assertNotEquals("Id shouldn't be 0", 0, response.getId());
    }

    @Test
    @DisplayName("check if can't login correctly without all needed data!")
    public void testIncorrectLogin(){
        models.CourierLogin.Input input = new models.CourierLogin.Input(Constants.COURIER_LOGIN, "");
        models.CourierLogin.Response response = loginCourier(input, HttpStatus.SC_BAD_REQUEST);
        assertEquals("Id should be 0", 0, response.getId());
        assertEquals("Error message is incorrect", Constants.NOT_ENOUGH_DATA_FOR_LOGIN_ERROR, response.getMessage());
    }

    @Test
    @DisplayName("check if can't login with wrong credentials")
    public void testWrongCredentialsLogin(){
        models.CourierLogin.Input input = new models.CourierLogin.Input(Constants.COURIER_LOGIN + "Salt", Constants.COURIER_PASSWORD + "Salt");
        models.CourierLogin.Response response = loginCourier(input, HttpStatus.SC_NOT_FOUND);
        assertEquals("Id should be 0", 0, response.getId());
        assertEquals("Error message is incorrect", Constants.NOT_FOUND_LOGIN_ERROR, response.getMessage());
    }
}
