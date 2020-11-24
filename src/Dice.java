//package src;

import java.util.*;


/**
 * Dice Class used for Risk Game. Can roll dice and return highest value from rolls and print rolls.
 *
 * @author Tyler Leung
 */
public class Dice
{
    ArrayList<Integer> diceRoll;
    public Dice(){
        diceRoll = new ArrayList<Integer>(); //create list for rolls
    }


    /**
     * Roll the dice for the player. Add the dice roll to the an array list.
     * 
     * @param numDice number of dice user would like to roll
     */
    public void rollDice(int numDice){
        Random randomNum = new Random();
        for (int i = 0; i < numDice; i++){
            int dieValue = randomNum.nextInt(5) + 1;
            diceRoll.add(dieValue);
        }
    }

    /**
     * Prints the rolls of the dice.
     */
    public void printRolls(){
        System.out.println("You rolled: ");
        for (int i : diceRoll){
            System.out.println(Integer.toString(i) + " ");
        }
    }


    /**
     * Finds highest roll out of all the dice rolled
     * 
     * @return highest dice roll
     */
    public int getHighest(){
        return Collections.max(diceRoll);
    }

    /**
     * Remove highest roll from list. Only used when one or more dice rolled.
     * 
     */
    public void removeHighest(){
        diceRoll.remove(diceRoll.indexOf(getHighest()));
    }

    /**
     * Clear list of all rolls. Needed when new rolls must occur.
     * 
     */
    public void clearRolls(){
        diceRoll.removeAll(diceRoll);
    }


}