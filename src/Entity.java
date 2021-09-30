abstract class Entity {

    protected String entityName;
    protected String description;
    protected String entityType;

    public String getEntityType()
    {
        return this.entityType;
    }

    public String getName()
    {
        return this.entityName;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String toString()
    {
        return this.description;
    }

}
