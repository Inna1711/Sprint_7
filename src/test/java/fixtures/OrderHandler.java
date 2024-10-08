package fixtures;

import models.Constants;
import models.OrderCreate.Input;
import models.OrderList.Output;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class OrderHandler {
    public static models.OrderCreate.Output createOrder(Input orderData){
        return given().
                header("Content-type", "application/json").
                body(orderData).
                post(Constants.ORDERS_ROUTE).
                then().assertThat().statusCode(HttpStatus.SC_CREATED).
                extract().as(models.OrderCreate.Output.class);
    }

    public static Output listOrders(){
        return given().
                header("Content-type", "application/json").
                get(Constants.ORDERS_ROUTE).
                then().assertThat().statusCode(HttpStatus.SC_OK).
                extract().as(models.OrderList.Output.class);
    }
}
