import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

/**
 * @author Braden Norton
 * @author Tyler Leung
 * @author Braxton Martin
 */
public class RiskGame
{
    // Game Parser
    private RiskParser parser;

    // Int variables
    private int playerAmount; // Amount of players in game    
    private int turnIndex = 1; // Turn number
    private int attackRoll;
    private int defendRoll;
    private int initialTroops;
    private int troops;
    private int aTroops; // Amount of attacker troops
    private int dTroops; // Amount of defender troops
    private int aTroopsLost; // Amount of troops the attacker lost
    private int dTroopsLost; // Amount of troops the defender lost
    private int currTurnNum= 0;
    private int currPlayerNum;
    private int totalCountries = 42;

    // Boolean variables
    private boolean quitGame; // True = Exit the game
    private boolean playersSelected; // True = The amount of players have been accepted
    private boolean boardLoaded;
    private boolean reinforcementStage; // True = turn is in reinforcement stage
    private boolean attackStage; // True = turn is in attack stage
    private boolean moveStage; // True = turn is in move stage (final stage)
    private boolean territoryAttackable; // True = the territory can be attacked
    private boolean territoryReinforceable; // True = the territory can be reinforced

    // String lists
    private String[] territoryCaptured; // List of country names that have been captured
    private String[] territoryOpen; // List of country names that haven't been captured

    // ArrayLists
    private ArrayList<String> names; // List of player names
    private ArrayList<Player> players; // List of player objects
    private ArrayList<Country> countries; // List of country objects
    private ArrayList<Continent> continents; // List of continent objects

    // Objects
    private RiskBoard board;
    private Dice aDice;
    private Dice dDice;
    private Player currPlayer;
    private Country countryA;
    private Country countryB;

    /**
     * @author Braden Norton, Tyler Leung, Braxton Martin
     * Constructor
     */
    public RiskGame()
    {
        parser = new RiskParser();
        continents = new ArrayList<>();
        countries = new ArrayList<>();
    }

    /**
     * @author Braden Norton
     */
    public void playRisk()
    {
        // Set booleans
        quitGame = false;
        playersSelected = false;

        // Print main menu
        mainMenu();

        // Player selection loop
        while(! playersSelected)
        {
            Command command = parser.getCommand();
            playersSelected = processPlayers(command);
        }

        // Number of players chosen
        System.out.println("New game for " + playerAmount + " players!");
        System.out.println("Enter player names");

        // Get player names
        names = new ArrayList<String>();
        for(int i = 0; i < playerAmount; ++i)
        {
            System.out.println("Player " + (i+1) + ":");
            Command command = parser.getName();
            String commandName = command.getCommandWord();
            names.add(commandName);
        }

        // Player names chosen
        System.out.println("Names chosen!");

        // Initialize game
        System.out.println("Initializing game...\n");
        initializeGame();

        // Main game loop
        while(! quitGame)
        {
            printTurnInfo();
            System.out.println("Command: ");
            Command command = parser.getCommand();
            quitGame = processCommand(command);
        }

        // Quit option selected
        System.out.println("Game quit! Thanks for playing Risk!");
    }

    /**
     * @author Braden Norton
     * @author Braxton Martin
     * @author Tyler Leung
     */
    public void initializeGame()
    {
        // Create player list
        players = new ArrayList<Player>();
        setInitialTroops(names.size());
        // Create players and add them to player list
        for(int i = 0; i < names.size(); ++i)
        {
            // Create new player and add them to players ArrayList
            players.add(new Player(names.get(i),initialTroops,i));
            System.out.println(names.get(i) + " added!");
        }

        // Initialize Board
        board = new RiskBoard();
        boardLoaded = false;

        // Set up countries
        try
        {
            setC_C();
        }
        catch(Exception e)
        {
        }

        
        // Set up adjacent countries
        try
        {
            setAdjacents();
        }
        catch(Exception e)
        {
        }
        
    

        // Load RiskBoard with country, adjacents, and continent arrays
        // boardLoaded = board.load(countriesArray, continentsArray, continentsArray);
     
        assignCountriesTroops();
        
    }

