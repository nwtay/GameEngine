public class InvalidCommandException extends Exception{

    private final String errorMessage;

    public InvalidCommandException(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String toString()
    {
        return "Invalid Command: " + errorMessage;
    }

}
