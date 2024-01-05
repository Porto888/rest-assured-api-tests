package authenticationApiTest.authTest;

import io.restassured.http.ContentType;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class basicAuth {
    @Test
    public void naoAcessarApiSemUsuarioESenha(){
        given()
                .log().all()
                .when()
                .get("http://restapi.wcaquino.me/basicauth")
                .then()
                .log().all()
                .statusCode(401)
        ;
    }

// Abaixo estão exemplificadas algumas maneiras de autenticação dados de acesso, em uma API utilizando Junit
    @Test
    public void realizarAutenticacaoBasica(){
        given()
                .log().all()
                .when()
                .get("http://admin:senha@restapi.wcaquino.me/basicauth")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"))

        ;
    }

    @Test
    public void realizarAutenticacaoBasicaOp02(){
        given()
                .log().all()
                .auth().basic("admin", "senha")
                .when()
                .get("http://restapi.wcaquino.me/basicauth")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"))
        ;
    }

    @Test
    public void realizarAutenticacaoBasicaChallengeOp03(){
        given()
                .log().all()
                .auth().preemptive().basic("admin", "senha")
                .when()
                .get("http://restapi.wcaquino.me/basicauth2")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"))
        ;
    }

    @Test
    public void realizarAutenticacaoViaTokenJwtOp04(){
        Map<String, String> login = new HashMap<String, String>();
        login.put("email", "guilhermepstk@hotmail.com");
        login.put("senha", "158892");

        String token = given()
                .log().all()
                .body(login)
                .contentType(ContentType.JSON)
                .when()
                .post("http://barrigarest.wcaquino.me/signin")
                .then()
                .log().all()
                .statusCode(200)
                .extract().path("token")

                 ;

        given()
                .log().all()
                .header("Authorization", "JWT " + token)
                .contentType(ContentType.JSON)
                .when()
                .get("http://barrigarest.wcaquino.me/contas")
                .then()
                .log().all()
                .statusCode(200)
                .body("nome", hasItem("M Rafael"))

        ;
    }


}
