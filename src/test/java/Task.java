import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;

public class Task
{
    String uri="http://localhost:9009";
     String cookieValue;
     String issueId;

    @Test(priority = 1)
    public void loginToJIra() throws IOException, ParseException {
        FileReader fr=new FileReader("C:\\Users\\amrit\\IdeaProjects\\Jira\\src\\main\\java\\LoginToJira.json");
        JSONParser jp=new JSONParser();
        String requestBody = jp.parse(fr).toString();

        Response response = RestAssured.given().body(requestBody).contentType(ContentType.JSON)
                .baseUri(uri)
                .when().post("/rest/auth/1/session")
                .then().extract().response();

        System.out.println(response.statusCode());

        JSONObject js=new JSONObject(response.asString());
        cookieValue="JSESSIONID="+js.getJSONObject("session").get("value").toString();
        System.out.println(cookieValue);
    }

    @Test(priority = 2)
    public void createTask() throws IOException, ParseException {
        FileReader fr=new FileReader("C:\\Users\\amrit\\IdeaProjects\\Jira\\src\\main\\java\\Task.json");
        JSONParser jp=new JSONParser();
        String requestBody = jp.parse(fr).toString();

        Response response = RestAssured.given().baseUri(uri).body(requestBody).contentType(ContentType.JSON)
                .header("Cookie", cookieValue)
                .when().post("/rest/api/2/issue")
                .then().extract().response();

        System.out.println(response.statusCode());
        System.out.println(response.asString());

        JSONObject js=new JSONObject(response.asString());
        issueId=js.get("key").toString();

        Assert.assertEquals(response.statusCode(),201);
    }

    @Test(priority = 3)
    public void getTask()
    {
        Response response = RestAssured.given().baseUri(uri).header("Cookie", cookieValue)
                .contentType(ContentType.JSON)
                .when().get("/rest/api/2/issue/" + issueId)
                .then().extract().response();

        System.out.println(response.statusCode());
        System.out.println(response.asString());

        Assert.assertEquals(response.statusCode(),200);
    }

    @Test(priority = 4)
    public void updateTask() throws IOException, ParseException {
        FileReader fr=new FileReader("C:\\Users\\amrit\\IdeaProjects\\Jira\\src\\main\\java\\Task.json");
        JSONParser jp=new JSONParser();
        String requestBody = jp.parse(fr).toString();

        JSONObject js=new JSONObject(requestBody);
        js.getJSONObject("fields").put("summary","Update Task");

        Response response = RestAssured.given().baseUri(uri).body(js.toString()).contentType(ContentType.JSON)
                .header("Cookie", cookieValue)
                .when().put("/rest/api/2/issue/" + issueId)
                .then().extract().response();

        System.out.println(response.statusCode());

    }

    @Test(priority = 5)
    public void getUpdatedTask()
    {
        Response response = RestAssured.given().baseUri(uri).header("Cookie", cookieValue)
                .contentType(ContentType.JSON)
                .when().get("/rest/api/2/issue/" + issueId)
                .then().extract().response();

        System.out.println(response.statusCode());
    }

    @Test(priority = 6)
    public void deleteTask()
    {
        Response response = RestAssured.given().baseUri(uri).contentType(ContentType.JSON)
                .header("Cookie", cookieValue)
                .when().delete("/rest/api/2/issue/" + issueId)
                .then().extract().response();

        System.out.println(response.statusCode());
    }
}
