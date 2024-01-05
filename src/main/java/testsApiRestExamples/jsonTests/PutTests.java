package testsApiRestExamples.jsonTests;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PutTests {

    @BeforeClass
    public static void setUp(){
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
    public void alterarUsuario() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{ \"name\": \"Porto\", \"age\": 31}")
                .when()
                .put("/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(2))
                .body("name", is("Porto"))


        ;

    }
}
