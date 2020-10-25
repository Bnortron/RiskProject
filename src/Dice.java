import java.util.*;

public class Dice
{
    ArrayList<Integer> diceRoll;
    public Dice(){
        diceRoll = new ArrayList<Integer>();
    }

    public void rollDice(int numDice){
        Random randomNum = new Random();
        for (int i = 0; i < numDice; i++){
            int dieValue = randomNum.nextInt(5) + 1;
            diceRoll.add(dieValue);
        }
    }

    public void getRolls(){
        System.out.println(diceRoll);
    }

    public int getHighest(){
        System.out.println(Collections.max(diceRoll));
        return Collections.max(diceRoll);
    }

}
