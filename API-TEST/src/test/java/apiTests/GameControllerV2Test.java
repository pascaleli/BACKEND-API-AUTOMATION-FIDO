package apiTests;


import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("GameControllerV2 API Testing")
@Feature("Game-V2 API")
@ExtendWith(AllureJunit5.class)
public class GameControllerV2Test extends TestBase {
    private String baseUrl;
    private String payload;
    private String  id;

    public GameControllerV2Test(){
        propertyReader config = propertyReader.getInstance();
        this.baseUrl = config.getValue("base_url_game_controllerV2");
        this.payload = config.getValue("game_controller_V2");
        this.id = config.getValue("controller_V2_path_attribute_id");
    }

    @Description("Validate the StatusCode & Content Type XML")
    @Test
    public void ValidateV2CodeStateAndXMLV2(){
        given().
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
                when().
                get(baseUrl).
                then().
                spec(responseSpecXML);
    }

    @Description("Validate the StatusCode & Content Type JSON")
    @Test
    public void ValidateV2CodeStateAndJSONL(){
        given().
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
                when().
                get(baseUrl).
                then().
                spec(responseSpec);
    }

    @Description("Validate Response Value for All elements returned in the Object")
    @Test
    public void ValidateV2ResponseAllValue(){
        given().
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
                when().
                get(baseUrl).
                then().
                body("[1].id", equalTo(2)).
                body("[1].name", equalTo("Gran Turismo 3")).
                body("[1].releaseDate", containsString("2001")).
                body("[1].reviewScore", equalTo(91)).
                body("[1].category", equalTo("Driving")).
                body("[1].rating", equalTo("Universal"));
    }

    @Description("Validate the API does not allow for Write permission")
    @Test
    public void SecurityCheckNoWritePermissionPayLoadV2(){
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

    @Description("Validate specific resource from the API")
    @Test
    public void ValidateResponseSpecificValueV2(){
        given().
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
        when().
                get(baseUrl+"/"+id).
        then().
                assertThat().
                body("id", equalTo(6)).
                body("name", equalTo("Doom"));
    }
}
