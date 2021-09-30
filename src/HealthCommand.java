import java.util.ArrayList;

public class HealthCommand extends Command {

    private final ArrayList<String> triggers;

    public HealthCommand()
    {
        triggers = new ArrayList<>();
        triggers.add("health");
    }

    @Override
    public String executeCommand(ArrayList<String> commandList, Player player)
    {
        int health = player.getHealth();
        return "" + health;
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
