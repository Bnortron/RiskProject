import java.util.ArrayList;

public class Continent
{
    // Basic continent information
    private String name; // Name of continent
    private int additionalTroops; // Additional troops given in reinforcement stage if whole continent owned
    private ArrayList<Country> residingCountries; // Countries residing in continent

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
}