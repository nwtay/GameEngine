import java.util.ArrayList;
import java.util.HashMap;

public class DynamicCommand extends Command{

    private final String narration;
    private final ArrayList<Location> locationList;
    private Player player;
    private final HashMap<String, ArrayList<String>> actionElements;

    public DynamicCommand(Action action, ArrayList<Location> locationList)
    {
        this.actionElements = action.getActionElements();
        this.locationList = locationList;
        narration = action.getNarration();
    }

    @Override
    public String executeCommand(ArrayList<String> commandList, Player player) throws InvalidCommandException
    {
        this.player = player;

        if (allSubjectsPresent())
        {
            // remove all consumed items
            removeConsumed();
            // add all produced items
            addProduced();
            // check health
            if(player.getHealth() <= 0){
                playerLose();
                return "No health! You've lost everything...";
            }
            return narration;
        }

        return null;
    }

    @Override
    public ArrayList<String> getTriggers()
    {
        return actionElements.get("triggers");
    }

    @Override
    public ArrayList<String> getSubjects()
    {
        return actionElements.get("subjects");
    }

    @Override
    public boolean containsSubject(String subject)
    {
        return actionElements.get("subjects").contains(subject);
    }

    // adds "produced" elements to the player/current location
    private void addProduced()
    {
        for (int j = 0; j < actionElements.get("produced").size(); j++) {

            // if produces health, add health
            addIfHealth(actionElements.get("produced").get(j));

            // add if produced item is a location
            addIfLocation(actionElements.get("produced").get(j));

            // if not, it must be an entity in another location
            addIfWanderingEntity(actionElements.get("produced").get(j));

        }
    }

    // if the produced item is health, execute addHealth command to player
    private void addIfHealth(String producedItem)
    {
        if(producedItem.equalsIgnoreCase("health")){
            player.addHealth();
        }
    }

    // find the "produced" item, and produce it in the current location
    private void addIfWanderingEntity(String producedItem)
    {
        for (Location location : locationList) {
            // check every location, if you find it swap it into this location
            if (location.getEntity(producedItem) != null) {
                Entity producedEntity = location.getEntity(producedItem);
                location.removeEntity(producedEntity);
                player.getCurrentLocation().addEntity(producedEntity);
            }
        }
    }

    // if the command adds a path, add this location to the current location's paths
    private void addIfLocation(String producedItem)
    {
        Location location = getLocation(producedItem);
        if(location != null) {
            player.getCurrentLocation().addPath(location);
        }
    }

    // make player drop their inventory in the current location, as well as sending player to start
    private void playerLose()
    {
        while(player.getAbstractInventory().size() != 0){
            // add player's entity to the current location
            player.getCurrentLocation().addEntity(player.getAbstractInventory().get(0));
            // remove player's entity from their inventory
            player.removeEntity(player.getAbstractInventory().get(0).getName());
        }

        // send player back to the start
        player.updateLocation(locationList.get(0));
        player.resetHealth();
    }

    // returns true if there are missing subjects
    private boolean allSubjectsPresent()
    {
        ArrayList<String> missingSubjects = new ArrayList<>();
        for (String subject : actionElements.get("subjects")) {
            // if this subject doesn't exist, add it to missing subjects
            if (absentSubject(subject)) {
                missingSubjects.add(subject);
            }
        }
        return missingSubjects.size() <= 0;
    }

    // get the location based on the string, if it exists
    private Location getLocation(String producedString)
    {
        for (Location location : locationList) {
            if (location.getLocationName().equals(producedString)) {
                return location;
            }
        }
        return null;
    }

    // returns true if the subject does not exist
    private boolean absentSubject(String subject)
    {
        if(player.getCurrentLocation().entitiesString(player).contains(subject)){
            return false;
        }
        return !player.getInventory().contains(subject);
    }

    // remove all consumed items for this command from the player's inventory or their current location
    private void removeConsumed()
    {
        for(int i = 0; i < actionElements.get("consumed").size(); i++)
        {
            if(actionElements.get("consumed").get(i).equals("health")){
                player.removeHealth();
            }
            else {
                player.removeEntity(actionElements.get("consumed").get(i));
                player.getCurrentLocation().dropEntity(actionElements.get("consumed").get(i));
            }
        }
    }

}
