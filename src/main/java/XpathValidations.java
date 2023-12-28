import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class XpathValidations {

    @Test
    public void validacoesUrtilizandoXpath(){
        given()
                .when()
                .get("http://restapi.wcaquino.me/usersXML")
                .then()
                .statusCode(200)
                .body(hasXPath("count(/users/user)", is("3")))
                .body(hasXPath("/users/user[@id = '1']"))
                .body(hasXPath("//user[age < 24]/name", is("Ana Julia")))
                .body(hasXPath("//name[text() = 'Maria Joaquina']"))
                .body(hasXPath("//name[text() = 'Ana Julia']/following-sibling::filhos", allOf(containsString("Zezinho"), containsString("Luizinho"))))
        ;


    }

}
