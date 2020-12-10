package src;
import junit.framework.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RiskGameTest {

    RiskGame game;

    @Test
    public void gameSetup(){
        game = new RiskGame();
        assertNotEquals(game,null);
    }

    @Test
    public void addPlayers(){
        game = new RiskGame();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Boolean> ai = new ArrayList<Boolean>();
        names.add("Braxton");
        names.add("Tyler");
        names.add("Braden");
        game.setPlayers(3);
        ai.add(false);
        ai.add(false);
        ai.add(false);
        game.initializeGame(names,ai);
        assertNotEquals(game.getPlayers().size(),0);
        assertEquals(game.getPlayers().size(),3);
    }

    @Test
    public void checkCountriesSetup(){
        game = new RiskGame();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Boolean> ai = new ArrayList<Boolean>();
        names.add("Braxton");
        names.add("Tyler");
        names.add("Braden");
        game.setPlayers(3);
        ai.add(false);
        ai.add(false);
        ai.add(false);
        game.initializeGame(names,ai);
        assertNotEquals(game.getCountries(),null);
    }

    @Test
    public void checkAdjacentSetup(){
        game = new RiskGame();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Boolean> ai = new ArrayList<Boolean>();
        names.add("Braxton");
        names.add("Tyler");
        names.add("Braden");
        game.setPlayers(3);
        ai.add(false);
        ai.add(false);
        ai.add(false);
        game.initializeGame(names,ai);
        for(Country c : game.getCountries()){
            assertNotEquals(c.getAdjacents(),null); //All countries should have at least 1 adjacent
        }
    }

    @Test
    public void checkNextTurn(){
        game = new RiskGame();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Boolean> ai = new ArrayList<Boolean>();
        names.add("Braxton");
        names.add("Tyler");
        names.add("Braden");
        game.setPlayers(3);
        ai.add(false);
        ai.add(false);
        ai.add(false);
        game.initializeGame(names,ai);
        Player p1 = game.getCurrentPlayer(); //Get Player 1
        game.nextTurn();
        Player p2 = game.getCurrentPlayer(); //Get Player 2
        assertNotEquals(p1,p2); //Player 1 and Player 2 are not the same
    }

    @Test
    public void getAttackableCountriesTest(){
        game = new RiskGame();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Boolean> ai = new ArrayList<Boolean>();
        names.add("Braxton");
        names.add("Tyler");
        names.add("Braden");
        game.setPlayers(3);
        ai.add(false);
        ai.add(false);
        ai.add(false);
        game.initializeGame(names,ai);
        String[] attackableCountries = game.getAttackableCountries(game.getCountries().get(0).getName());
        assertNotEquals(attackableCountries,null);
    }
}
