import io.qameta.allure.junit4.DisplayName;
import models.order.list.Output;
import org.junit.BeforeClass;
import org.junit.Test;

import static fixtures.OrderHandler.listOrders;
import static org.junit.Assert.assertNotEquals;
import static utils.Initializer.initialize;

public class GetOrderListTest {
    @BeforeClass
    public static void setUp(){
        initialize();
    }

    @Test
    @DisplayName("check if got a list of orders")
    public void testGetOrdersList(){
        Output response = listOrders();
        assertNotEquals("Check if we got a list of orders", 0, response.getOrders().length);
    }
}
