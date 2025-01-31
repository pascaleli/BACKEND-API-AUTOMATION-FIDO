package apiTests;

import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("GameController API Testing")
@Feature("Game API")
@ExtendWith(AllureJunit5.class)
public class GameControllerAPI extends TestBase{
    private String baseUrl;
    private String payload;
    private String  id;

    public GameControllerAPI(){
        propertyReader config = propertyReader.getInstance();
        this.baseUrl = config.getValue("base_url_game_controller");
        this.payload = config.getValue("game_controller_payload");
        this.id = config.getValue("path_attribute_id");
    }

    @Description("given the games controller ; api-video-games-controller")
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
    @Description("given the games controller ; api-video-games-controller")
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

    @Description("given the games controller ; api-video-games-controller")
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

    @Description("Security Check for [Read Only]Post a Game; api-video-games-controller")
    @Test
    public void SecurityCheckNoWriteRight(){
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


    @Description(" Security Check Validate size of Bod | given the games controller ; api-video-games-controller")
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
