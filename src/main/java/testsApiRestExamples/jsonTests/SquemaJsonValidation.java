package testsApiRestExamples.jsonTests;

import io.restassured.RestAssured;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import static io.restassured.RestAssured.given;

public class SquemaJsonValidation {

    @BeforeClass
    public static void setUp(){
        RestAssured.baseURI = "http://restapi.wcaquino.me";

    }
@Test
    public void validandoSchemaJSON(){
    given()
            .log().all()
            .when()
            .get("/users")
            .then()
            .log().all()
            .statusCode(200)
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json"))

    ;

    }

    @Test(expected = SAXParseException.class ) 
    public void naoDeveValidarSchemaXMLInvalido(){
    given()
            .log().all()
            .when()
            .get("/invalidUsersJSON")
            .then()
            .log().all()
            .statusCode(404)
            .body(RestAssuredMatchers.matchesXsdInClasspath("users.json"))

    ;

    }

}
