package xmlTests;

import SerializandoObjetos.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostXmlValidations {

    @BeforeClass
    public static void setUp(){
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
    public void salvarUsuarioViaXml(){
        given()
                .log().all()
                .contentType("application/xml")
                .body("<user>" +
                        "<name>Rafael Porto</name>" +
                        "<age>08</age>" +
                        "</user>")
                .when()
                .post("/usersXML")
                .then()
                .log().all()
                .statusCode(201)
                .body("user.@id", is(notNullValue()))
                .body("user.name", is("Rafael Porto"))
                .body("user.age", is("08"))

        ;

    }

    @Test
    public void naoSalvarUsuarioSemNomeViaXml(){
        given()
                .log().all()
                .contentType(ContentType.XML)
                .body("<user>" +
                        "<age>08</age>" +
                        "</user>")
                .when()
                .post("/usersXML")
                .then()
                .log().all()
                .statusCode(400)
                .body("user.@id", is(nullValue()))
                .body("error", is("Name é um atributo obrigatório"))
        ;


    }

    @Test
    public void salvarUsuarioViaXmlUtilizandoObjeto(){
        User user = new User("User Xml", 35);
        given()
                .log().all()
                .contentType(ContentType.XML)
                .body(user)
                .when()
                .post("/usersXML")
                .then()
                .log().all()
                .statusCode(201)
                .body("user.@id", is(notNullValue()))
                .body("user.name", is("User Xml"))
                .body("user.age", is("35"))

        ;

    }

    @Test
    public void deserealizandoObjetoAoSalvarUsuário() {
        User user = new User("User Obj99", 33);

        User userInserido = given()
                .log().all()
                .contentType(ContentType.XML)
                .body(user)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(User.class)

                ;
        System.out.println(userInserido);
        Assert.assertEquals("User Obj99", userInserido.getName());
        Assert.assertThat(userInserido.getId(), notNullValue());


    }
}
