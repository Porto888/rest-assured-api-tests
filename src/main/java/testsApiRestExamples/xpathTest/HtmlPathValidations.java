package testsApiRestExamples.xpathTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HtmlPathValidations {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification respSpec;

    @BeforeClass
    public static void setUp(){
        RestAssured.baseURI = "http://restapi.wcaquino.me/";
//
//        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
//        reqBuilder.log(LogDetail.ALL);
//        reqSpec = reqBuilder.build();
//
//        ResponseSpecBuilder respBuilder = new ResponseSpecBuilder();
//        respBuilder.expectStatusCode(200);
//        respSpec = respBuilder.build();
//
//        RestAssured.requestSpecification = reqSpec;
//        RestAssured.responseSpecification = respSpec;
    }

    @Test
    public void buscaUtilizandoHtmlPath(){
        given()
                .log().all()
                .when()
                .get("/v2/users")
                .then()
                .log().all()
                .contentType(ContentType.HTML)
                .body("html.body.div.table.tbody.tr.size()", is(3))

//  Este é um exemplo de como utilizar o "appendRootPath", para melhorar a manutenção e leitura do código
//  melhorando o dimanismo do processo

                .appendRootPath("html.body.div.table.tbody")
                .body("tr.find{it.toString().startsWith('2')}.td[1]", is("Maria Joaquina"))
        ;

    }
    @Test
    public void buscaUtilizandoXpath(){
        given()
                .log().all()
                .when()
                .get("/v2/users?format=clean")
                .then()
                .log().all()
                .contentType(ContentType.HTML)
                .body(hasXPath("count(//table/tr)", is("4")))
                .body(hasXPath("//td[text() = '2']/../td[2]", is("Maria Joaquina")))
        ;

    }
}
