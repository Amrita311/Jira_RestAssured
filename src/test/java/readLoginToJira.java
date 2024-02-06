import groovy.json.JsonParser;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class readLoginToJira
{
    public static void main(String[] args) throws IOException, ParseException {
        FileReader fr=new FileReader("C:\\Users\\amrit\\IdeaProjects\\Jira\\src\\main\\java\\LoginToJira.json");
        JSONParser jp=new JSONParser();
        String requestBody = jp.parse(fr).toString();
        System.out.println(requestBody);



    }
}
