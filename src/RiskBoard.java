import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RiskBoard
{
    // Boolean variables
    private boolean boardLoaded;
    private boolean adjacent;

    // String lists
    private String[] continentNames;

    // ArrayLists
    private ArrayList<Country> countryList;
    private ArrayList<Country> continentCountries;
    private ArrayList<Country> openCountry;

    // HashMaps
    public HashMap<String, Country> mapCountries;
    private HashMap<String, Continent> mapContinents;

    /**
     * Constructor for objects of class RiskBoard
     */
    public RiskBoard()
    {
        // initialise instance variables
    }

    public boolean load(String[] countries, String[] adjacents, String[] continents)
    {
        // Not loaded
        boardLoaded = false; // Board not loaded yet

        // Initialize HashMaps
        mapCountries = new HashMap<String, Country>();
        mapContinents = new HashMap<String, Continent>();

        // Initialize ArrayLists
        countryList = new ArrayList<Country>();
        continentCountries = new ArrayList<Country>();
        openCountry = new ArrayList<Country>();

        // Board loaded
        boardLoaded = true;
        return boardLoaded;
    }

    public void loadCountries(String[] countries)
    {
        for(int i = 0; i<countries.length; ++i)
        {
            // Add new country to HashMap (Name, Country Object)
            mapCountries.put(countries[i], new Country(countries[i]));
        }
    }

    public void addContinents(Continent continent)
    {
        // Use a .txt file that contains all the continents and the countries in them
        // The format is as follows:
        // Continent1, country, country, ...
        // Continent2, country, country, ...
        // ...
        // New line each continent
        // Words separated by commas
            mapContinents.put(continent.getName(), continent);
    }

    public Country getCountry(String country){
        return(mapCountries.get(country));
    }

}