import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.io.FileReader;
import java.io.Reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

/**
 * Model class for RiskGame that stores and processes game data
 *
 * @author Braden Norton
 * @version 11/17/20
 */
public class RiskGame implements Serializable
{
    /**
     * TO DO:
     *
     * 1. Write AI logic (DONE)
     * 2. Set win conditions (DONE)
     * 3. Test cases (DONE)
     * 5. End of turn logic (DONE)
     * 6. Better documentation
     * 7. Refined code/get rid of useless getters & setters
     * 8. Make GUI look better
     *
     */

    private int playerAmount;
    private int initialTroops;
    private int totalCountries = 42;
    private Player currentPlayer;
    private ArrayList<String> names;
    private ArrayList<String> fortifiableCountries;
    private ArrayList<Boolean> aiPlayers;
    private ArrayList<Player> players;
    private ArrayList<Country> countries;
    private ArrayList<Continent> continents;
    private boolean validMap = false;

    // For Reinforcement Phase
    private boolean reinforcementPhaseActive = false;
    private String rCountry;
    private int countryBonus;
    private int continentBonus;
    private int totalBonus;
    private int troopsAdded;

    // For Attack Phase
    private String[] d1 = {"1"};
    private String[] d2 = {"1", "2"};
    private String[] d3 = {"1", "2", "3"};
    private Country aCountry, dCountry;
    private Player dCountryOwner;
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
    private boolean fortifyStageActive = false;

    //For Win Condition is met
    private boolean winner = false;

    /**
     * Constructor for objects of class RiskGame
     */
    public RiskGame()
    {
        names = new ArrayList<>();
        aiPlayers = new ArrayList<>();
        players = new ArrayList<>();

        continents = new ArrayList<>();
        countries = new ArrayList<>();
    }

