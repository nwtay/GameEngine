import java.util.ArrayList;

public class Player extends Entity{

    private Location location;
    private final ArrayList<Entity> inventory;
    private int health;

    public Player(String entityName, Location location)
    {
        this.entityName = entityName;
        this.location = location;
        inventory = new ArrayList<>();
        this.entityType = "player";
        this.health = 3;
        // for player's description same as their name
        this.description = entityName;
    }

    public void updateLocation(Location location)
    {
        this.location.removeEntity(this);
        this.location = location;
        location.addEntity(this);
    }

    public Location getCurrentLocation()
    {
        return this.location;
    }

    public String getInventory()
    {
        return inventory.toString();
    }

    public void addToInventory(Artefact artefact)
    {
        inventory.add(artefact);
    }

    // drops artefact from player's inventory
    public void dropArtefact(Artefact artefact)
    {
        inventory.remove(artefact);
    }

    public ArrayList<Entity> getAbstractInventory()
    {
        return new ArrayList<>(inventory);
    }

    public void removeEntity(String entityName)
    {
        for(int i = 0; i < inventory.size(); i++)
        {
            if(inventory.get(i).getName().equals(entityName)){
                inventory.remove(inventory.get(i));
            }
        }
    }

    public int getHealth()
    {
        return health;
    }

    public void removeHealth()
    {
        health -= 1;
    }

    public void addHealth()
    {
        health += 1;
    }

    public ArrayList<Entity> getEntityList()
    {
        return inventory;
    }

    // this implements a drop, by which we find an entity from user command, then
    // if present, we drop it in the current location
    public String dropEntitySwap(ArrayList<String> userInput)
    {
        EntityFinder entityFinder = new EntityFinder(this);
        Entity entity = entityFinder.findArtefact(userInput);
        if(entity != null){
            dropArtefact((Artefact) entity);
            getCurrentLocation().addEntity(entity);
            return entity + " dropped from inventory.";
        }
        return null;
    }

    // this implements picking up an artefact, determined from the user command,
    // from the current location
    public String getEntitySwap(ArrayList<String> userInput)
    {
        EntityFinder entityFinder = new EntityFinder(getCurrentLocation());
        Entity entity = entityFinder.findArtefact(userInput);
        if(entity != null){
            addToInventory((Artefact) entity);
            getCurrentLocation().removeEntity(entity);
            return entity + " added to inventory.";
        }
        return null;
    }

    public void resetHealth()
    {
        health = 0;
    }

}
