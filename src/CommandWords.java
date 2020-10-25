public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = { "Q", "A"};

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word.
     *
     * @param aString The String to check
     * @return true if it is valid, false otherwise
     */
    public boolean isCommand(String aString)
    {
        // Test if command is an int
        try
        {
            // Assume valid command
            int playerNum = Integer.parseInt(aString);
            return true;
        }
        catch(NumberFormatException e)
        {
            // User didn't enter a number
        }

        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }

        // String was not a number or found in the commands
        return false;
    }

    /**
     * Print all valid commands to System.out.
     */
    public String getCommandList()
    {
        String commandList = "";
        for(String command: validCommands) {
            commandList += command + " ";
            //System.out.print(command + "  ");
        }
        return commandList;
    }
}
