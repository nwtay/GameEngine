import java.util.ArrayList;

public class LookCommand extends Command {

    private final ArrayList<String> triggers;

    public LookCommand()
    {
        triggers = new ArrayList<>();
        triggers.add("look");
    }

    @Override
    public String executeCommand(ArrayList<String> commandList, Player player)
    {
        Location currentLocation = player.getCurrentLocation();
        return currentLocation.describeLocation(player);
    }

    @Override
    public ArrayList<String> getTriggers()
    {
        return this.triggers;
    }

    @Override
    public ArrayList<String> getSubjects()
    {
        return this.subjects;
    }

    @Override
    public boolean containsSubject(String subject)
    {
        // since this is a built in command, it requires no subjects
        return true;
    }
}
