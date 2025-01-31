package apiTests;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.hamcrest.Matchers.aMapWithSize;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("AthenticationController API Testing")
@Feature("Athentication API")
@ExtendWith(AllureJunit5.class)

public class AthenticationControllerApiTest extends TestBase{
    private String baseUrl;
    private String username;
    private String password;

    public AthenticationControllerApiTest(){
        propertyReader config = propertyReader.getInstance();
        this.baseUrl = config.getValue("base_url_authentication_controller");
        this.username = config.getValue("authentication_controller_username");
        this.password = config.getValue("authentication_controller_password");

    }
    @Test
    @Description("We expected a JSON content")
    @AllureId("001")  // Optional unique test case ID
    public void ValidateStatusCodeExpectedcontentJson(){
        given().
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
                when().
                get(baseUrl).
                then().
                assertThat().
                contentType(ContentType.JSON);
    }
    @Test
    @Description("We expect a code 200 as the Payload is correct")
    public void ValidateCodeStateWithPayload(){
        // Build the request body
        String requestBody = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";


        given().
                contentType(ContentType.JSON).
                body(requestBody).
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
         when().
                post(baseUrl).
                then().
                spec(responseSpec);

    }

    @Test
    @Description("We expect 400 as the Payload is wrong")
    public void ValidateFailedCodeStateWithWrongPayload(){
        // Build the request body
        String requestBody = "{\"username\":\"" + username + "\", \"password\":\"" + "well" + "\"}";


        given().
                contentType(ContentType.JSON).
                body(requestBody).
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
                when().
                post(baseUrl).
                then().
                statusCode(400);

    }
@Test
@Description("We expect a code 403 with no payload")

    public void ValidateFailedCodeStateWithNoPayload(){
        // Build the request body
        String requestBody = "{\"username\":\"" + "" + "\", \"password\":\"" + "" + "\"}";


        given().
                contentType(ContentType.JSON).
                body(requestBody).
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
                when().
                post(baseUrl).
                then().
                statusCode(403);

    }
    @Test
    @Description("We expect the body value for Token:string")
    public void ValidateTokenBodyValueEqualString(){
        // Build the request body
        String requestBody = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";


        given().
                contentType(ContentType.JSON).
                body(requestBody).
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
                when().
                post(baseUrl).
                then().
                spec(responseSpec).
                and().
                assertThat().
                body("token", equalTo("string"));

    }
    @Description("Security Check [Size] and No nul token Value is Returned")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void SecurityCheckSizeAndNotNullValueReturn(){
        String requestBody = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";


        given().
                contentType(ContentType.JSON).
                body(requestBody).
                filter(new RequestLoggingFilter()).
                filter(new ResponseLoggingFilter()).
                when().
                post(baseUrl).
                then().
                spec(responseSpec).
                and().
                assertThat().
                body("$", aMapWithSize(1)).
                body("token",notNullValue());
    }


}