    /**
     * Assign Countries To Player and Assign Troops to Each Country
     * 
     * @author Tyler Leung
     */
    private void assignCountriesTroops(){
        ArrayList<Country> tempList = countries; 
        Collections.shuffle(tempList); //Randomly Shuffle List
        while (totalCountries != 0){
            for(Player p : players){
                if(totalCountries > 0){
                    p.addCapturedCountry(tempList.get(totalCountries-1));
                    totalCountries--;
                }
            }
        }
        for(Player p : players){
            int assignTroops = p.getTroops();
            while (assignTroops != 0){
                for(Country c : p.getCapturedCountries()){
                    if(assignTroops > 0){
                        c.addTroops(1);
                        assignTroops--;
                    }
                }
            }
        }
    }

    /**
     * @author Braden Norton
     */
    public void mainMenu()
    {
        System.out.println("Welcome to the game of Risk!");
        System.out.println("Choose the number of players (2-6): \n");
    }

    /**
     * @author Braden Norton
     * @author Braxton Martin
     * @author Tyler Leung
     * @param command
     * @return
     */
    private boolean processCommand(Command command)
    {
        // Boolean to quit game when true
        boolean quitRequested = false;

        // Get command word
        String commandWord = command.getCommandWord();

        // Commands
        if(command.isUnknown()){
            return false;
        }
        if(commandWord.equals("Q")){
              quitRequested = quit(command);
        } else if (commandWord.equals("A")){
            attackStage(command);
        }
        // True = game ends
        return quitRequested;
    }

    /**
     * @author Braden Norton
     * @param command
     * @return
     */
    private boolean processPlayers(Command command)
    {
        // True when players chosen
        boolean pSelected = false;

        // Test if command is valid
        if(command.isUnknown()) {
            System.out.println("You must enter a number between 2-6\n");
            return false;
        }

        // Get command word
        String commandWord = command.getCommandWord();

        // Parse to int value
        int num = Integer.parseInt(commandWord);

        // Process command
        pSelected = selectPlayers(num);

        return pSelected;
    }

    /**
     * @author Braden Norton
     * @param amount
     * @return
     */
    public boolean selectPlayers(int amount)
    {
        // Check if number is between 2 and 6
        if(amount >= 2 && amount <= 6)
        {
            // Number entered is valid
            playerAmount = amount;
            return true;
        }
        else
        {
            // Number invalid
            System.out.println("You must enter a number between 2 and 6\n");
            return false;
        }
    }

    /**
     * @author Braxton Martin
     * @author Tyler Leung
     * @throws Exception
     */
    private void setC_C() throws Exception
    {
        Scanner sc = new Scanner(new File("resources/Country.txt"));
       
        while(sc.hasNextLine())
        {
            ArrayList<Country> c = new ArrayList<>();
            String s = sc.nextLine();
            System.out.println(s);

            int i = s.indexOf("-");
            String firstWord = s.substring(0,i);
            String restOfLine = s.substring(i+2);

            String[] arrC = restOfLine.split(",");


            for(String t: arrC){
                System.out.println(t);
                c.add(new Country(t));
                countries.add(new Country(t));
            }
            
            Continent tempContinent = new Continent(firstWord, c);
            continents.add(tempContinent);
                        
        }
        
        sc.close();
    }

    /**
     * @author Braxton Martin
     * @author Tyler Leung
     * @throws Exception
     */
    private void setAdjacents() throws Exception
    {
        Scanner sc = new Scanner(new File("resources/Adjacent.txt"));

        while(sc.hasNextLine())
        {
            String s = sc.nextLine();

            int i = s.indexOf("-");
            String firstWord = s.substring(0,i);
            String restOfLine = s.substring(i+2);

            String[] arr = restOfLine.split(",");
            for(Country coun : countries){
                if(firstWord.equals(coun.getName())){
                    for(Country c : countries){
                        for(String adj : arr){
                            if(adj.equals(c.getName())){
                                coun.addAdjacents(c);
                            }
                        }
                    }
                }
            }
        }
    }

    private void setInitialTroops(int players)
    {
        if(players == 2){ initialTroops = 50;}
        else if(players == 3) {initialTroops = 35;}
        else if(players == 4) {initialTroops = 30;}
        else if(players == 5) {initialTroops = 25;}
        else if(players == 6) {initialTroops = 20;}
    }

