import io.qameta.allure.junit4.DisplayName;
import org.junit.BeforeClass;
import org.junit.Test;

import static fixtures.OrderHandler.listOrders;
import static org.junit.Assert.assertNotEquals;
import static utils.Initializer.Initialize;

public class GetOrderListTest {
    @BeforeClass
    public static void initialize(){
        Initialize();
    }

    @Test
    @DisplayName("check if got a list of orders")
    public void testGetOrdersList(){
        models.OrderList.Output response = listOrders();
        assertNotEquals("Check if we got a list of orders", 0, response.getOrders().length);
    }
}
