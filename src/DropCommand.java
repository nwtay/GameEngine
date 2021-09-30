import java.util.ArrayList;

public class DropCommand extends Command{

    protected ArrayList<String> triggers;

    public DropCommand()
    {
        triggers = new ArrayList<>();
        triggers.add("drop");
    }

    public String executeCommand(ArrayList<String> userInput, Player player) throws InvalidCommandException
    {
        String result = player.dropEntitySwap(userInput);
        if(result == null){
            throw new InvalidCommandException("Could not execute drop command as no artefacts given from your inventory.");
        }
        return result;
    }

    public ArrayList<String> getTriggers()
    {
        return this.triggers;
    }

    public ArrayList<String> getSubjects()
    {
        return null;
    }

    public boolean containsSubject(String subject)
    {
        // since this is a built in command, it requires no subjects
        return true;
    }

}
