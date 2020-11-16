import java.util.ArrayList;

public class Continent
{
    // Basic continent information
    private String name; // Name of continent
    private int additionalTroops; // Additional troops given in reinforcement stage if whole continent owned
    private ArrayList<Country> residingCountries; // Countries residing in continent

    public Continent(String name,ArrayList<Country> countries)
    {
        this.name = name;
        residingCountries = countries;
    }

    // Getters

    public String getName()
    {
        return name;
    }
    
    public ArrayList<Country> getResidingCountries()
    {
        return residingCountries;
    }
}