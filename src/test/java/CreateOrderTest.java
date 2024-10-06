import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import models.Constants;
import models.OrderCreate.Input;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    String[] colors;

    @Before
    public void initialize(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }


    @Parameterized.Parameters
    public static String[][][] getColorsInit(){
        return new String[][][]{
                {
                        {
                                Constants.ORDER_BLACK_COLOR,
                        }

                },
                {
                        {
                                Constants.ORDER_GREY_COLOR
                        }
                },
                {
                        {
                                Constants.ORDER_GREY_COLOR, Constants.ORDER_BLACK_COLOR
                        }
                },
                {
                        {}
                }
        };
    }

    public CreateOrderTest(String[] colors){
        this.colors = colors;
    }

    @Test
    @DisplayName("create an correct order")
    public void testOrdersCreation(){
        Input inputData = new Input(colors);
        models.OrderCreate.Output response = given().
                header("Content-type", "application/json").
                body(inputData).
                post("/api/v1/orders").
                then().assertThat().statusCode(HttpStatus.SC_CREATED).
                extract().as(models.OrderCreate.Output.class);
        assertNotEquals("Track shouldn't be 0", 0, response.getTrack());
    }
}
