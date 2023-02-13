package D_20_Jovana_Kontic;

import D_20_Jovana_Kontic.pojo.Body;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;


public class petStoreAPITest {
    //KRECEMO OD USERA
    @Test
    public void createUser () {
//          AKO IMA BODY ONDA SE PRAVI odvojeni folder (pojo - ako hocemo da menjamo interaktivno i
//                                                  payloads - ako hocemo da imamo unapred spremljene vrednosti)

        Body createUserBody = new Body();
        createUserBody.setUsername("Jovana1");
        RestAssured.baseURI ="https://petstore.swagger.io/v2";
        given().log().all()
                .header("Content-Type", "application/json")
                .body(createUserBody)
                .when().post("/user")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void logInUser () {
        Body createUserBody = new Body();
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(createUserBody)
                .when().get("/user/login?" + createUserBody.getUsername() + createUserBody.getPassword())
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        Assert.assertTrue(response.contains("logged in user session"));
    }

    @Test
    public void userFlow () {

//        CREATE USER
        Body createUserBody = new Body();
        createUserBody.setUsername("JovanaKo");
        RestAssured.baseURI ="https://petstore.swagger.io/v2";
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(createUserBody)
                .when().post("/user")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

//        EXTRACT ID
        JsonPath js = new JsonPath(response);
        String id = js.getString("message");
        createUserBody.setId(id);

//        GET USER LOG IN
        String response1 = given().log().all()
                .when().get("/user/login?" + createUserBody.getUsername() + createUserBody.getPassword())
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

//        ASSERT LOG IN
        Assert.assertTrue(response1.contains("logged in user session"));

//        UPDATE USER
        createUserBody.setFirstName("Joka");
        String response2 = given().log().all()
                .header("Content-Type", "application/json")
                .body(createUserBody)
                .when().put("/user/" + createUserBody.getUsername())
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

//        EXTRACT ID AFTER UPDATE
        JsonPath js1 = new JsonPath(response2);
        String id2 = js1.getString("message");

//        ASSERT ID S
        Assert.assertEquals(id, id2);

//          GET USER BY USER NAME
        String response3 = given().log().all()
                .when().get("/user/" + createUserBody.getUsername())
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js2 = new JsonPath(response3);
        String firstNameConfirm = js2.getString("firstName");

//        ASSERRT FIRST NAME
        Assert.assertEquals(firstNameConfirm, "Joka");

//        USER LOG OUT
        String response4 = given().log().all()
                .when().get("/user/logout")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

//        ASSERT LOGGING OUT
        JsonPath js3 = new JsonPath(response4);
        String logOutMessage = js3.getString("message");

        Assert.assertEquals(logOutMessage,"ok");

//        DELETE USER
        String response5 = given().log().all()
                .when().delete("/user/" + createUserBody.getUsername())
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

//        ASSERT DELETED USERS
        JsonPath js4 = new JsonPath(response5);
        String deleteuser = js4.getString("message");

        Assert.assertEquals(deleteuser,createUserBody.getUsername());






    }

}
