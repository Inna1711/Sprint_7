import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

public class GetOrderListTest {
    @Before
    public void initialize(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("check if got a list of orders")
    public void testGetOrdersList(){
        models.OrderList.Output response = given().
                header("Content-type", "application/json").
                get("/api/v1/orders").
                then().assertThat().statusCode(HttpStatus.SC_OK).
                extract().as(models.OrderList.Output.class);
        assertNotNull("Check if we got a list of orders", response.getOrders());
    }
}
