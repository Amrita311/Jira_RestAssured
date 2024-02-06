import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

public class ReadEmployeeFile
{
    public static void main(String[] args) throws IOException, ParseException {
        FileReader fr=new FileReader("C:\\Users\\amrit\\IdeaProjects\\Jira\\src\\main\\java\\Employee.json");
        JSONParser jp=new JSONParser();
        String requestBody = jp.parse(fr).toString();
        System.out.println(requestBody);

        JSONObject js=new JSONObject(requestBody);
        String name = js.getJSONArray("employees").getJSONObject(0).get("name").toString();
        System.out.println(name);

        String indexOne = js.getJSONArray("employees").getJSONObject(1).toString();
        System.out.println(indexOne);

        String email = js.getJSONArray("employees").getJSONObject(1).get("email").toString();
        System.out.println(email);



    }
}
