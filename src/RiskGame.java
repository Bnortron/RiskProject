import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.Observable;
import java.lang.StringBuilder;
import java.lang.NumberFormatException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;

/**
 * Model class for RiskGame that stores and processes game data
 *
 * @author Braden Norton
 * @version 11/17/20
 */
public class RiskGame
{
    /**
     * TO DO:
     *
     * 1. Write AI logic
     * 2. Set win conditions
     * 3. Test cases
     * 5. End of turn logic
     * 6. Better documentation
     * 7. Refined code/get rid of useless getters & setters
     * 8. Make GUI look better
     *
     */

    private int playerAmount;
    private int initialTroops;
    private int countryBonus;
    private int continentBonus;
    private int totalCountries = 42;
    private Player currentPlayer;
    private ArrayList<String> names;
    private ArrayList<String> fortifiableCountries;
    private ArrayList<Player> players;
    private ArrayList<Country> countries;
    private ArrayList<Continent> continents;

    // Initialization
    private boolean initialized = false;
    private boolean p1,p2,p3,p4,p5,p6;

    // For Reinforcement Phase
    private boolean reinforcementPhaseActive = false;

    // For Attack Phase
    private String[] d1 = {"1"};
    private String[] d2 = {"1", "2"};
    private String[] d3 = {"1", "2", "3"};
    private Country aCountry, dCountry;
    private String dCountryOwner;
    private int aTroops, dTroops, aDice, dDice, attLoss, defLoss, totalBattleRolls;
    // Original amount of troops in each country: aTroops, dTroops
    // Amount of troops involved in battle: aDice, dDice
    private ArrayList<Integer> aRolls; // Highest rolls that attacker had
    private ArrayList<Integer> dRolls; // Highest rolls that defender had
    private Dice attackerDice, defenderDice;
    private boolean attackPhaseActive = false;
    private boolean successfulAttack = false;

    // For Fortify Phase
    private int fortifyAmount;
    private String ccName, fcName;
    private Country cCountry, fCountry;
    private ArrayList<String> ownedAdjCountries;
    private boolean fortifyPhaseActive = false;

    /**
     * Constructor for objects of class RiskGame
     */
    public RiskGame()
    {
        names = new ArrayList<>();
        players = new ArrayList<>();
        continents = new ArrayList<>();
        countries = new ArrayList<>();
    }

    void addPlayer(String s)
    {
        names.add(s);
        System.out.println("Amount of players: " + names.size());
    }


