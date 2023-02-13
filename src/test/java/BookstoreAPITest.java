import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.body;

import static io.restassured.RestAssured.given;

public class BookstoreAPITest {
    String token;
    @Test
    public void createUser() {
        body createUserBody = new body();
        createUserBody.setUserName("Jovana1");
//        createUserBody.setPassword("!Nekidrugipassword1");   //ako ovo ne ubacimo onda nam je password null i onda se uzima vrednost koju smo stavili u body
        RestAssured.baseURI = "https://demoqa.com";
        given().log().all()
                .header("Content-Type", "application/json")
                .body(createUserBody).post("Account/v1/User")
                .then().log().all()
                .assertThat().statusCode(201);
    }
    @Test
    public void generateToken () {
//        MORAM PRVO DA KREIRAM NOVOG USERA AKO HOCU SVE DA PUSTIM ODJEDNOM

        body createUserBody = new body();
        createUserBody.setUserName("Jovana");
        RestAssured.baseURI ="https://demoqa.com";
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(createUserBody).post("/Account/v1/GenerateToken")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = new JsonPath(response);
        token = js.getString("token");
        System.out.println(token);
        String status = js.getString("status");
        String resault = js.getString("result");
        Assert.assertEquals(status, "Success");
        Assert.assertEquals(resault,"User authorized successfully.");
    }
    @Test
    public void Authorisation () {
        body createUserBody = new body();
        createUserBody.setUserName("Jovana2");
        RestAssured.baseURI ="https://demoqa.com";
//        given().log().all().header().body().post().then().log().all().assertThat().statusCode(200)
        ;

    }



}
