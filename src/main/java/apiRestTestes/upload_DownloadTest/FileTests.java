package apiRestTestes.upload_DownloadTest;

import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class FileTests {


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
    public void uploadDeveSerObrigatorio(){
        given()
                .log().all()
                .when()
                .post("/upload")
                .then()
                .log().all()
                .statusCode(404)
                .body("error", is("Arquivo não enviado"))

        ;
    }
    @Test
    public void uploadDeArquivoComPadrãoCorreto(){
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/users.pdf"))
                .when()
                .post("/upload")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("users.pdf"))

        ;
    }

    @Test
    public void uploadDeArquivoMaiorQueDefinido(){
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/CertAWS01.pdf"))
                .when()
                .post("/upload")
                .then()
                .log().all()
                .statusCode(413)


        ;
    }

    @Test
    public void realizarDownloadDeArquivo() throws IOException {
        byte[] image = given()
                .log().all()
                .when()
                .get("/download")
                .then()
                .statusCode(200)
                .extract().asByteArray()
                ;
        File imagem = new File("src/main/resources/file.jpg");
        OutputStream out = new FileOutputStream(imagem);
        out.write(image);
        out.close();

        System.out.println(imagem.length());
        Assert.assertThat(imagem.length(), lessThan(100000L));



    }

}
