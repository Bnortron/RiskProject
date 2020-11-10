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


/**
 * @author Braden Norton
 * @author Tyler Leung
 * @author Braxton Martin
 */
public class RiskGame
{
    // Reading text file variables
    private StringBuilder sb;
    private BufferedReader reader;

    // Int variables
    private int playerAmount; // Amount of players in game    private int turnIndex = 1; // Turn number
    private int attackRoll;
    private int defendRoll;
    private int initialTroops;
    private int troops;
    private int aTroops; // Amount of attacker troops
    private int dTroops; // Amount of defender troops
    private int aTroopsLost; // Amount of troops the attacker lost
    private int dTroopsLost; // Amount of troops the defender lost
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
    private String[] countriesArray;
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
     * Constructor
     */
    public RiskGame()
    {
        continents = new ArrayList<>();
        countries = new ArrayList<>();
    }

    public void initialize(int n, ArrayList<String> s)
    {
        // List of player names
        names = new ArrayList<String>();
        this.names = s;

        // Set number of players
        setPlayers(n);
        System.out.println("Players set!" + "(" + getPlayerAmount() + ")");

        // Set initial troops
        setInitialTroops(n);
        System.out.println("Initial Troops set!" + "(" + getInitialTroops() + ")");

        // Create players
        players = new ArrayList<Player>();
        for(int i = 0; i < n; ++i)
        {
            Player p = new Player(names.get(i), getInitialTroops(), i);
            players.add(p);
            System.out.println("Turn Order: " + players.get(i).getName());
        }
        currPlayer = players.get(0);

        // Initialize Board
        board = new RiskBoard();
        boardLoaded = false;

        // Set up countries
        try
        {
            setC_C();
            System.out.println(countries.size());
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

        // Assign Countries & Troops
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
     * Takes data from GUI and sets the amount of players
     */
    public void setPlayers(int n)
    {
        this.playerAmount = n;
        setInitialTroops(n);
    }

    /**
     * Gets the amount of players.
     *
     * @return int amount of players
     */
    public int getPlayerAmount()
    {
        return playerAmount;
    }

    public Player getCurrentPlayer()
    {
        return currPlayer;
    }

    public void nextTurn()
    {

    }

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

    public int getInitialTroops()
    {
        return initialTroops;
    }

    /**
     * check if you can attack possible country
     *
     * UNFINISHED
     *
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
     *
     * @param command
     */
    /**
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
     **/

    /**
     * Checks if attack is valid.
     *
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
