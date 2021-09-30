import java.util.ArrayList;

abstract class Command {

    protected ArrayList<String> triggers;
    protected ArrayList<String> subjects;

    public abstract String executeCommand(ArrayList<String> commandList, Player player) throws InvalidCommandException;

    public abstract ArrayList<String> getTriggers();

    public abstract ArrayList<String> getSubjects();

    public abstract boolean containsSubject(String subject);

}
