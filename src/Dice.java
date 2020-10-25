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

    
    
    /**
     * Compares attackers highest roll to defenders highest roll
     * @return 1 if attackers, win 2 in defenders, win 0 if tie
     */
    public int compareDice(int atkRoll, int defRoll){
        if (atkRoll > defRoll){
            System.out.println("Attackers Win! Defenders Lose 1 Army.");
            return 1;
        } else if (atkRoll == defRoll){
            System.out.println("Tie! Attackers Lose 1 Army.");
            return 0;
        } else {
            System.out.println("Defenders Win! Attackers Lose 1 Army.");
            return 2;
        }
        
    }

    public static void main(String[] args) {
        Dice atkDie = new Dice();
        Dice defDie = new Dice();

        atkDie.rollDice(3);
        defDie.rollDice(2);

        atkDie.getRolls();
        defDie.getRolls();
        
        atkDie.getHighest();
        defDie.getHighest();
    }

}
