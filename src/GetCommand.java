import java.util.ArrayList;

public class GetCommand extends Command{

    private final ArrayList<String> triggers;

    public GetCommand()
    {
        triggers = new ArrayList<>();
        this.triggers.add("get");
    }

    @Override
    public String executeCommand(ArrayList<String> userInput, Player player) throws InvalidCommandException
    {
        String result = player.getEntitySwap(userInput);
        if(result == null){
            throw new InvalidCommandException("Could not execute get command as entities specified not present");
        }
        return result;
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