    void initializeGame()
    {
        // Create Players
        int n = names.size();
        for(int i=0; i<n; ++i)
        {
            Player p = new Player(names.get(i), initialTroops, i);
            players.add(p);
            //System.out.println("Turn " + (i+1) + ": " + players.get(i).getName());
        }

        // Shuffle player names to establish random turn order
        Collections.shuffle(names);

        // Set current turn
        currentPlayer = players.get(0);

        // Set Countries & Continents
        // Set up countries
        try
        {
            setC_C();
            //System.out.println("Country Amount: " + countries.size());
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

    private void setC_C() throws Exception
    {
        Scanner sc = new Scanner(new File("resources/Country.txt"));

        while(sc.hasNextLine())
        {
            ArrayList<Country> c = new ArrayList<>();
            String s = sc.nextLine();
            //System.out.println(s);

            int i = s.indexOf("-");
            String firstWord = s.substring(0,i);
            String restOfLine = s.substring(i+2);

            String[] arrC = restOfLine.split(",");

            for(String t: arrC){
                //System.out.println(t);
                c.add(new Country(t));
                countries.add(new Country(t));
            }

            if(firstWord.equals("North America"))
            {
                Continent tempContinent = new Continent(firstWord, c,5);
                continents.add(tempContinent);
            }
            if(firstWord.equals("South America"))
            {
                Continent tempContinent = new Continent(firstWord, c,2);
                continents.add(tempContinent);
            }
            if(firstWord.equals("Europe"))
            {
                Continent tempContinent = new Continent(firstWord, c,5);
                continents.add(tempContinent);
            }
            if(firstWord.equals("Africa"))
            {
                Continent tempContinent = new Continent(firstWord, c,3);
                continents.add(tempContinent);
            }
            if(firstWord.equals("Asia"))
            {
                Continent tempContinent = new Continent(firstWord, c,7);
                continents.add(tempContinent);
            }
            if(firstWord.equals("Australia"))
            {
                Continent tempContinent = new Continent(firstWord, c,2);
                continents.add(tempContinent);
            }

        }

        for(Continent c: continents)
        {
            //System.out.println("Continent: " + c.getName());
            //System.out.println("Countries: " + c.getResidingCountries().size());
            //System.out.println("Bonus Troops: " + c.getBonusTroops());
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

    /**
     * Assign Countries To Player and Assign Troops to Each Country
     *
     * @author Tyler Leung
     */
    private void assignCountriesTroops(){
        ArrayList<Country> tempList = new ArrayList<>();
        tempList = countries;
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
     * Reinforcement Stage: The start of players turn, where bonus troops are alotted depending on two factors
     * 1. "Reinforcement Troops", which are proportional to # of territories held
     * 2. "Bonus Troops", which are given for holding every territory in a continent
     *
     * @author Braden Norton
     */
    void reinforcementStage(Player p)
    {
        System.out.println("Reinforcement Stage Active!");
        //Give reinforcement troops
        continentBonus(p);
        p.setContinentBonus(continentBonus);

        countryBonus(p);
        p.setCountryBonus(countryBonus);
    }

    /**
     * Attack Stage: Player can decide to attack adjacent countries
     *
     *
     * @author Braden Norton
     */
    void attackStage(String ac, String dc)
    {

        // Initialize
        attLoss = 0;
        defLoss = 0;
        successfulAttack = false;

        // Set Attacking & Defending Countries
        setAttackCountry(ac);
        setDefendCountry(dc);

        // Create Dice
        attackerDice = new Dice();
        defenderDice = new Dice();

        // Roll Dice
        attackerDice.rollDice(aDice);
        defenderDice.rollDice(dDice);

        // Add highest rolls to reference list
        aRolls = new ArrayList<>();
        aRolls.add(attackerDice.getHighest());
        dRolls = new ArrayList<>();
        dRolls.add(defenderDice.getHighest());

        // Defender Rolled 1 Dice
        // Compare Highest Rolls
        if(attackerDice.getHighest() > defenderDice.getHighest())
        {
            defLoss++;
        }
        else if(attackerDice.getHighest() < defenderDice.getHighest())
        {
            attLoss++;
        }
        else if(attackerDice.getHighest() == defenderDice.getHighest())
        {
            attLoss++;
        }

        // Defender & Attacker Rolled at least 2 Dice
        if(aDice > 1 && dDice > 1)
        {
            // Compare Second Highest Rolls
            attackerDice.removeHighest();
            aRolls.add(attackerDice.getHighest());

            defenderDice.removeHighest();
            dRolls.add(defenderDice.getHighest());
            if(attackerDice.getHighest() > defenderDice.getHighest())
            {
                defLoss++;
            }
            else if(attackerDice.getHighest() < defenderDice.getHighest())
            {
                attLoss++;
            }
            else if(attackerDice.getHighest() == defenderDice.getHighest())
            {
                attLoss++;
            }
        }

        // Adjust Troops
        aCountry.removeTroops(attLoss);
        dCountry.removeTroops(defLoss);

        // Adjust defending country if all defence is eliminated
        if(dCountry.getTroops() == 0)
        {
            successfulAttack = true;
            // Check if defending player is eliminated
            if(dCountry.getOwner().getCapturedCountries().size() == 0)
            {
                // Remove From Game
                System.out.println(dCountry.getOwner().getName() + " is eliminated!");
                players.remove(dCountry.getOwner().getTurnPosition());
            }
            // Set attacker as new owner of the country
            dCountry.getOwner().removeCapturedCountry(dCountry);
            currentPlayer.addCapturedCountry(dCountry);

            // Add remaining troops from the encounter to newly captured country
            // Logically: Attacking troops that weren't killed during battle will remain in that country once battle ends
            dCountry.addTroops(aDice);
            aCountry.removeTroops(aDice);
        }

        // Update Model with roll results
        updateModel();
    }

    /**
     * Fortify Stage
     *
     * @author Braden Norton
     */
    void fortifyStage(String cc, String fc, int i)
    {
        // Set current & fortified country
        setCurrentCountry(cc);
        setFortifiedCountry(fc);

        // Add troops to fortified country
        fortifyAmount = i;
        fCountry.addTroops(i);

        // Remove troops from current country
        cCountry.removeTroops(i);
    }

    /**
     * Ends current players turn and starts the turn of next player based on turn position
     * After all players have taken their turn, the turn positon resets back to the first player
     *
     * @author Braden Norton
     */
    void nextTurn()
    {
        int n = currentPlayer.getTurnPosition();
        // Last player's turn
        if(n+1 == playerAmount)
        {
            // Turn order back to start
            currentPlayer = players.get(0);
        }
        else
        {
            // Next players turn
            currentPlayer = players.get(n+1);
        }
    }

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

    /**
     * At the end of a phase, update the model values
     *
     * @author Braden Norton
     */
    void updateModel()
    {
        // Attack Phase Variables: aTroops, dTroops, aDice, dDice, totalBattleRolls
        if(attackPhaseActive)
        {
            System.out.println("\nAttack Phase Active");

            setACountryTroops();
            System.out.println("Attack Troops: " + aTroops);
            setDCountryTroops();
            System.out.println("Defence Troops: " + dTroops);

            // Set dice amount
            allowedAttDice(aTroops);
            System.out.println("Allowed Attack Dice: " + aDice);
            allowedDefDice(dTroops);
            System.out.println("Allowed Defence Dice: " + dDice);

            // Increment Total Rolls
            totalBattleRolls++;
        }
    }

    void setCurrentCountryName(String s) {ccName = s;}

    void setFortifiedCountryName(String s){fcName = s;}

    void setCurrentCountry(String s)
    {
        for(Country c: countries){if(c.getName().equals(s)){this.cCountry = c;}}
    }

    void setFortifiedCountry(String s)
    {
        for(Country c: countries){if(c.getName().equals(s)){this.fCountry = c;}}
    }

    String getCurrentCountryName(){ return ccName; }

    String getFortifiedCountryName() { return fcName; }


    /**
     * Initialization: Getters & Setters
     *
     *
     */
    void setPlayers(int n)
    {
        this.playerAmount = n;
        setInitialTroops(n);
    }

    /**
     * Sets the amount of troops each player starts with based on the total amount of players
     *
     * @param players, Total number of players
     *
     * @author Braden Norton
     */
    void setInitialTroops(int players)
    {
        if(players == 2){ initialTroops = 50;}
        else if(players == 3) {initialTroops = 35;}
        else if(players == 4) {initialTroops = 30;}
        else if(players == 5) {initialTroops = 25;}
        else if(players == 6) {initialTroops = 20;}
    }

    public int getPlayerAmount()
    {
        return playerAmount;
    }

    public ArrayList<String> getNames()
    {
        return names;
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public Player getCurrentTurn()
    {
        return currentPlayer;
    }

    /**
     * Reinforcement Getters & Setters
     *
     *
     */
    public void continentBonus(Player p)
    {
        for(int i=0; i<continents.size(); ++i)
        {
            if(p.getCapturedCountries().containsAll(continents.get(i).getResidingCountries()))
            {
                p.addTroops(continents.get(i).getBonusTroops());
                continentBonus = continents.get(i).getBonusTroops();
            }
        }
    }

    public void countryBonus(Player p)
    {
        if(p.getCapturedCountries().size() < 12)
        {
            p.addTroops(3);
            countryBonus = 3;
        }
        else
        {
            p.addTroops((p.getCapturedCountries().size()) / 3);
            countryBonus = ((p.getCapturedCountries().size()) / 3);
        }
    }


    /**
     * Attack Stage: Getters & Setters
     *
     *
     */

    public ArrayList<Country> getCurrentPlayerOC(){return currentPlayer.getCapturedCountries();}

    // Get amount of troops used to attack
    public int getAttackerTroops(){return aTroops;}

    // Get amount of troops in defending country
    public int getDefenderTroops() {return dTroops;}

    int getTroopsByName(String s)
    {
        for(Country c: countries)
        {
            if(c.getName().equals(s)){return c.getTroops();}
        }
        // Country not found
        return 0;
    }

    void setAttackCountry(String s)
    {
        for(Country c: countries){if(c.getName().equals(s)){this.aCountry = c;}}
    }

    void setDefendCountry(String s)
    {
        for(Country c: countries){if(c.getName().equals(s)){this.dCountry = c;}}
        dCountryOwner = this.dCountry.getOwner().getName();
    }

    public String getAttacker(){return aCountry.getName();}

    public String getDefender(){return dCountry.getName();}

    // Get Troop Amount in Att Country
    public int getACountryTroops(){return aCountry.getTroops();}

    // Get Troop Amount in Def Country
    public int getDCountryTroops() {return dCountry.getTroops();}

    void setACountryTroops() {aTroops = aCountry.getTroops();}

    void setDCountryTroops() {dTroops = dCountry.getTroops();}

    // Set amount of dice/troops attacker is using (from GUI input)
    void setAttDice(String s)
    {
        if(s.equals("3"))
        {
            aTroops = 3;
            aDice = 3;
        }
        else if(s.equals("2"))
        {
            aTroops = 2;
            aDice = 2;
        }
        else if(s.equals("1"))
        {
            aTroops = 1;
            aDice = 1;
        }
        System.out.println("Attacking with " + aDice + " troops/dice");
    }

    // Set amount of dice defender is using (from GUI input)
    void setDefDice(String s)
    {
        if(s.equals("2"))
        {
            dDice = 2;
        }
        else if(s.equals("1"))
        {
            dDice = 1;
        }
        System.out.println("Defence using " + dDice + " dice");
    }

    /**
     * Set the amount of dice the attacker can use depending on troop amount in the attacking country
     * Attacker can use up to maximum 3 dice, each dice represents 1 troop
     * Territories must be occupied at all times, thus there must be at least one troops left behind in the country initiating attack
     * Allowed attack troops from troops in attacking country:
     *
     * 4+ Troops in attacking country: Can attack w/3 troops = 3 dice
     * 3 Troops in attacking country: Can attack w/2 troops = dice
     * 2 Troops in attacking country: Can attack w/1 troop = 1 dice
     *
     * @param troops, amount of troops in attacking country
     *
     * @author Braden Norton
     */
    public String[] allowedAttDice(int troops)
    {
        if(troops > 4) {return d3;}
        else if(troops == 3){return d2;}
        else if(troops == 2){return d1;} // Troops = 2
        else{return null;}// not valid
    }

    /**
     * Set amount of dice the defender uses depending on troop amount in the defending country
     * Defender can use up to maximum 2 dice, each dice represents 1 troop
     * Defenders must have at least 2 troops in defending country to roll 2 dice
     * Allowed dice amount per troops in defending country:
     *
     * 2+ Troops in defending country: Allowed (up to) 2 dice
     * 1 Troops in defending country: Allowed 1 dice
     *
     * @param troops, amount of troops in defending country
     *
     * @author Braden Norton
     */
    public String[] allowedDefDice(int troops)
    {
        if(troops >= 2){return d2;}
        else{return d1;} // Troops = 1
    }

    int getAttDiceAmount() { return aDice; }

    int getDefDiceAmount() { return dDice; }

    int getAttLosses() { return attLoss; }

    int getDefLosses() { return defLoss; }

    int getTotalBattleRolls() { return totalBattleRolls; }

    void startAttackPhase() { attackPhaseActive = true; }

    void endAttackPhase() {attackPhaseActive = false;}

    String getDefendingCountryOwner()
    {
        return dCountryOwner;
    }

    /**
     * Determine all possible attackable countries for a given country
     *
     *
     * @param s, Name of country that you want to attack from
     * @return String[], list of countries that are eligible to be attacked from the given country
     *
     * @author Braden Norton
     */
    public String[] getAttackableCountries(String s)
    {
        ArrayList<String> acList = new ArrayList<>();
        ownedAdjCountries = new ArrayList<>();
        for(int i=0; i<getCurrentPlayerOC().size(); ++i)
        {
            if(getCurrentPlayerOC().get(i).getName().equals(s))
            {
                Country tc = getCurrentPlayerOC().get(i);
                //System.out.println(tc.getName()+" Selected: "+tc.getAdjacents().size()+" adjacents");
                for(int j=0; j<tc.getAdjacents().size(); ++j)
                {
                    Country tac = tc.getAdjacents().get(j);
                    //System.out.println(tac.getName() + " Selected (Owner: "+tac.getOwner().getName()+")");
                    if(tac.getOwner() != currentPlayer)
                    {
                        if(tc.getTroops() > 1)
                        {
                            acList.add(tac.getName());
                        }
                    }
                    else
                    {
                        // If you own the adjacent country
                        ownedAdjCountries.add(tac.getName());
                    }
                }
            }
        }
        String[] arr = acList.toArray(new String[acList.size()]);
        return arr;
    }

    int getARolls(int i)
    {
        return aRolls.get(i);
    }

    int getDRolls(int i)
    {
        return dRolls.get(i);
    }

    boolean getAttackResult()
    {
        return successfulAttack;
    }

    /**
     * Fortify Phase: Setters and Getters
     *
     *
     */
    ArrayList<String> getValidMovementAmount(String s)
    {
        // Run attackable adjacent countries method used in the attack phase
        getAttackableCountries(s);

        // List of all possible troop values
        int cTroop = getTroopsByName(s);
        ArrayList<String> temp = new ArrayList<>();
        System.out.println("Troops: " + cTroop);
        // Iterate until only 1 troop left in current country
        for(int i=0; i< (cTroop - 1); ++i)
        {
            int t = i+1;
            temp.add(t+"");
        }
        return temp;
    }

    ArrayList<String> getFortifiableCountries(String s)
    {
        getAttackableCountries(s);

        return ownedAdjCountries;
    }

    int getFortifiedAmount() { return fortifyAmount; }
}
