package apiTests;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("GameController API Testing")
@Feature("Game API")
@ExtendWith(AllureJunit5.class)
public class GameControllerApiTest extends TestBase{
    private String baseUrl;
    private String payload;
    private String  id;

    public GameControllerApiTest(){
        propertyReader config = propertyReader.getInstance();
        this.baseUrl = config.getValue("base_url_game_controller");
        this.payload = config.getValue("game_controller_payload");
        this.id = config.getValue("path_attribute_id");
    }

    @Description("Validate code status is 200 and content type is JSON")
    @Test
    public void ValidateCodeStateAndExpectedcontent(){
        given().
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
                when().
                get(baseUrl).
                then().
                spec(responseSpec);
    }
    @Description("Validate API returns list of objects and their values")
    @Test
    public void ValidateResponseAllValue(){
        given().
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
                when().
                get(baseUrl).
                then().
                body("[0].id", equalTo(1)).
                body("[0].name", equalTo("Resident Evil 4")).
                body("[0].releaseDate", containsString("2005")).
                body("[0].reviewScore", equalTo(85)).
                body("[0].category", equalTo("Shooter")).
                body("[0].rating", equalTo("Universal"));
    }

    @Description("Fetch a specific resource and validate some values")
    @Test
    public void ValidateResponseSpecificValue(){
        given().
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
                when().
                get(baseUrl+"/"+id).
                then().
                assertThat().
                body("id", equalTo(8)).
                body("name", equalTo("SimCity 2000"));
    }

    @Description("The API is read only , so validating this permission")
    @Test
    public void SecurityCheckNoWritePermission(){
        given().
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
                body(payload).
                when().
                post(baseUrl).
                then().
                assertThat().
                statusCode(403).
                     body("error", equalTo("Forbidden"));   // Validate error message



    }


    @Description(" Security Check Validate size of Bod | given the games controller ")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void SecurityCheckSizeOfBodyReturn(){
        given().
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
         when().
                get(baseUrl).
         then().
                assertThat().
                body("",hasSize(10));
    }


}
