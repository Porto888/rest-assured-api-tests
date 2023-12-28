import groovyjarjarantlr4.v4.runtime.tree.TerminalNodeImpl;
import io.restassured.path.xml.element.Node;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class XmlValidations {

    //    Abaixo temos exemplos básicos de como realizar  validações em um arquivo ".XML"
    @Test
    public void validandoObjetoNumArquivoXML() {
        given()
                .when()
                .get("http://restapi.wcaquino.me/usersXML/3")
                .then()
                .statusCode(200)

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
                .when()
                .get("http://restapi.wcaquino.me/usersXML")
                .then()
                .statusCode(200)
                .body("users.user.size()", is(3))
                .body("users.user.findAll{it.age.toInteger() <= 25}.size()", is(2))
                .body("users.user.@id", hasItems("1", "2", "3"))
                .body("users.user.find{it.age == 30}.name", is("João da Silva"))
                .body("users.user.filhos.size()", is(1))
                .body("users.user.name.findAll{it.toString().startsWith('Maria')}.collect{it.toString().toUpperCase()}", is("MARIA JOAQUINA"))

        ;
    }




    //    Aqui estão exemplificadas algumas verificações e asserções um pouco mais avançadas, utilizando 'XMLpath + Java"

    @Test
    public void realizandoTestesMaisAvancadoXmlPathInJava (){
//        Object path = given()
//                .when()
//                .get("http://restapi.wcaquino.me/usersXML")
//                .then()
//                .statusCode(200)
//                .extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}")
//
//                ;
//        System.out.println(path.toString());


//        String name = given()
//                .when()
//                .get("http://restapi.wcaquino.me/usersXML")
//                .then()
//                .statusCode(200)
//                .extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}")
//
//                ;
//        Assert.assertEquals("Maria Joaquina".toUpperCase(), name.toUpperCase());

        ArrayList<Node> nomes = given()
                .when()
                .get("http://restapi.wcaquino.me/usersXML")
                .then()
                .statusCode(200)
                .extract().path("users.user.name.findAll{it.toString().contains('n')}")

                ;
        Assert.assertEquals(2, nomes.size());
        Assert.assertEquals("Maria Joaquina".toUpperCase(), nomes.get(0).toString().toUpperCase());
        Assert.assertTrue("Ana Julia".equalsIgnoreCase(nomes.get(1).toString()));
        System.out.println(nomes);
    }


}
