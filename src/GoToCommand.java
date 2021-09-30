import java.util.ArrayList;

public class GoToCommand extends Command {

    public GoToCommand()
    {
        this.triggers = new ArrayList<>();
        this.triggers.add("goto");
    }

    @Override
    public String executeCommand(ArrayList<String> commandList, Player player) throws InvalidCommandException
    {
        for (String destinationName : commandList) {

            Location currentLocation = player.getCurrentLocation();
            Location destination;

            // if try to goto the current location, just return info for current location
            if(commandList.contains(currentLocation.getLocationName()))
            {
                return currentLocation.describeLocation(player);
            }

            // else, go to another location if you can
            if(currentLocation.pathList().contains(destinationName))
            {
                destination = currentLocation.getAccessibleLocation(destinationName);
                player.updateLocation(destination);
                return player.getCurrentLocation().describeLocation(player);
            }

        }
        throw new InvalidCommandException("Cannot go here! No path to this location.");
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
