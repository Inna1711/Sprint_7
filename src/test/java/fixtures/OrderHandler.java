package fixtures;

import models.Constants;
import models.order.create.Input;
import models.order.list.Output;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class OrderHandler {
    public static models.order.create.Output createOrder(Input orderData){
        return given().
                header("Content-type", "application/json").
                body(orderData).
                post(Constants.ORDERS_ROUTE).
                then().assertThat().statusCode(HttpStatus.SC_CREATED).
                extract().as(models.order.create.Output.class);
    }

    public static Output listOrders(){
        return given().
                header("Content-type", "application/json").
                get(Constants.ORDERS_ROUTE).
                then().assertThat().statusCode(HttpStatus.SC_OK).
                extract().as(Output.class);
    }
}
