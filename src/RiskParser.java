import java.util.Scanner;
import java.util.StringTokenizer;

public class RiskParser
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public RiskParser()
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * Get the command from the user.
     *
     * @return The next command from the user
     */
    public Command getCommand()
    {
        String inputLine;   // will hold the full input line
        String word1 =null;
        String word2 = null;
        String word3 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
            }
            if(tokenizer.hasNext()) {
                word3 = tokenizer.next();      // get second word
                // note: we just ignore the rest of the input line.
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if(commands.isCommand(word1)) {
            return new Command(word1, word2,word3);
        }
        else {
            return new Command(null, word2, word3);
        }
    }

    public Command getName()
    {
        String input;
        String name = "";

        input = reader.nextLine();

        Scanner tokenizer = new Scanner(input);
        if(tokenizer.hasNext())
        {
            name = tokenizer.next();
        }

        return new Command(name, null,null);
    }
    /**
     * Print out a list of valid command words.
     */
    public String getCommands()
    {
        return commands.getCommandList();
    }
}
