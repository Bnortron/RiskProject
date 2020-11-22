import java.util.*;

public class Player
{
    private String name;
    private int troops;
    private int turnPosition;
    private int countryBonus;
    private int continentBonus;
    private boolean AI;

    private ArrayList<Country> capturedCountries;

    /**
     * Constructor
     *
     * @param name
     * @param troops
     * @param turnPosition
     */
    public Player(String name, int troops, int turnPosition, boolean ai)
    {
        this.name = name;
        this.troops = troops;
        this.turnPosition = turnPosition;
        this.AI = ai;
        capturedCountries = new ArrayList<>();
    }

    /**
     * Getter for name of player
     *
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * Getter for players turn position
     *
     * @return
     */
    public int getTurnPosition()
    {
        return turnPosition;
    }

    /**
     * Getter for amount of troops this player has
     *
     * @return
     */
    public int getTroops()
    {
        return troops;
    }

    void addTroops(int n)
    {
        troops = troops + n;
    }

    void removeTroops(int n)
    {
        troops = troops - n;
    }

    public ArrayList<Country> getCapturedCountries()
    {
        return capturedCountries;
    }

    public ArrayList<String> capturedCountriesToString()
    {
        ArrayList<String> temp = new ArrayList<>();
        for(Country c : capturedCountries){
            temp.add(c.getName());
        }
        return temp;
    }

    public void printCapturedCountries()
    {
        System.out.println("Captured Countries by this player are: ");
        for(Country c : capturedCountries){
            System.out.println(c.getName());
        }
    }

    public void addCapturedCountry(Country c)
    {
        capturedCountries.add(c);
        c.setOwner(this);
    }

    public void removeCapturedCountry(Country c){
        capturedCountries.remove(c);
    }

    void setCountryBonus(int n)
    {
        this.countryBonus = n;
        System.out.println("Country Bonus: " + countryBonus);
    }

    void setContinentBonus(int n)
    {
        this.continentBonus = n;
        System.out.println("Continent Bonus: " + continentBonus);
    }

    int getCountryBonus()
    {
        return this.countryBonus;
    }

    int getContinentBonus()
    {
        return this.continentBonus;
    }

    boolean isAI()
    {
        return AI;
    }
}