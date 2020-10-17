import java.util.ArrayList;

public class Continent
{
    // Basic continent information
    private String name; // Name of continent
    private int additionalTroops; // Additional troops given in reinforcement stage if whole continent owned
    private ArrayList<Country> residingCountries; // Countries residing in continent

    public Continent(String name, int additionalTroops, ArrayList<Country> countries)
    {
        this.name = name;
        this.additionalTroops = additionalTroops;
        residingCountries = countries;
    }

    // Getters

    public String getName()
    {
        return name;
    }

    public int getAdditionalTroops()
    {
        return additionalTroops;
    }

    public ArrayList<Country> getResidingCountries()
    {
        return residingCountries;
    }
}
