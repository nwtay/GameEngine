import java.util.ArrayList;

public class InventoryCommand extends Command{

    private final ArrayList<String> triggers;

    public InventoryCommand()
    {
        triggers = new ArrayList<>();
        triggers.add("inventory");
        triggers.add("inv");
    }

    @Override
    public String executeCommand(ArrayList<String> commandList, Player player)
    {
        return player.getInventory();
    }

    @Override
    public ArrayList<String> getTriggers()
    {
        return this.triggers;
    }

    @Override
    public ArrayList<String> getSubjects()
    {
        return null;
    }

    @Override
    public boolean containsSubject(String subject)
    {
        // since this is a built in command, it requires no subjects
        return true;
    }

}
