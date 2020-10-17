import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import java.lang.StringBuilder;
import java.lang.NumberFormatException;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RiskGame
{
    // Game Parser
    private RiskParser parser;

    // Reading text file variables
    private StringBuilder sb;
    private BufferedReader reader;

    // Int variables
    private int playerAmount; // Amount of players in game
    private int turnIndex; // Turn number
    private int aDice; // Attacker dice
    private int dDice; // Defender dice
    private int initialTroops;
    private int troops;
    private int aTroops; // Amount of attacker troops
    private int dTroops; // Amount of defender troops
    private int aTroopsLost; // Amount of troops the attacker lost
    private int dTroopsLost; // Amount of troops the defender lost

    // Boolean variables
    private boolean quitGame; // True = Exit the game
    private boolean playersSelected; // True = The amount of players have been accepted
    private boolean boardLoaded;
    private boolean reinforcementStage; // True = turn is in reinforcement stage
    private boolean attackStage; // True = turn is in attack stage
    private boolean moveStage; // True = turn is in move stage (final stage)
    private boolean territoryAttackable; // True = the territory can be attacked
    private boolean territoryReinforceable; // True = the territory can be reinforced

    // String variables
    private String country;
    private String countryAName;
    private String countryBName;
    private String countryList = "country.txt"; // List of all countries in the game
    private String continentsList = "continents.txt"; // List of all continents in the game
    private String adjacentList = "adjacent.txt"; // List of all adjacent countries in the game

    // String lists
    private String[] countriesArray;
    private String[] continentsArray = {"North America", "South America", "Europe", "Africa", "Asia", "Australia"};
    private String[] adjacentsArray;
    private String[] territoryCaptured; // List of country names that have been captured
    private String[] territoryOpen; // List of country names that haven't been captured

    // ArrayLists
    private ArrayList<String> names; // List of player names
    private ArrayList<Player> players; // List of player objects
    private ArrayList<Country> countries; // List of country objects
    private ArrayList<Continent> continents; // List of continent objects

    // Random variable for dice
    private Random rand;

    // Objects
    private RiskBoard board;
    private Dice dice;
    private Player currPlayer;
    private Country countryA;
    private Country countryB;

    /**
     * Constructor
     */
    public RiskGame()
    {
        parser = new RiskParser();
    }

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
            System.out.println("Main game loop entered..\n");
            Command command = parser.getCommand();
            quitGame = processCommand(command);
        }

        // Quit option selected
        System.out.println("Game quit! Thanks for playing Risk!");
    }

    public void initializeGame()
    {
        // Create player list
        players = new ArrayList<Player>();

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
            setCountries();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        // Set up adjacent countries
        try
        {
            setAdjacents();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        // Load RiskBoard with country, adjacents, and continent arrays
        boardLoaded = board.load(countriesArray, adjacentsArray, countriesArray);

        // Start game at reinforcement stage
    }

    public void mainMenu()
    {
        System.out.println("Welcome to the game of Risk!");
        System.out.println("Choose the number of players (2-6): \n");
    }

    private boolean processCommand(Command command)
    {
        // Boolean to quit game when true
        boolean quitRequested = false;

        // Get command word
        String commandWord = command.getCommandWord();

        // Commands
        if(commandWord.equals("Q"))
        {
            quitRequested = quit(command);
        }

        // True = game ends
        return quitRequested;
    }

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

    private void setCountries() throws Exception
    {
        Scanner sc = new Scanner(new File("resources/Country.txt"));
        StringBuilder countrySB = new StringBuilder();
        StringBuilder continentSB = new StringBuilder();

        while(sc.hasNextLine())
        {
            String s = sc.nextLine();

            int i = s.indexOf("");
            String firstWord = s.substring(0,i);
            String restOfLine = s.substring(i);

            continentSB.append(firstWord);
            countrySB.append(restOfLine);
        }

        // Create array of Country names
        String countryInputs = countrySB.toString();
        countriesArray = countryInputs.split(" , ");
        System.out.println("Countries added: " + countryInputs);
    }

    private void setAdjacents() throws Exception
    {
        Scanner sc = new Scanner(new File("resources/Adjacent.txt"));
        StringBuilder adjacentSB = new StringBuilder();

        while(sc.hasNextLine())
        {
            String s = sc.nextLine();
            adjacentSB.append(s);
        }

        // Create array of adjacent countries
        String adjacentInputs = adjacentSB.toString();
        adjacentsArray =  adjacentInputs.split("\t");
        System.out.println("Adjacents added: " + adjacentInputs);
    }

    private void setInitialTroops(int players)
    {
        if(players == 2){ initialTroops = 50;}
        else if(players == 3) {initialTroops = 35;}
        else if(players == 4) {initialTroops = 30;}
        else if(players == 5) {initialTroops = 25;}
        else if(players == 6) {initialTroops = 20;}
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
}

