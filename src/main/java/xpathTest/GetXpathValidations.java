package xpathTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetXpathValidations {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification respSpec;

    @BeforeClass
    public static void setUp(){
        RestAssured.baseURI = "http://restapi.wcaquino.me";

        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.log(LogDetail.ALL);
        reqSpec = reqBuilder.build();

        ResponseSpecBuilder respBuilder = new ResponseSpecBuilder();
        respBuilder.expectStatusCode(200);
        respSpec = respBuilder.build();

        RestAssured.requestSpecification = reqSpec;
        RestAssured.responseSpecification = respSpec;
    }

    @Test
    public void validacoesUrtilizandoXpath(){
        given()
                .when()
                .get("http://restapi.wcaquino.me/usersXML")
                .then()
//                .statusCode(200)
                .body(hasXPath("count(/users/user)", is("3")))
                .body(hasXPath("/users/user[@id = '1']"))
                .body(hasXPath("//user[age < 24]/name", is("Ana Julia")))
                .body(hasXPath("//name[text() = 'Maria Joaquina']"))
                .body(hasXPath("//name[text() = 'Ana Julia']/following-sibling::filhos", allOf(containsString("Zezinho"), containsString("Luizinho"))))
        ;


    }

}
