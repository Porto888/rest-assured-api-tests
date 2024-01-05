package publicApiTest;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class Auth {

    @Test
    public void naoDeveAcessarWeatherMapApiSemAutenticacao(){
        given()
                .log().all()
                .queryParams("q", "Salvador,BR")
                .queryParams("appid", "")
                .queryParams("units", "metric")
                .when()
                .get("https://api.openweathermap.org/data/2.5/weather")
                .then()
                .log().all()
                .statusCode(401)
                .body("message", is("Invalid API key. Please see https://openweathermap.org/faq#error401 for more info."))

        ;
    }


    @Test
    public void acessarWeatherMapApi(){
        given()
                .log().all()
                .queryParams("q", "Salvador,BR")
                .queryParams("appid", "d21d6b447f78bff7df75ecd294fb0f85")
                .queryParams("units", "metric")
                .when()
                .get("https://api.openweathermap.org/data/2.5/weather")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Salvador"))
                .body("sys.country", is("BR"))


        ;
    }
}
