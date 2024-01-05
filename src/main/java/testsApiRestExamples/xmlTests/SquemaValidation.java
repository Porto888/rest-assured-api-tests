package testsApiRestExamples.xmlTests;

import io.restassured.RestAssured;
import io.restassured.matcher.RestAssuredMatchers;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import static io.restassured.RestAssured.given;

public class SquemaValidation {

    @BeforeClass
    public static void setUp(){
        RestAssured.baseURI = "http://restapi.wcaquino.me";

    }
@Test
    public void validandoSchemaXML(){
    given()
            .log().all()
            .when()
            .get("/usersXML")
            .then()
            .log().all()
            .statusCode(200)
            .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))

    ;

    }

    @Test(expected = SAXParseException.class )
    public void naoDeveValidarSchemaXMLInvalido(){
    given()
            .log().all()
            .when()
            .get("/invalidUsersXML")
            .then()
            .log().all()
            .statusCode(200)
            .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))

    ;

    }

}