    private void printTurnInfo(){
        //print whos turn
        //print turn number
    }
    private void changeTurn(){
        //change turn
    }

    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?" + "\n");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * check if you can attack possible country
     * 
     * UNFINISHED
     * 
     * @author Braxton Martin
     */
    private boolean checkAdjacents(String defCountry, String attCountry){
        Country dc = board.getCountry(defCountry);
        Country ac = board.getCountry(attCountry);
        for(Country con : ac.getAdjacents()){
            if(con==dc)return true;
            return false;
        }
            
        return false;
    }

    /**
     * Check if you are attacking adjacent 
     * 
     * @author Tyler Leung
     * @param command
     */
    private void attackStage(Command command){
        if(!command.hasSecondWord()) {
            System.out.println("Attack WHO from WHERE?");
        } else if(!command.hasThirdWord()){
            System.out.println("Attack who from WHERE?");
        }else if(!checkAdjacents(command.getSecondWord(),command.getThirdWord())){
            System.out.println("Invalid Adjacent Country to Attack. Input another one.");
            System.out.println("Here is a list of valid countries: ");
            //GET LIST OF VALID ADJACENTS TO ATTACK
        } else {
            //Ask how many dices attacker would like to use
            System.out.println("How many dice would the attacker like to use?");
            Command aNumDice = parser.getCommand();
            
            //Create new dice object and roll those dice if valid attack
            aDice = new Dice();
            if(validAttack(Integer.parseInt(aNumDice.getCommandWord()))){
                aDice.rollDice(Integer.parseInt(aNumDice.getCommandWord()));
            }
            aDice.printRolls();
            
            //Ask how many dices defender would like to use
            System.out.println("How many dice would the defender like to use?");
            Command dNumDice = parser.getCommand();

            //Create new dice object and roll those dice
            dDice = new Dice();
            if(validDefend(Integer.parseInt(dNumDice.getCommandWord()))){
                dDice.rollDice(Integer.parseInt(dNumDice.getCommandWord()));
            }
            dDice.printRolls();

            //Compare Highest Rolls
            compareDice(aDice.getHighest(),dDice.getHighest());
            //If both attack and defend used more than 1 dice, remove highest and compare again.
            if((Integer.parseInt(aNumDice.getCommandWord()) > 1) && (Integer.parseInt(dNumDice.getCommandWord()) > 1)){
                aDice.removeHighest();
                dDice.removeHighest();
            }
            compareDice(aDice.getHighest(),dDice.getHighest());

            //MAYBE ADD SOMETHING TO SHOW NUMBER OF ARMIES REMAINING AFTER ATTACK
        }
    }

    private void nextTurn(){
        currTurnNum = (currTurnNum + 1)%players.size();
        currPlayer = players.get(currTurnNum);
    }

    /**
     * Checks if attack is valid. 
     * @author Tyler Leung
     * @param numArmy
     * @return true if valid attack
     */
    private boolean validAttack(int numArmy){
        if (numArmy <= 0){ //cant attack with 0
            return false;
        } else if(numArmy > aTroops){ //cant attack with more armies than you have
            return false;
        } else { //possible attack
            return true;
        }
    }

    /**
     * Checks if defense is valid
     * @author Tyler Leung
     * @param numArmy number of armies used to defend
     * @return true if valid defend
     */
    private boolean validDefend(int numArmy){
        if (numArmy > 2){ //max 2 armies
            return false;
        } else if (numArmy < 0){ //cant not defend
            return false;
        } else if (numArmy > dTroops){ //cant attack with 2 if only 1 army
            return false;
        } else{
            return true;
        }
    }

    /**
     * @author Tyler Leung
     * @param aHigh highest from attack roll
     * @param dHigh highest from defend roll
     */
    private void compareDice(int aHigh, int dHigh){
        if (aHigh > dHigh){
            System.out.println("Attackers win! Defenders lose 1 army.");
            dTroops -= 1;
        } else if (aHigh == dHigh){
            System.out.println("It was a tie! Attackers lose 1 army.");
            aTroops -= 1;
        } else {
            System.out.println("Defenders win! Attackers lose 1 army.");
            aTroops -= 1;
        }
    }
}