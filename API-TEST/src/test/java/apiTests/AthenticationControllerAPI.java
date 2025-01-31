package apiTests;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.hamcrest.Matchers.aMapWithSize;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("AthenticationController API Testing")
@Feature("Athentication API")
@ExtendWith(AllureJunit5.class)

public class AthenticationControllerAPI  extends TestBase{
    private String baseUrl;
    private String username;
    private String password;

    public AthenticationControllerAPI(){
        propertyReader config = propertyReader.getInstance();
        this.baseUrl = config.getValue("base_url_authentication_controller");
        this.username = config.getValue("authentication_controller_username");
        this.password = config.getValue("authentication_controller_password");

    }
    @Test
    @Description("api-authentication-controller\n" +
            "Api Authentication Controller")
    @AllureId("001")  // Optional unique test case ID
    public void ValidateCodeStateExpectedcontent(){
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
    public void ValidateTokenValueEqualString(){
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
    @Description("Security Check [Size] and No nul token Value Returned")
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
