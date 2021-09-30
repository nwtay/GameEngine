import java.util.ArrayList;

public class Location extends Entity{

    private final ArrayList<Entity> entityList;
    private final ArrayList<Location> accessibleLocations;

    public Location(String entityName, String description)
    {
        this.entityName = entityName;
        this.description = description;
        entityList = new ArrayList<>();
        // list of the paths from this location
        accessibleLocations = new ArrayList<>();
        this.entityType = "location";
    }

    public void addPath(Location location)
    {
        // if this location is not null, and there isn't already a
        // path to it, add one
        if(location != null && !accessibleLocations.contains(location)) {
            accessibleLocations.add(location);
        }
    }

    // gets the list of locations accessible from this location
    public String pathList()
    {
        StringBuilder pathString = new StringBuilder();
        for (Location accessibleLocation : accessibleLocations) {
            pathString.append(accessibleLocation.getName()).append("\n");
        }
        return pathString.toString();
    }

    // gets the list of entities in this location
    public String entitiesString(Player player)
    {
        StringBuilder entitiesString = new StringBuilder();
        if(entityList.size() > 0) {
            for (Entity entity : entityList) {
                if (!entity.equals(player)) {
                    entitiesString.append(entity.getName()).append("\n");
                }
            }
        }
        return entitiesString.toString();
    }

    public void addEntity(Entity entity)
    {
        entityList.add(entity);
    }

    public void removeEntity(Entity entity)
    {
        entityList.remove(entity);
    }

    // returns the location as a string, including entities within the location
    public String toString()
    {
        StringBuilder locationString = new StringBuilder();
        locationString.append(entityName).append(" (").append(description).append("):\n");
        for (Entity entity : entityList) {
            locationString.append("   ").append(entity.toString()).append("\n");
        }
        return locationString.toString();
    }

    public String getLocationName()
    {
        return entityName;
    }

    public Entity getEntity(String entityName)
    {
        for (Entity entity : entityList) {
            if (entity.getName().equals(entityName)) {
                return entity;
            }
        }
        return null;
    }

    // removes entity from location
    public void dropEntity(String entityName)
    {
        for(int i = 0; i < entityList.size(); i++)
        {
            if(entityList.get(i).getName().equals(entityName)){
                entityList.remove(entityList.get(i));
            }
        }
    }

    // gets a location by name if it exists in the path of locations
    public Location getAccessibleLocation(String locationName)
    {
        for (Location accessibleLocation : accessibleLocations) {
            if (accessibleLocation.getLocationName().equals(locationName)) {
                return accessibleLocation;
            }
        }
        return null;
    }

    public ArrayList<Entity> getAllOtherEntities(Player player)
    {
       // this method returns all present entities, apart from the current player
        ArrayList<Entity> entitiesWithoutPlayer = new ArrayList<>();
        for (Entity entity : entityList) {
            if (!entity.equals(player)) {
                entitiesWithoutPlayer.add(entity);
            }
        }
        return entitiesWithoutPlayer;
    }

    public ArrayList<Entity> getFullEntityList()
    {
        return entityList;
    }

    // returns a string that describes the location, from a player's perspective
    public String describeLocation(Player player)
    {
        StringBuilder narration = new StringBuilder("You are in ");
        narration.append(getDescription()).append(".\n");
        narration.append("You can see:\n");
        // we want all entities present, not including the player that is looking
        for(int i = 0; i < getAllOtherEntities(player).size(); i++){
            narration.append(getAllOtherEntities(player).get(i).getDescription()).append("\n");
        }
        narration.append("You can access from here:\n");
        narration.append(pathList());
        return narration.toString();
    }

}
