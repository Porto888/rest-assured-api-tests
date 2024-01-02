package jsonTests;

import SerializandoObjetos.User;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostTests {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification respSpec;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "https://restapi.wcaquino.me";

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

    @Test
    public void salvarUsuario() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{ \"name\": \"Porto\", \"age\": 31}")
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", is("Porto"))
//                .body("age", is("31"))

        ;

    }

    @Test
    public void naoSalvarUsuarioSemNome() {
        given()
                .log().all()
                .contentType("application/json")
                .body("{ \"age\": 31}")
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(400)
                .body("id", is(nullValue()))
                .body("error", is("Name é um atributo obrigatório"))


        ;
    }

    @Test
    public void salvarUsuarioUtilizandoMap() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "GPorto");
        params.put("age", 18);

        given()
                .log().all()
                .contentType("application/json")
                .body(params)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", is("GPorto"))


        ;

    }

    @Test
    public void salvarUsuarioUtilizandoObjeto() {
        User user = new User("User Obj", 21);

        given()
                .log().all()
                .contentType("application/json")
                .body(user)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("name", is("User Obj"))


        ;

    }

    @Test
    public void deserealizandoObjetoAoSalvarUsuário() {
        User user = new User("User Obj88", 24);

        User userInserido = given()
                .log().all()
                .contentType("application/json")
                .body(user)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(User.class)

        ;
        System.out.println(userInserido);
        Assert.assertEquals("User Obj88", userInserido.getName());
        Assert.assertThat(userInserido.getId(), notNullValue());


    }

}
