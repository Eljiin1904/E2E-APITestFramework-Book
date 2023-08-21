package apiTests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.List;
import java.util.Map;


public class E2E_Test1 {

    public static void main(String[] args) {
        String userID = "186bb0ca-577d-4a61-ae1d-b4077a0b39a3";
        String userName = "Eljin1904";
        String password = "Abc@123!";

        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        RequestSpecification request = RestAssured.given();


        //Step - 1
        //Test will start from generating Token for Authorization
        request.header("Content-Type", "application/json");

        Response response = request.body("{ \"userName\":\"" + userName + "\", \"password\":\"" + password + "\"}")
                .post("/Account/v1/GenerateToken");

        Assert.assertEquals(response.getStatusCode(), 200);

        String jsonString = response.asString();
        Assert.assertTrue(jsonString.contains("token"));

        //This token will be used in later requests
        String token = JsonPath.from(jsonString).get("token");


        //System.out.println(token);
        System.out.println(response.getStatusCode());
        System.out.println("Step #1 - Token Created");


        //Step - 2
        // Get Books - No Auth is required for this.
        response = request.get("/BookStore/v1/Books");

        Assert.assertEquals(response.getStatusCode(), 200);

        jsonString = response.asString();
        List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
        Assert.assertTrue(books.size() > 0);

        //This bookId will be used in later requests, to add the book with respective isbn
        String bookId = books.get(0).get("9781593275846");


        System.out.println(books);
        System.out.println("Step #2 - Get Book Request Sent");
        System.out.println(response.getStatusCode());




        //Step - 3
        // Add a book - with Auth
        //The token we had saved in the variable before from response in Step 1,
        //we will be passing in the headers for each of the succeeding request
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        response = request.body("{ \"userId\": \"" + userID + "\", " +
                        "\"collectionOfIsbns\": [ { \"isbn\": \"9781593275846\" } ]}")
                .post("/BookStore/v1/Books");

        Assert.assertEquals( 201, response.getStatusCode());

        System.out.println("Step 3 - Book Added");
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());



        //Step - 4
        // Delete a book - with Auth
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

    response = request.body("{ \"userId\": \"186bb0ca-577d-4a61-ae1d-b4077a0b39a3\"}")
                .delete("/BookStore/v1/Books");

        //Assert.assertEquals(204, response.getStatusCode());



        System.out.println(response.asString());
        System.out.println(response.getStatusCode());
        System.out.println("Step #4 - Book Deleted");


        //Step - 5
        // Get User
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        response = request.get("/Account/v1/User/" + userID);
        Assert.assertEquals(200, response.getStatusCode());

        jsonString = response.asString();
        List<Map<String, String>> booksOfUser = JsonPath.from(jsonString).get("books");
        Assert.assertEquals(0, booksOfUser.size());

        System.out.println("Step #5 - Get User");
    }
}


