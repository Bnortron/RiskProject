public class Command
{
    private String commandWord;
    private String secondWord;
    private String thirdWord;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     *
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised
     * @param secondWord The second word of the command
     */
    public Command(String firstWord, String secondWord, String thirdWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     *
     * @return The command word, or null if not understood
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * Returns the second word of the command.  Returns null if there was no
     * second word.
     *
     * @return The second word of this command, or null if only one word
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * Returns the third word of the command.  Returns null if there was no
     * second word.
     *
     * @return The second word of this command, or null if only one word
     */
    public String getThirdWord()
    {
        return thirdWord;
    }

    /**
     * Returns true if the command is unknown.
     *
     * @return true if this command was not understood, false otherwise
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    /**
     * Returns true if the command has a second word.
     *
     * @return true if the command has a second word, false otherwise
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
    /**
     * Returns true if the command has a third word.
     *
     * @return true if the command has a third word, false otherwise
     */
    public boolean hasThirdWord()
    {
        return (thirdWord != null);
    }
}


