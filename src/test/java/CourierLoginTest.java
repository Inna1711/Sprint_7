import io.qameta.allure.junit4.DisplayName;
import models.Constants;
import models.courier.create.Input;
import models.courier.create.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static fixtures.CourierHandler.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static utils.Initializer.initialize;

public class CourierLoginTest {

    @BeforeClass
    public static void setUp(){
        initialize();
    }

    @Before
    public void createCourier(){
        Input inputData = new Input(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD, Constants.COURIER_NAME);
        Response response = createCourierHandler(inputData).body().as(Response.class);
        MatcherAssert.assertThat(response, notNullValue());
    }

    @After
    public void deleteCourier(){
        models.courier.login.Input courierCredentials = new models.courier.login.Input(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD);

        models.courier.login.Response response = loginCourier(courierCredentials);
        MatcherAssert.assertThat(response, notNullValue());
        deleteCourierHandler(response.getId());
    }

    @Test
    @DisplayName("check if can login correctly")
    public void testCorrectLogin(){
        models.courier.login.Input loginData = new models.courier.login.Input(Constants.COURIER_LOGIN, Constants.COURIER_PASSWORD);
        models.courier.login.Response response = loginCourier(loginData, HttpStatus.SC_OK);
        assertNull("Message is not null", response.getMessage());
        assertNotEquals("Id shouldn't be 0", 0, response.getId());
    }

    @Test
    @DisplayName("check if can't login correctly without all needed data!")
    public void testIncorrectLogin(){
        models.courier.login.Input input = new models.courier.login.Input(Constants.COURIER_LOGIN, "");
        models.courier.login.Response response = loginCourier(input, HttpStatus.SC_BAD_REQUEST);
        assertEquals("Id should be 0", 0, response.getId());
        assertEquals("Error message is incorrect", Constants.NOT_ENOUGH_DATA_FOR_LOGIN_ERROR, response.getMessage());
    }

    @Test
    @DisplayName("check if can't login with wrong credentials")
    public void testWrongCredentialsLogin(){
        models.courier.login.Input input = new models.courier.login.Input(Constants.COURIER_LOGIN + "Salt", Constants.COURIER_PASSWORD + "Salt");
        models.courier.login.Response response = loginCourier(input, HttpStatus.SC_NOT_FOUND);
        assertEquals("Id should be 0", 0, response.getId());
        assertEquals("Error message is incorrect", Constants.NOT_FOUND_LOGIN_ERROR, response.getMessage());
    }
}
