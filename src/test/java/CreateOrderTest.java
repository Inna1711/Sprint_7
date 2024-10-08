import io.qameta.allure.junit4.DisplayName;
import models.Constants;
import models.OrderCreate.Input;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static fixtures.OrderHandler.createOrder;
import static org.junit.Assert.assertNotEquals;
import static utils.Initializer.Initialize;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    String[] colors;

    @BeforeClass
    public static void initialize(){
        Initialize();
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
    @DisplayName("create a correct order")
    public void testOrdersCreation(){
        Input inputData = new Input(colors);
        models.OrderCreate.Output response = createOrder(inputData);
        assertNotEquals("Track shouldn't be 0", 0, response.getTrack());
    }
}
