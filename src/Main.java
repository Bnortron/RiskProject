package src;
/**
 * Main class for the game of Risk
 *
 * Creates the model, view, and controller for the MVC design pattern
 *
 * @author Braden Norton
 * @version 11/17/20
 */
class main
{
    /**
     * Constructor for objects of class RiskMain
     */
    public static void main(String[] args)
    {
        RiskGame game = new RiskGame();
        GameView gui = new GameView();
        GuiController controller = new GuiController(game,gui);
    }
}
