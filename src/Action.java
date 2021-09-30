import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Action {

    private final JSONObject jsonObject;
    private final String narration;
    private final HashMap<String, ArrayList<String>> actionElements;

    public Action(JSONObject jsonObject)
    {
        // a class which holds all actions from the json file
        this.jsonObject = jsonObject;
        // a hash map which stores lists of strings: trigger words, subjects, consumed entities, produced entities,
        // narration
        actionElements = new HashMap<>();
        addElement("triggers");
        addElement("subjects");
        addElement("consumed");
        addElement("produced");
        narration = jsonObject.get("narration").toString();
    }

    private void addElement(String element)
    {
        // adding an element to the hash map which stores all the action features
        ArrayList<String> elementList = new ArrayList<>();
        // retrieving the jsonArray from the actions.json file
        JSONArray jsonArray = (JSONArray)jsonObject.get(element);
        if(jsonArray != null){
            for (Object object : jsonArray) {
                elementList.add(object.toString().toLowerCase());
            }
        }
        actionElements.put(element, elementList);
    }

    public ArrayList<String> getTriggers()
    {
        return actionElements.get("triggers");
    }

    public String getNarration()
    {
        return narration;
    }

    public HashMap<String, ArrayList<String>> getActionElements()
    {
        return actionElements;
    }

}
