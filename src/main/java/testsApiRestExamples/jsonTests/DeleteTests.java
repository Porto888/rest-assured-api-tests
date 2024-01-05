package testsApiRestExamples.jsonTests;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.is;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class DeleteTests {
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
    public void deletandoUsuario(){

        given()
                .log().all()
                .contentType("application/json")
                .when()
                .delete("/users/1")
                .then()
                .log().all()
                .statusCode(204)
        ;


    }

    @Test
    public void deletandoUsuarioInexistente(){

        given()
                .log().all()
                .contentType("application/json")
                .when()
                .delete("/users/5")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Registro inexistente"))
        ;


    }
}
