package utils;

import io.restassured.RestAssured;
import models.Constants;

public class Initializer {
    public static boolean isInitialized = false;

    public static void Initialize(){
        if (!isInitialized){
            RestAssured.baseURI = Constants.BASE_URL;
        }
    }
}
