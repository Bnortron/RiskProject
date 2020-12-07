//package src;

import java.io.Serializable;
import java.util.*;

public class Player implements Serializable
{
    private String name;
    private int troops;
    private int turnPosition;
    private int countryBonus;
    private int continentBonus;
    private boolean AI;

    private ArrayList<Country> capturedCountries;
    private ArrayList<Continent> capturedContinents;

    /**
     * Constructor
     *
     * @param name
     * @param troops
     * @param turnPosition
     */
    public Player(String name, int troops, int turnPosition, boolean ai)
    {
        this.name = name;
        this.troops = troops;
        this.turnPosition = turnPosition;
        this.AI = ai;
        capturedCountries = new ArrayList<>();
        capturedContinents = new ArrayList<>();
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

    void addTroops(int n)
    {
        troops = troops + n;
    }

    void removeTroops(int n)
    {
        troops = troops - n;
    }

    public ArrayList<Country> getCapturedCountries()
    {
        return capturedCountries;
    }

    public ArrayList<String> capturedCountriesToString()
    {
        ArrayList<String> temp = new ArrayList<>();
        for(Country c : capturedCountries){
            temp.add(c.getName());
        }
        return temp;
    }

    public void addCapturedCountry(Country c)
    {
        this.capturedCountries.add(c);
        c.setOwner(this);
    }

    public void removeCapturedCountry(Country c){
        capturedCountries.remove(c);
    }

    void setCountryBonus(int n)
    {
        this.countryBonus = n;
        System.out.println("Country Bonus: " + countryBonus);
    }

    int getCountryBonus()
    {
        return this.countryBonus;
    }

    ArrayList<Continent> getCapturedContinents() { return capturedContinents; }

    void removeCapturedContinent(Continent c) { capturedContinents.remove(c); }

    int numCapturedCountries(){ return capturedCountries.size(); }

    void addCapturedContinent(Continent c)
    {
        this.capturedContinents.add(c);
    }

    void setContinentBonus(int n)
    {
        this.continentBonus = n;
    }

    int getContinentBonus()
    {
        return this.continentBonus;
    }

    boolean isAI()
    {
        return AI;
    }

}