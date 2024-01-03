package verbosRest.xmlTests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.xml.element.Node;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetXmlValidations {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification respSpec;

    //    Abaixo temos exemplos básicos de como realizar  validações em um arquivo ".XML"
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
    public void validandoObjetoNumArquivoXML() {

        given()
                .spec(reqSpec)
                .when()
                .get("usersXML/3")
                .then()
//                .statusCode(200)
                .spec(respSpec)
                .rootPath("user")
                .body("name", is("Ana Julia"))
                .body("@id", is("3"))

                .rootPath("user.filhos")
                .body("name.size()", is(2))

// Validando o atributo "filhos" e suas respectivas posições na lista

                .detachRootPath("filhos")
                .body("filhos.name[0]", is("Zezinho"))
                .body("filhos.name[1]", is("Luizinho"))

// Validando o conteúdo do atributo "filhos"

                .appendRootPath("filhos")
                .body("name", hasItem("Luizinho"))
                .body("name", hasItems("Luizinho", "Zezinho"))
        ;
    }

    @Test
    public void validarArqXML() {
        given()
                .spec(reqSpec)
                .when()
                .get("usersXML")
                .then()
                .spec(respSpec)
                .body("users.user.size()", is(3))
                .body("users.user.findAll{it.age.toInteger() <= 25}.size()", is(2))
                .body("users.user.@id", hasItems("1", "2", "3"))
                .body("users.user.find{it.age == 30}.name", is("João da Silva"))
                .body("users.user.filhos.size()", is(1))
                .body("users.user.name.findAll{it.toString().startsWith('Maria')}.collect{it.toString().toUpperCase()}", is("MARIA JOAQUINA"))

        ;
    }

    @Test
    public void validacoesUtilizandoXmlPath(){
        given()
                .when()
                .get("http://restapi.wcaquino.me/usersXML")
                .then()
                .body(hasXPath("count(/users/user)", is("3")))
                .body(hasXPath("/users/user[@id = '1']"))
                .body(hasXPath("//user[age < 24]/name", is("Ana Julia")))
                .body(hasXPath("//name[text() = 'Maria Joaquina']"))
                .body(hasXPath("//name[text() = 'Ana Julia']/following-sibling::filhos", allOf(containsString("Zezinho"), containsString("Luizinho"))))
        ;

    }




    //    Aqui estão exemplificadas algumas verificações e asserções um pouco mais avançadas, utilizando 'XMLpath + Java"

    @Test
    public void realizandoTestesMaisAvancadosComXmlPath(){
        Object path = given()
                .spec(reqSpec)
                .when()
                .get("usersXML")
                .then()
                .spec(respSpec)
                .extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}")

                ;
        System.out.println(path.toString());


        String name = given()
        .spec(reqSpec)
                .when()
                .get("usersXML")
                .then()
                .spec(respSpec)
                .extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}")

                ;
        Assert.assertEquals("Maria Joaquina".toUpperCase(), name.toUpperCase());

        ArrayList<Node> nomes = given()
                .spec(reqSpec)
                .when()
                .get("usersXML")
                .then()
                .spec(respSpec)
                .extract().path("users.user.name.findAll{it.toString().contains('n')}")

                ;
        Assert.assertEquals(2, nomes.size());
        Assert.assertEquals("Maria Joaquina".toUpperCase(), nomes.get(0).toString().toUpperCase());
        Assert.assertTrue("Ana Julia".equalsIgnoreCase(nomes.get(1).toString()));
        System.out.println(nomes);
    }


}
