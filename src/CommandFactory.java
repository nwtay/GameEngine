import java.util.ArrayList;

public class CommandFactory {

    private final CommandCollection commandCollection;
    private String triggerWord;
    private ArrayList<String> builtInTriggers;

    // executes commands based on user input
    public CommandFactory(ArrayList<Action> actionList, ArrayList<Location> locationList)
    {
        commandCollection = new CommandCollection(actionList, locationList);
    }

    // returns the first word in user input that matches a trigger word in any of the commands
    public String getFirstTrigger(ArrayList<String> userInput) throws InvalidCommandException
    {
        builtInTriggers = commandCollection.getBuiltInTriggers();

        for(int i = 0; i < userInput.size(); i++)
        {
            if(assignedTrigger(userInput, i) != null)
            {
                return triggerWord;
            }
        }
        throw new InvalidCommandException("No trigger words found in your command.");
    }

    // returns the trigger word that matches the current word in user word, if any
    public String assignedTrigger(ArrayList<String> userInput, int index)
    {
        if(builtInTrigger(userInput, index) != null || dynamicTrigger(userInput, index) != null)
        {
            return triggerWord;
        }
        return null;
    }

    // matches a string in the trigger words for built in commands
    public String builtInTrigger(ArrayList<String> userInput, int index)
    {
        // make it to lower case, then check if its a built in command
        // since we match built in command trigger words in a case insensitive way
        if(builtInTriggers.contains(userInput.get(index).toLowerCase()))
        {
            triggerWord = userInput.get(index).toLowerCase();
            // if the current word is a trigger word for a built in command, convert that word to lower case
            userInput.set(index, triggerWord);
            return triggerWord;
        }
        return null;
    }

    // matches a string in the trigger words for dynamic commands
    public String dynamicTrigger(ArrayList<String> userInput, int index)
    {
        if (commandCollection.getAllTriggers().contains(userInput.get(index))) {
            triggerWord = userInput.get(index);
            return triggerWord;
        }
        return null;
    }

    // returns the output of execution of the command
    public String executeCommand(ArrayList<String> userInput, Player currentPlayer) throws InvalidCommandException
    {
        // reset the trigger word to be empty for each new incoming command from client
        triggerWord = "";

        // find the first word in user input that is a trigger word of a command
        triggerWord = getFirstTrigger(userInput);

        // get a list of commands that contain the trigger word from userInput
        ArrayList<Command> potentialCommands = commandCollection.getTriggeredCommands(triggerWord);

        // filter potential Commands so it contains commands that have a subject mentioned in the user input
        potentialCommands = getSubjectMatches(potentialCommands, userInput);

        for (Command potentialCommand : potentialCommands) {
            // for each potential command, try to execute. If it's not null, send result of command to user
                String result = potentialCommand.executeCommand(userInput, currentPlayer);
                if (result != null) {
                    return result;
                }
        }
        // if we get here, then none of our commands executed, hence we throw a relevant, abstract error message to user
        throw new InvalidCommandException("Required items for " + triggerWord + " not present / specified in command.");

    }

    // returns all commands that have subjects that match the entities in location or current player's inventory
    private ArrayList<Command> getSubjectMatches(ArrayList<Command> potentialCommands, ArrayList<String> userInput)
    {
        ArrayList<Command> commandsWithSubjects = new ArrayList<>();
        for (Command potentialCommand : potentialCommands) {
            if (containsSubject(potentialCommand, userInput)) {
                commandsWithSubjects.add(potentialCommand);
            }
        }
        return commandsWithSubjects;
    }

    // a user must specify at least one subject to avoid ambiguity, hence this
    // method tests if they have done so for a particular command
    private boolean containsSubject(Command command, ArrayList<String> userInput)
    {
        for (String input : userInput) {
            if (command.containsSubject(input)) {
                return true;
            }
        }
        return false;
    }

}
