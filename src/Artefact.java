public class Artefact extends Entity{

    public Artefact(String entityName, String description)
    {
        // an entity which can be picked up and dropped by players
        this.entityName = entityName;
        this.description = description;
        this.entityType = "artefact";
    }

}
