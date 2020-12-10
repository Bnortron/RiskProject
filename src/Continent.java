import java.io.Serializable;
import java.util.ArrayList;

public class Continent implements Serializable
{
    // Basic continent information
    private String name; // Name of continent
    private int additionalTroops; // Additional troops given in reinforcement stage if whole continent owned
    private ArrayList<Country> residingCountries; // Countries residing in continent
    private Player owner; // Owner of every country in this continent

    /**
     * Continent Constructor
     *
     * @param name the name of the continent
     * @param countries a list of countries that are held inside the continent
     * @param bonus the amount of troops a player recieves in the reinforcement stage when every country in this continent is owned
     */
    public Continent(String name,ArrayList<Country> countries, int bonus)
    {
        this.name = name;
        this.additionalTroops = bonus;
        residingCountries = countries;
    }

    /**
     *
     * @return name of continent as String
     */
    public String getName()
    {
        return name;
    }

    /**
     *
     * @return amount of bonus troops a player recieves when they own the continent
     */
    public int getBonusTroops()
    {
        return additionalTroops;
    }

    /**
     *
     * @return a list of countries that make up the continent
     */
    public ArrayList<Country> getResidingCountries()
    {
        return residingCountries;
    }

    /**
     * Method that checks if a given country is one of the countries that make up this continent
     *
     * @param c the Country that is being checked
     * @return True if country is in continent, false if not
     */
    public boolean isInContinent(Country c)
    {
        for(int i=0;i<residingCountries.size();++i)
        {
            if(residingCountries.get(i).getName().equals(c.getName()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param p Player that's set as owner of the continent
     */
    void setOwner(Player p)
    {
        owner = p;
    }

    Player getOwner(){
        return owner;
    }
}