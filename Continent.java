

import java.io.Serializable;
import java.util.ArrayList;

public class Continent implements Serializable
{
    // Basic continent information
    private String name; // Name of continent
    private int additionalTroops; // Additional troops given in reinforcement stage if whole continent owned
    private ArrayList<Country> residingCountries; // Countries residing in continent
    private Player owner; // Owner of every country in this continent
    private boolean owned;

    public Continent(String name,ArrayList<Country> countries, int bonus)
    {
        this.name = name;
        this.additionalTroops = bonus;
        residingCountries = countries;
    }

    // Getters
    public String getName()
    {
        return name;
    }

    public int getBonusTroops()
    {
        return additionalTroops;
    }

    public ArrayList<Country> getResidingCountries()
    {
        return residingCountries;
    }

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

    void setOwner(Player p)
    {
        this.owner = p;
    }
}