    public ArrayList<Country> getCountries(){
        return countries;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    ArrayList<Player> getPlayers()
    {
        return players;
    }

    /**
     * Adds player names to an array list as Strings
     *
     * @param s: player name
     */
    void addPlayer(String s)
    {
        names.add(s);
        System.out.println("Amount of players: " + names.size());
    }

    /**
     * Sets up the players, turn order, the map (loads countries/continents/adjacencies from text file), & deployment phase (randomly assigns countries to each player, & troops randomly to those countries)
     *
     * @author Braden Norton
     * @author Braxton Martin
     * @author Tyler Leung
     *
     * @param ai: ArrayList of booleans that represent whether a given player is designated as an AI
     */
    void initializeGame(ArrayList<String> playerList, ArrayList<Boolean> ai)
    {
        // Create Players
        System.out.println("Players: " + playerAmount);
        for(int i=0; i<playerAmount; ++i)
        {
            Player p = new Player(playerList.get(i), initialTroops,i, ai.get(i));
            players.add(p);
            if(players.get(i).isAI())
            {
                System.out.println("Player " + players.get(i).getName() + " is AI.");
            }
        }

        // Shuffle player names to establish random turn order
        Collections.shuffle(players);

        // Set current turn
        currentPlayer = players.get(0);
        System.out.println("Turn Order:");
        for(Player p:players){ System.out.println(p.getName()); System.out.println(p.isAI()); }

        // Set Countries & Continents
        // Set up countries
        try
        {
            setupMap("resources/WorldMap.json");
            //System.out.println("Country Amount: " + countries.size());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        // Assign Countries & Troops
        assignCountriesTroops();

        // Start in reinforcement stage
        setReinforcementAmount();
    }

    /**
     * Parses a text file of continents/countries to an ArrayList, setting up the game map
     * First word in each line of the text file is a continent
     * the rest of the words in the line (separated by commas) are countries that reside within that continent
     *
     * This method adds creates a continent, which sets: The name, The countries it holds, Amount of bonus troops given for owning the whole continent
     * When a continent is added, all the countries are added to the game as well in an arraylist of 42 countries
     * Additionally, the countries residing in the continent are added to an ArrayList held within the continent class
     *
     *@author Braxton Martin
     *
     * @throws Exception: throws exception if text file not found
     */
    void setupMap(String fileName) throws Exception
    {
        JSONParser parser = new JSONParser();
        
        try{
           
            Reader reader = new FileReader(fileName);

            Object obj = parser.parse(reader); 
            JSONObject jsonObject = (JSONObject) obj;

            Set keys = jsonObject.keySet();

            System.out.println(keys); //Prints out [South America, Asia, Europe, Africa, Australia, North America]

            Object[] obArr = keys.toArray();

           // JSONObject[] jobArr = (JSONObject[])obArr;

           for(int i =0; i<obArr.length; i++){

            ArrayList<Country> c = new ArrayList<Country>();

            JSONObject continentObj = (JSONObject)jsonObject.get(obArr[i]);           
                       
            //System.out.println(continentObj.keySet()); //This prints out [bonusTroops, countries, numCountries]

            Set continentKeys = continentObj.keySet();

            Object[] continentArr = continentKeys.toArray();

            JSONObject countryJObj = (JSONObject)continentObj.get(continentArr[1]);


            Set countrySet = countryJObj.keySet();
            Object[] countryArr = countrySet.toArray();
            

            for(int j =0; j<countryArr.length; j++){
            
                Country newCountry = new Country(countryArr[j].toString());
                c.add(newCountry);
                countries.add(newCountry);

                //System.out.println(newCountry.getName());
            //JSONObject countryName = (JSONObject)continentObj.get(countryArr[1]);

            //System.out.println(countryJObj.keySet()); //Prints out "countries"

            }
            continents.add(new Continent(jsonObject.get(obArr[i]).toString(),c,Integer.parseInt(continentObj.get(continentArr[0]).toString()) ) );

        }
        for(int i =0; i<obArr.length; i++){

            JSONObject continentObj = (JSONObject)jsonObject.get(obArr[i]);           
                       
            //System.out.println(continentObj.keySet()); //This prints out [bonusTroops, countries, numCountries]

            Set continentKeys = continentObj.keySet();

            Object[] continentArr = continentKeys.toArray();

            JSONObject countryJObj = (JSONObject)continentObj.get(continentArr[1]);


            Set countrySet = countryJObj.keySet();
            Object[] countryArr = countrySet.toArray();
            for(int j =0; j<countryArr.length; j++){

                Set adjacentKeys = countryJObj.keySet();
                Object[] adjacentArr = adjacentKeys.toArray();

                for(int k = 0; k<adjacentArr.length; k++){

                    for(Country coun : countries){
                         if(countryArr[j].equals(coun.getName())){

                            for(Country c : countries){
                   
                                if(adjacentArr[k].equals(c.getName())){
                                    coun.addAdjacents(c);
                                }
                            }
                        }
                    }
                }
            }   
        }
    
    }catch(Exception e){
        e.printStackTrace();
    }
}
    

    public void checkMap(){
        if(continents.size()<0){
            if(countries.size()>players.size()){
                boolean check = true;
                for(Country c : countries){
                    System.out.println(c.getAdjacents().size());
                    if(c.getAdjacents().size()==0)check=false;
                }
                if(check)validMap = true;
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
    void reinforcementStage(int troops, String country)
    {
        // Change phase
        reinforcementPhaseActive = true;
        fortifyStageActive = false;

        // Set reinforced country
        this.rCountry = country;

        // Add troops to country & remove troops from total bonus reinforcements
        for(Country c: currentPlayer.getCapturedCountries())
        {
            if(c.getName().equals(country))
            {
                // Add troops to designated country
                c.addTroops(troops);

                // Remove troops from total reinforcement amount
                totalBonus -= troops;
            }
        }

        // End reinforcement phase when total bonus reinforcements = 0
        if(totalBonus==0)
        {
            reinforcementPhaseActive = false;
        }
    }

    /**
     * Attack Stage: Player can decide to attack adjacent countries using dice
     *
     * Rules:
     * Attacker & Defender must declare amount of dice they intent to roll before the battle starts
     * Compare all the rolled dice, the highest roll wins the encounter
     * If both the attacker & defender roll multiple dice, repeat above rule for the second highest dice
     * If the highest rolls from the attacker and defender are equal, the tie goes to the defender
     *
     * Results:
     * If the attacker wins the roll, the defender losses 1 troop from the territory under attack
     * If the defender wins the roll, the attacker losses 1 troop from the territory under attack
     * If the attacker eliminates all the defending troops in the territory under attack, he claims its ownership
     * If the attacker claims ownership of a territory, they must place at least 1 troop less than the amount of troops remaining in attacking territory
     * There is no limit on the amount of times a player can attack in a single turn
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

        //Checks If Defender Is AI
        if(dCountry.getOwner().isAI()){
            setAIDefDice();
        }

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
        aCountry.getOwner().removeTroops(attLoss);
        dCountry.removeTroops(defLoss);
        dCountry.getOwner().removeTroops(defLoss);

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
     * Fortify Stage: Player may move any number of troops from one territory to another adjacently placed territory
     *
     * Can only fortify one territory: Once the troops are moved, the players turn is over
     * The player is not required to fortify in order to end their turn
     *
     * @author Braden Norton
     */
    void fortifyStage(String cc, String fc, int i)
    {
        this.fortifyStageActive = true;
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
        int winningPlayer = checkWin();
        if(winningPlayer!= -1){
            winner = true;
        }
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
     * At the end of attack phase, update the model values
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

    public Player getCurrentTurn()
    {
        return currentPlayer;
    }

    public ArrayList<Continent> getContinents(){ return continents; }

    /**
     * Reinforcement Getters & Setters
     *
     *
     */
    public void continentBonus(Player p)
    {
        for(int i=0; i<continents.size(); ++i)
        {
            // Player owns all the countries in the continent
            if(p.getCapturedCountries().containsAll(continents.get(i).getResidingCountries()))
            {
                // Adjust Current Player
                p.addCapturedContinent(continents.get(i));
                p.addTroops(continents.get(i).getBonusTroops());

                // Adjust Continent ownership
                continents.get(i).setOwner(p);

                // Add troops to total bonus amount
                continentBonus += continents.get(i).getBonusTroops();
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

    void setReinforcementAmount()
    {
        // Enabled ReinforcementPhase
        reinforcementPhaseActive = true;

        //Give reinforcement troops
        continentBonus(currentPlayer);
        currentPlayer.setContinentBonus(continentBonus);

        countryBonus(currentPlayer);
        currentPlayer.setCountryBonus(countryBonus);

        this.totalBonus = currentPlayer.getCountryBonus() + currentPlayer.getContinentBonus();
    }

    ArrayList<String> getReinforcementAmount()
    {
        ArrayList<String> temp = new ArrayList<>();

        for(int i=0; i< totalBonus; ++i)
        {
            int t = i+1;
            temp.add(t+"");
        }
        return temp;
    }

    int getRemainingReinforcements()
    {
        return totalBonus;
    }

    String getReinforcedCountry()
    {
        return rCountry;
    }

    Boolean reinforcementPhaseActive()
    {
        return reinforcementPhaseActive;
    }

    /**
     * Attack Stage: Getters & Setters
     *
     *
     */
    public ArrayList<Country> getCurrentPlayerOC(){return currentPlayer.getCapturedCountries();}

    public String getAttackingPlayer(){ return aCountry.getOwner().getName(); }

    public String getDefendingPlayer(){ return dCountry.getOwner().getName(); }

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
        dCountryOwner = this.dCountry.getOwner();
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

    void setAIDefDice(int n)
    {
        dDice = n;
        System.out.println("Defence using " + dDice + " dice");
    }

    /**
     * Set the amount of dice the attacker can use depending on troop amount in the attacking country
     * Attacker can use up to maximum 3 dice, each dice represents 1 troop
     * Territories must be occupied at all times, thus there must be at least one troops left behind in the country initiating attack
     * Allowed attack troops from troops in attacking country:
     *
     * 4+ Troops in attacking country: Can attack w/3 troops = 3 dice
     * 3 Troops in attacking country: Can attack w/2 troops = 2 dice
     * 2 Troops in attacking country: Can attack w/1 troop = 1 dice
     *
     * @param troops, amount of troops in attacking country
     *
     * @author Braden Norton
     */
    public String[] allowedAttDice(int troops)
    {
        if(troops > 3) {return d3;}
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

    Player getDefendingCountryOwner()
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
     * Checking to see if a win has occured
     *
     * @author Braxton Martin
     */
    public int checkWin(){
        for(Player p : players){
            int playerNum =0;
            if(p.numCapturedCountries() == countries.size()) return playerNum; //Returns the player's index that won
            playerNum++;
        }
        return -1; //No one wins yet
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

    Boolean fortifyStageActive() { return fortifyStageActive; }

    /**
     * Generates random country if AI is the current player
     *
     * @author Tyler Leung
     * @return name of random country
     */
    public String randomAICountry(){
        int numAICountry = currentPlayer.numCapturedCountries();
        Random rnd = new Random();
        int rndNum = rnd.nextInt(numAICountry);
        String randomCountry = currentPlayer.getCapturedCountries().get(rndNum).getName();
        return randomCountry;
    }

    /**
     * Set attack/defend country and attack/defend troops for AI Attack
     *
     * @author Tyler Leung
     */
    public void setACDC(){
        String ac = randomAICountry();
        setAttackCountry(ac);
        System.out.println(getAttacker());
        Random rndIndex = new Random();
        int rndDefend = rndIndex.nextInt(getAttackableCountries(ac).length);
        String dc = getAttackableCountries(ac)[rndDefend];
        setDefendCountry(dc);
        System.out.println(getDefender());
        setACountryTroops();
        setDCountryTroops();
    }

    /**
     * Attack Logic For AI Attacking
     *
     * @author Tyler Leung
     */
    public void aiAttackStage(){

        //Re-Initialize For AI Attack
        attLoss = 0;
        defLoss = 0;
        successfulAttack = false;

        //Create New Dice
        attackerDice = new Dice();
        defenderDice = new Dice();

        //Roll Dice
        setAIAtkDice();
        attackerDice.rollDice(aDice); //Select Max Num Dice
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
     * @author Tyler Leung
     * @return Num Dice Used To Defend
     */
    public void setAIDefDice(){
        if(dCountry.getTroops() >= 2){
            dDice = 2;
        }else {
            dDice = 1;
        }
    }

    /**
     * @author Tyler Leung
     * @return Num Dice Used To Attack
     */
    public void setAIAtkDice(){
        if(aCountry.getTroops() > 4) {
            aDice = 3;
        }
        else if(aCountry.getTroops() == 3){
            aDice = 2;
        }
        else {
            aDice = 1;
        }
    }


    /**
     * @author Tyler Leung
     * @author Braxton Martin
     * This function is the random fortification for an AI
     */
    public void aiFortify(){
        //Select Random Country
        String fortifyCountry = randomAICountry();
        
        //Find All Adjacent Country
        ArrayList<String> fortifiableCountries = getFortifiableCountries(fortifyCountry);
        //Check If Random Country Has Less Than 2 Troops
        setCurrentCountry(fortifyCountry);
        System.out.println(cCountry);
        System.out.println(cCountry.getTroops());
        if(cCountry.getTroops() > 2 ){ //if random country has less than 2 troops
            //Find First Adjacent That Has More Than 2
            for(Country c : currentPlayer.getCapturedCountries()){ //for each country owned
                for(String fC : fortifiableCountries){ //for each adjacent country
                    if(c.getName().equals(fC) && c.getTroops() < 2){ //if the fortifiable country has more than 2 troops
                        setFortifiedCountry(fC); //set country to be fortified
                        //Transfer Troops Until Random Country Has 2
                        if(cCountry.getTroops() == 2){ //If Country Has 2 Troops
                            break;
                        }else {
                            fCountry.addTroops(1);
                            cCountry.removeTroops(1);
                        }
                    }
                }
            }
        }
    }

    /**
     * @author Braxton Martin
     * @author Tyler Leung
     * The random reinforcement of a country by AI
     */
    public void aiReinforce(){
        //Select Random Country
        //Select Random Number of Units From 0 -  Bonus To Add
        //Add Random Number To Random Country
        //Subtract Random Number From Total Amount
        setReinforcementAmount();
        while (totalBonus > 0){
            rCountry = randomAICountry();
            Random rInt = new Random();
            troopsAdded = rInt.nextInt(totalBonus) + 1;
            System.out.println(troopsAdded);
            System.out.println(rCountry);
            for(Country c: currentPlayer.getCapturedCountries())
            {
                if(c.getName().equals(rCountry))
                {
                    // Add troops to designated country
                    c.addTroops(troopsAdded);

                    // Remove troops from total reinforcement amount
                    totalBonus -= troopsAdded;
                }
            }
            System.out.println(totalBonus); 
        }
    }

    public int getReinforcedAI(){
        return troopsAdded;
    }
}