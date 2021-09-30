import org.json.simple.parser.JSONParser;
import org.json.simple.*;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ActionParser {

    private final String actionFilename;
    private final ArrayList<Action> actionList;

    public ActionParser(String actionFilename)
    {
        // object for parsing the actions JSON file
        this.actionFilename = actionFilename;
        actionList = new ArrayList<>();
    }

    public void parseAndLoad()
    {
        try{
            // using the JSON parser to check validity of the actions file
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(actionFilename);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("actions");

            for(Object arrayIndex: jsonArray)
            {
                // for each action, if parsed ok, store it in Action class and add to list
                // actions
                JSONObject jsonAtIndex = (JSONObject) arrayIndex;
                Action action = new Action(jsonAtIndex);
                actionList.add(action);
            }
        }
        catch (ParseException | IOException | ClassCastException exception) {
            System.out.println("Parsing of action file failed: " + exception);
        }
    }

    public ArrayList<Action> getActionList()
    {
        return actionList;
    }

}
