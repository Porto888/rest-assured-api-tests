package verbosRest.xmlTests;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class PutXmlValidations {
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "https://restapi.wcaquino.me";

//        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
//        reqBuilder.log(LogDetail.ALL);
//        reqSpec = reqBuilder.build();
//
//        ResponseSpecBuilder respBuilder = new ResponseSpecBuilder();
//        respBuilder.expectStatusCode(201);
//        respSpec = respBuilder.build();
//
//        RestAssured.requestSpecification = reqSpec;
//        RestAssured.responseSpecification = respSpec;
    }

    @Test
    public void editandoUsuario() {
        given()
                .log().all()
                .contentType("application/xml")
                .body("<user>" +
                        "<name>Rafael Porto</name>" +
                        "<age>55</age>" +
                        "</user>")
                .when()
                .put("/usersXML")
                .then()
                .log().all()
                .statusCode(200)
                .body("user.@id", is(0))
                .body("user.name", is("Rafael Porto"))


        ;

    }
}
