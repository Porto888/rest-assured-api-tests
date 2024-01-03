package utilizandoQuery;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class UtlQueryTest {

    @BeforeClass
    public static void setUp(){
        RestAssured.baseURI = "https://restapi.wcaquino.me/v2";

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

    // Abaixo seguem alguns exemplos de como trabalhar utilizando "Query", em testes de APIs.
    @Test
    public void enviarValorViaQuery(){

                given()
                        .log().all()
                .when()
                        .get("/users?format=xml")
                .then()
                        .log().all()
                        .statusCode(200)
                        .contentType(ContentType.XML)
//                        .contentType(ContentType.JSON)
                ;

    }

//    Neste teste iremos parametrizar os valores para enviar as requições via query
    @Test
    public void enviarValorViaQueryParams(){

                given()
                        .queryParam("format", "json")
                        .log().all()
                .when()
                        .get("/users")
                .then()
                        .log().all()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .contentType(containsString("utf-8"))

                ;

    }


    //    Neste teste iremos enviar as requições via header
    @Test
    public void enviarValorViaHeader(){

                given()
                        .accept(ContentType.HTML)
                        .log().all()
                .when()
                        .get("/users")
                .then()
                        .log().all()
                        .statusCode(200)
                        .contentType(ContentType.HTML)

                ;

    }
}
