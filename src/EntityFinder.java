import java.util.ArrayList;

public class EntityFinder {

    // a class used to find entities
    // contains methods that can be used for locations and players to
    // find artefacts

    private final ArrayList<Entity> entityList;

    public EntityFinder(Player player)
    {
        entityList = player.getEntityList();
    }

    public EntityFinder(Location location)
    {
        entityList = location.getFullEntityList();
    }

    // finds the first artefact in this location the user entered in their command
    public Entity findArtefact(ArrayList<String> userInput)
    {
        // for each word in user command, if theres an artefact with this name then return it
        for (String input : userInput) {
            Entity entity = getArtefactByName(input);
            if (entity != null) {
                return entity;
            }
        }
        return null;
    }

    // returns the entity via it's name, if it exists
    public Entity getArtefactByName(String entityName)
    {
        for (Entity entity : entityList) {
            if (entity.getName().equals(entityName) && entity.getEntityType().equals("artefact")) {
                return entity;
            }
        }
        return null;
    }

}
