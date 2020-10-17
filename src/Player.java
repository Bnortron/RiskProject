/**
 *
 */
public class Player
{
    private String name;
    private int troops;
    private int turnPosition;

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
}