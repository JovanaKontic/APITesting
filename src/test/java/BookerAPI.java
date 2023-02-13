import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static files.payload.createBookingBody;
import static io.restassured.RestAssured.given;

public class BookerAPI {

    @Test
    public void PingHealthTest () {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
//        given().when().get("/ping");
//        given().log().all().when().get("/ping");
        given().log().all()                         //log all je da se prikazu svi podaci koje smo ubacili REQUEST
                .when().get("/ping")             // get nam je metoda koju smo koristili u postmanu
                .then().log().all()                 //prikazujemo RESPONSE
                .assertThat().statusCode(201);
    }

    @Test
    public void GetAllBookings () {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        given().log().all()
                .when().get("/booking")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void CreateBooking () {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                "    \"firstname\" : \"Jovana\",\n" +
                "    \"lastname\" : \"Brown1\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast1\"\n" +
                "}")
                .when().post("/booking")                   //post je metoda koju smo koristili u postmanu
                .then().log().all()
                .assertThat().statusCode(200);
    }
    @Test
    public void CreateBookingWithPayload () {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        given().log().all()
                .header("Content-Type", "application/json")
                .body(createBookingBody())
                .when().post("/booking")
                .then().log().all()
                .assertThat().statusCode(200);
    }
    @Test
    public void CreateBookingJson () {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String response = given().log().all()
                .header("Content-Type", "application/json")
                .body(createBookingBody())
                .when().post("/booking")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString(); // prebacujemo u string da bi posle mogli da koristimo elemente za cuvanje i asertovanje

        JsonPath js = new JsonPath(response);
//        String bookingID = js.getString("bookingid"); //izvlacimo iz responsa ("bookingid" onako kako je u responsu),broj prebacujemo u string
//        Assert.assertTrue(response.contains(bookingID));
        String firstname = js.getString("booking.firstname");
        String lastname = js.getString("booking.lastname");
        int totalprice = js.getInt("booking.totalprice");

        Assert.assertEquals(firstname, "Jovana");
        Assert.assertEquals(lastname, "Brown1");
        Assert.assertEquals(totalprice, 111);


    }










}
