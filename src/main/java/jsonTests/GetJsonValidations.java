package jsonTests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class GetJsonValidations {


//    Abaixo temos exemplos básicos de como realizar  validações utilizando "JsonPath"

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
    public void validarJson(){
        given()
                .when()
                .get("/users/1")
                .then()
//                .statusCode(200)
                .body("id", is(1))
                .body("name", is("João da Silva"))
                .body("age", is(30));
//                .body("salary", greaterThan(1234.5678));

    }
    @Test
    public void outrasValidacoesJson(){
        Response response = RestAssured.request(Method.GET, "/users/1");

//        //Utilizando Path
//        Assert.assertEquals(new Integer(1), response.path("id"));
//
//        //Utilizando JsonPath
//        JsonPath jsonPath = new JsonPath(response.asString());
//        Assert.assertEquals(1, jsonPath.getInt("id"));

        //Utilizando from
        int id = JsonPath.from(response.asString()).getInt("id");
        Assert.assertEquals(1, id);

    }
    @Test
    public void outrasValidacoesJsonSegundoNivel(){
        given()
                .when()
                .get("/users/2")
                .then()
//                .statusCode(200)
                .body("name", containsString("Maria Joaquina"))
                .body("endereco.rua", is("Rua dos bobos"))
                .body("age", is(25));


    }
    @Test
    public void verificarListaJson(){
        given()
                .when()
                .get("/users/3")
                .then()
//                .statusCode(200)
                .body("name", containsString("Ana"))
                .body("filhos", hasSize(2))

//  As linhas 65,66 e 67, mostram duas maneiras eficientes de obter objetos especificos a partir de uma lista Json

                .body("filhos[0].name", is("Zezinho"))
                .body("filhos[1].name", is("Luizinho"))
//                .body("filhos.name", hasItem("Zezinho"), "Luizinho")
        ;
    }
    @Test
    public void usuarioInexistente(){
        given()
                .when()
                .get("users/4")
                .then()
//                .statusCode(404)
                .body("error", is("Usuário inexistente"))
                ;
    }

    @Test
    public void verificandoListaNaRaiz(){
        given()
                .when()
                .get("/users")
                .then()
//                .statusCode(200)
                .body("$", hasSize(3))
                .body("name", hasItems("João da Silva","Maria Joaquina","Ana Júlia"))
                .body("age[1]", is(25))
                .body("filhos.name", hasItem(Arrays.asList("Zezinho", "Luizinho")))
//  A letra "f" alocada no primeiro valor contido em "salary", é para que a aplicação encotre o atributo requisitado que se trada de um float
                .body("salary", contains(1234.5677f, 2500, null))

        ;
    }
    @Test
    public void realizandoVerificacoesMaisAvandadas(){
        given()
                .when()
                .get("/users")
                .then()
//                .statusCode(200)
                .body("$", hasSize(3))
                .body("age.findAll{it <= 25}.size()", is(2))
                .body("age.findAll{it <= 25 && it > 20}.size()", is(1))
                .body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina")) // Validando um objeto na lista em ordem crescente

                .body("findAll{it.age <= 25}[0].name", is("Maria Joaquina")) // Validando um objeto na lista em ordem crescente


                .body("findAll{it.age <= 25}[-1].name", is("Ana Júlia")) // Validando um objeto na lista em ordem decrescente



        ;
    }
    @Test
    public void unindoJasonPathComJava(){
        ArrayList<String> names =
        given()
                .when()
                .get("/users")
                .then()
//                .statusCode(200)
                .extract().path("name.findAll{it.startsWith('Maria')}")
                ;
        Assert.assertEquals(1, names.size());
        Assert.assertTrue(names.get(0).equalsIgnoreCase("Maria Joaquina"));
        Assert.assertEquals(names.get(0).toUpperCase(), "maria joaquina".toUpperCase());


    }
}
