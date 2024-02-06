
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ReadUpdateUserStory
{
    public static void main(String[] args) throws IOException, ParseException
    {
        FileReader fr=new FileReader("C:\\Users\\amrit\\IdeaProjects\\Jira\\src\\main\\java\\UpdateUserStory.json");
        JSONParser jp=new JSONParser();
        String requestBody = jp.parse(fr).toString();
        System.out.println(requestBody);

        JSONObject js=new JSONObject(requestBody);
        String key=js.getJSONObject("fields").getJSONObject("project").get("key").toString();
        System.out.println(key);

        String summary = js.getJSONObject("fields").get("summary").toString();
        System.out.println(summary);

        String name = js.getJSONObject("fields").getJSONObject("issuetype").get("name").toString();
        System.out.println(name);

        js.getJSONObject("fields").put("summary","UserStory");
        System.out.println(js);

    }
}
