import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BasicsValidations {
    @Test
    public void olaMundoTest(){

            Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
            System.out.println(response.getBody().asString().equals("Ola Mundo!"));
            ValidatableResponse validacao = response.then();
            validacao.statusCode(200);
            Assert.assertTrue(response.statusCode() != 404);


    }

//    @Test
//    public void olaMundoApiTest(){
//
//            Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
//            System.out.println(response.getBody().asString().equals("Ola Mundo  !"));
//            Assert.assertTrue(response.statusCode() == 200);
//
//
//            get("http://restapi.wcaquino.me/ola").then().statusCode(201);
//
//
//    }

    @Test
    public void olaMundoApiTest(){

        given()
                .when()
                .get("http://restapi.wcaquino.me/ola")
                .then()
                .statusCode(200);


    }
    @Test
    public void validarBody(){
        given()
                .when()
                .get("http://restapi.wcaquino.me/ola")
                .then()
                .statusCode(200)
//                .body(is("Ola Mundo!"));
                .body(containsString("Mundo"))
                .body(is(notNullValue()));

    }

}
