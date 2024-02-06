import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;

public class LoginJira
{

    String cookieValue;
    String issueId;

    String uri="http://localhost:9009";

    @Test(priority = 1)
    public void loginToJira()throws IOException, ParseException
    {
        FileReader fr=new FileReader("C:\\Users\\amrit\\IdeaProjects\\Jira\\src\\main\\java\\LoginToJira.json");
        JSONParser jp=new JSONParser();
        String requestBody = jp.parse(fr).toString();

        Response response = RestAssured.given().body(requestBody).contentType(ContentType.JSON).baseUri("http://localhost:9009")
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response();

       // System.out.println(response.getStatusCode());
        //System.out.println(response.asString());

        JSONObject js=new JSONObject(response.asString());
        cookieValue="JSESSIONID="+js.getJSONObject("session").get("value").toString();
        System.out.println(cookieValue);
    }
    @Test(priority = 2)
    public void createUserStory() throws IOException, ParseException
    {
        FileReader fr=new FileReader("C:\\Users\\amrit\\IdeaProjects\\Jira\\src\\main\\java\\CreateUserStory.json");
        JSONParser jp=new JSONParser();
        String requestBody = jp.parse(fr).toString();

        Response response = RestAssured.given().baseUri("http://localhost:9009").body(requestBody)
                .header("cookie",cookieValue)
                .contentType(ContentType.JSON)
                .when().post("/rest/api/2/issue")
                .then().extract().response();

        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

        JSONObject js=new JSONObject(response.asString());
        issueId=js.get("key").toString();
        System.out.println(issueId);

    }
    @Test(priority = 3)
    public void getUserStory()
    {
        Response response = RestAssured.given().baseUri("http://localhost:9009")
                .contentType(ContentType.JSON)
                .header("cookie",cookieValue)
                .when().get("/rest/api/2/issue/" + issueId)
                .then().extract().response();
        System.out.println(response.getStatusCode());
        System.out.println(response.asString());
    }

    @Test(priority = 4)
    public void updateUserStory() throws IOException, ParseException {
        FileReader fr=new FileReader("C:\\Users\\amrit\\IdeaProjects\\Jira\\src\\main\\java\\CreateUserStory.json");
        JSONParser jp=new JSONParser();
        String requestBody = jp.parse(fr).toString();

        JSONObject js=new JSONObject(requestBody);
        js.getJSONObject("fields").put("summary","Update user story");

        Response response = RestAssured.given().baseUri(uri).body(js.toString())
                .contentType(ContentType.JSON)
                .header("Cookie",cookieValue)
                .when().put("/rest/api/2/issue/" + issueId)
                .then().extract().response();

        System.out.println(response.getStatusCode());
        System.out.println(response.asString());

    }

    @Test(priority = 5)
    public void deleteUserstory()
    {
        Response response = RestAssured.given().baseUri(uri).contentType(ContentType.JSON)
                .header("Cookie", cookieValue)
                .when().delete("/rest/api/2/issue/" + issueId)
                .then().log().all().extract().response();

        //System.out.println(response.getStatusCode());
    }
}
