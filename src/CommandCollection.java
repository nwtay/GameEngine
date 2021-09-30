import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CommandCollection {

    private final ArrayList<Command> commandLog;
    private final Set<String> keywordsSet;
    private final ArrayList<String> builtInTriggers;

    // a class that holds all the possible commands
    public CommandCollection(ArrayList<Action> actionList, ArrayList<Location> locationList)
    {
        // store keywords as a set, since keywords may be repeated across actions and sets store distinct items
        keywordsSet = new HashSet<>();

        // list of all commands
        commandLog = new ArrayList<>();

        // list of all the trigger words for the built in commands
        builtInTriggers = new ArrayList<>();

        // adding pre-defined commands
        addBuiltInCommands();

        // adding dynamic commands from actions JSON file
        for (Action action : actionList) {
            commandLog.add(new DynamicCommand(action, locationList));
        }

        // adding trigger words for all commands into keyword set
        for (Command command : commandLog) {
            keywordsSet.addAll(command.getTriggers());
        }
    }

    private void addBuiltInCommands()
    {
        // adds the built in commands to the command log, as well as these commands' trigger words
        commandLog.add(new InventoryCommand());
        builtInTriggers.add("inv");
        builtInTriggers.add("inventory");
        commandLog.add(new LookCommand());
        builtInTriggers.add("look");
        commandLog.add(new GetCommand());
        builtInTriggers.add("get");
        commandLog.add(new DropCommand());
        builtInTriggers.add("drop");
        commandLog.add(new GoToCommand());
        builtInTriggers.add("goto");
        commandLog.add(new HealthCommand());
        builtInTriggers.add("health");
    }

    // returns all the trigger words for the built in commands
    public ArrayList<String> getBuiltInTriggers()
    {
        return builtInTriggers;
    }

    // returns the trigger words for all commands/actions
    public Set<String> getAllTriggers()
    {
        return keywordsSet;
    }

    // returns a list of commands that have a trigger that matches triggerWord input
    public ArrayList<Command> getTriggeredCommands(String triggerWord)
    {
        ArrayList<Command> potentialCommands = new ArrayList<>();
        for (Command command : commandLog) {
            if (command.getTriggers().contains(triggerWord)) {
                potentialCommands.add(command);
            }
        }
        return potentialCommands;
    }

}
