package jsonTests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PostJsonValidations {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification respSpec;

    @BeforeClass
    public static void setUp(){
        RestAssured.baseURI = "https://restapi.wcaquino.me";

        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.log(LogDetail.ALL);
        reqSpec = reqBuilder.build();

        ResponseSpecBuilder respBuilder = new ResponseSpecBuilder();
        respBuilder.expectStatusCode(201);
        respSpec = respBuilder.build();

        RestAssured.requestSpecification = reqSpec;
        RestAssured.responseSpecification = respSpec;
    }
    @Test
    public void salvarUsuario (){
        given()
                .contentType("application/json")
                .body("{ \"name\": \"Porto\", \"age\": 31}")
                .when()
                .post("/users")
                .then()
                .body("id", is(notNullValue()))
                .body("name", is("Porto"))



        ;


    }


}
