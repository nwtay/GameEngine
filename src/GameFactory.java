import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class GameFactory {

    private final ArrayList<Location> locationList;
    ArrayList<Player> players;
    private final CommandFactory commandFactory;
    private static GameFactory gameFactory;

    // factory pattern implementation: GameFactory is a factory that holds all elements
    // of the game (entities, actions, players), and executes incoming commands
    private GameFactory(String entityFilename, String actionFilename)
    {
        EntityParser entityParser = new EntityParser(entityFilename);
        // load and parse all entities from entities file
        entityParser.parseAndLoad();
        // get the list of all locations loaded with entities
        locationList = entityParser.getLocationList();

        ActionParser actionParser = new ActionParser(actionFilename);
        // load and parse all possible actions from actions file
        actionParser.parseAndLoad();
        // get the list of all possible actions
        ArrayList<Action> actionList = actionParser.getActionList();

        // command factory attempts to retrieve a command that the user intended to use
        // and execute that command based off of the user input
        commandFactory = new CommandFactory(actionList, locationList);

        // the list of players that have joined this game
        players = new ArrayList<>();
    }

    // implementation of singleton pattern - don't want more than one server running
    // hence, we store a running instance in gameFactory, then make this method static
    // so can retrieve an instance without instantiating a new GameFactory
    public static GameFactory getInstance(String entityFilename, String actionFilename) throws FileNotFoundException
    {
        if(gameFactory == null){
            gameFactory = new GameFactory(entityFilename, actionFilename);
        }
        return gameFactory;
    }

    // does the main handling of the command, such as parsing and executing
    public String handleCommand(String newCommand) throws InvalidCommandException
    {
        // assumed only normal names will be used (alphabetical with perhaps hyphens and apostrophes)
        int colonIndex = newCommand.indexOf(':');
        String playerName = newCommand.substring(0, colonIndex);
        // gets the player the command is associated with, if new player, gets this new player
        Player currentPlayer = getPlayer(playerName);

        // checks there is text within the user command
        parseInput(newCommand, colonIndex);

        ArrayList<String> splitCommands = getSplitCommand(newCommand.substring(colonIndex + 1));
        // uses factory pattern to find the action, and execute the user command
        return commandFactory.executeCommand(splitCommands, currentPlayer);
    }

    private void parseInput(String newCommand, int colonIndex) throws InvalidCommandException
    {
        // if user enters nothing, throw error
        int endOfNameIndex = colonIndex + 1;
        // i.e., if the command is less than the index at the end of player name, we have an empty command
        checkLength(newCommand, endOfNameIndex);

        // if the user just enters a series of spaces, throw error
        String plainCommand = newCommand.substring(colonIndex + 1);
        // if command is less than 1 in length, we have an empty command
        checkLength(plainCommand, 1);
    }

    private void checkLength(String newCommand, int requiredLength) throws InvalidCommandException
    {
        if(newCommand.length() < requiredLength)
        {
            throw new InvalidCommandException("No input. Please type a command");
        }
    }

    // splits the user's command into separate strings
    private ArrayList<String> getSplitCommand(String plainCommand)
    {
        String[] commandArray = plainCommand.split("\\s+");
        ArrayList<String> splitCommands = new ArrayList<>(Arrays.asList(commandArray));
        splitCommands.remove(0);
        return splitCommands;
    }

    // gets a player, if they exist, by the string
    private Player getPlayer(String playerName)
    {
        for (Player player : players) {
            if (player.entityName.equals(playerName)) {
                return player;
            }
        }

        // if player isn't in list of players, add new player and set their location to be at start
        Player newPlayer = new Player(playerName, locationList.get(0));
        // add player to start location
        locationList.get(0).addEntity(newPlayer);
        players.add(newPlayer);
        return newPlayer;
    }

}

