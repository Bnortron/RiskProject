import java.io.Serializable;
import java.util.ArrayList;

/**
 * Write a description of class Country here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Country implements Serializable
{
    // Basic Country information
    // Country name
    private String name;

    // Amount of troops in country
    private int troops;

    // True if a player owns this country
    private boolean owned;

    // Adjacent countries
    private ArrayList<Country> adjacents;

    // Owner
    private Player owner;


    /**
     * Constructor for objects of class Country
     */
    public Country(String name)
    {
        // Assign name
        this.name = name;

        // Initialize AL
        adjacents = new ArrayList<>();

        // Initially unowned & empty
        owned = false;
        troops = 0;

        // Testing
        //System.out.println("Created: " + name);
    }

    public void setTroops(int initialTroops)
    {
        owned = true;
        this.troops = initialTroops;
    }

    public void setAdjacents(ArrayList<Country> adjacents)
    {
        this.adjacents = adjacents;
    }

    public void addAdjacents(Country c){
        adjacents.add(c);
    }

    // Getter Methods
    public String getName()
    {
        return name;
    }

    public String toString(){return name;}

    public int getTroops()
    {
        return troops;
    }

    public boolean isOwned()
    {
        return owned;
    }

    void setOwner(Player p)
    {
        this.owner = p;
    }

    public Player getOwner()
    {
        return owner;
    }

    public ArrayList<Country> getAdjacents()
    {
        return adjacents;
    }

    // Adjusting amount of troops
    public void addTroops(int amount)
    {
        troops = troops + amount;
        //System.out.println(owner.getName() + " added " + amount + " troops to " + name + ".");
        //System.out.println("Troops in " + name + ": " + troops);
    }

    public void removeTroops(int amount)
    {
        troops = troops - amount;
        //owner.removeTroops(amount);
        //System.out.println(owner.getName() + " lost " + amount + " troops in " + name + ".");
        //System.out.println("Troops in " + name + ": " + troops);
    }
}
