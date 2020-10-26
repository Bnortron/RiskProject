import java.util.*;

public class Player
{
    private String name;
    private int troops;
    private int turnPosition;

    private ArrayList<Country> capturedCountries;

    /**
     * Constructor
     *
     * @param name
     * @param troops
     * @param turnPosition
     */
    public Player(String name, int troops, int turnPosition)
    {
        this.name = name;
        this.troops = troops;
        this.turnPosition = turnPosition;
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

    public void getCapturedCountries(){
        System.out.println("Captured Countries by this player are: ");
        for(Country c : capturedCountries){
            System.out.println(c.toString());
        }
    }

    public void addCapturedCountry(Country c){
        capturedCountries.add(c);
    }

    public void removeCapturedCountry(Country c){
        capturedCountries.remove(c);
    }
}