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
    private String[] adjacentCountries;

    // ArrayLists
    private ArrayList<Country> countryList;
    private ArrayList<Country> adjList;
    private ArrayList<Country> continentCountries;
    private ArrayList<Country> openCountry;

    // HashMaps
    private HashMap<String, Country> mapCountries;
    private HashMap<String, Continent> mapContinents;

    /**
     * Constructor for objects of class RiskBoard
     */
    public RiskBoard()
    {
        // initialise instance variables
    }

    public boolean load(String[] countries, String[] continents, String[] adjacents)
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

        // Load countries
        loadCountries(countries);

        // Load adjacent countries
        loadAdjacents(adjacents);

        // Load the continents
        loadContinents(continents);

        // Board loaded
        boardLoaded = true;
        return boardLoaded;
    }

    private void loadCountries(String[] countries)
    {
        for(int i = 0; i<countries.length; ++i)
        {
            // Add new country to HashMap (Name, Country Object)
            mapCountries.put(countries[i], new Country(countries[i]));

            // Add Country to Country ArrayList
            countryList.add(mapCountries.get(countries[i]));
        }
    }

    private void loadContinents(String[] continents)
    {
        // Use a .txt file that contains all the continents and the countries in them
        // The format is as follows:
        // Continent1, country, country, ...
        // Continent2, country, country, ...
        // ...
        // New line each continent
        // Words separated by commas
        for(int i = 0; i<continents.length; ++i)
        {
            // Split the 
        }
    }

    private void loadAdjacents(String[] adjacents)
    {
        // Use a .txt file that contains all the adjacent countries of each country
        // The format is as follows:
        // Country1, Adjacent Country, Adjacent Country, Adjacent Country, ...
        // Country2, Adjacent Country, Adjacent Country, Adjacent Country, ...
        // ...
        // New line for each Country
        // Words separated by commas
        for(int i = 0; i < adjacents.length; ++i)
        {
            // Remove commas from each line
            adjacentCountries = adjacents[i].split(",");

            // List of adjacent countries for country at line "i"
            adjList = new ArrayList<Country>();

            // Fill adjList with the countries on line i starting with the second country in line
            // 1st word in line = country we want to find the adjacents for
            // 2nd word in line = First adjacent country
            // last word in line = last adjacent country for Country from first word
            for(int j = 1; j<adjacentCountries.length; ++j)
            {
                adjList.add(mapCountries.get(adjacentCountries[j]));
                System.out.println(adjacentCountries[j] + " added to: " + adjacentCountries[0]);
            }
        }
    }

}