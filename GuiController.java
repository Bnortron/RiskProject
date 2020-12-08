import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Controller class that controls the data flow into model object and updates the view whenever data changes.
 * This class holds a different controller for each frame in GameView class
 *
 * Controllers:
 *
 * GUIController -
 * PlayerAmountController -
 * PlayerNameController
 * BoardController
 * ReinforcementPhaseController -
 * AttackPhaseController -
 * FortifyPhaseController -
 *
 *
 * @author Braden Norton
 * @version 11/17/20
 */
public class GuiController implements ActionListener
{
    // Risk model
    private RiskGame model;

    // Loaded Model
    private RiskGame loadModel;

    // GUI View
    private GameView view;

    // Next Frame
    private PlayerAmountGUI next;

    // Load Game Frame
    private BoardGUI start;

    // Load Game Reader
    private ObjectInputStream objectReader;

    /**
     * Constructor for controller
     *
     * @param m, model holding all the data
     * @param v, GUI that displays changes in model
     *
     * @author Braden Norton
     */
    public GuiController(RiskGame m, GameView v)
    {
        this.model = m;
        this.view = v;

        // Set controlled actions
        view.menuActionListener(this);
    }

    /**
     * Handler for events from menuGUI
     *
     * Start Game: Opens playerAmountGUI
     * Load Game: Opens loadGameGUI
     * Quit Game: Exits the game/closes menuGUI
     *
     * @author Braden Norton
     */
    public void actionPerformed(ActionEvent e)
    {
        // Get action command
        String o = e.getActionCommand();

        // Process command
        if(o.equals("Start"))
        {
            System.out.println("Start Selected");

            // Open PlayerAmountGUI
            next = new PlayerAmountGUI();
            next.playerAmountActionListener(new PlayerAmountController(model, next));

            // Close menuGUI
            view.dispose();
        }
        else if(o.equals("Load"))
        {
            // Update Model & View
            try {
                loadGame();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else
        {
            System.out.println("Quit Selected");

            // Quit Game
            view.dispose();
            view.quitGame();
        }
    }

    void loadGame() throws IOException, ClassNotFoundException {
        // Get Save File
        String loadFile = view.loadGame();

        // Load Model
        if(!loadFile.equals("No path selected"))
        {
            // Read File
            objectReader = new ObjectInputStream(new FileInputStream(loadFile));
            loadModel = (RiskGame)objectReader.readObject();
            objectReader.close();

            // Initialize Game
            start = new BoardGUI(loadModel.getPlayers(), loadModel.getCurrentPlayer());
            start.boardActionListener(new BoardController(loadModel, start), new BoardController(loadModel, start));

            // Close MenuGUI
            view.dispose();
        }
        else
        {
            view.loadFailed();
        }
    }
}

//====================================================================================================//

/**
 *
 *
 * @author Braden Norton
 * @version 11/27/20
 */
class PlayerAmountController implements ActionListener
{
    // Variables
    private RiskGame model;
    private PlayerAmountGUI view;

    // Next Controller
    private PlayerNameGUI next;

    /**
     * Constructor
     *
     * @param m, model of RiskGame
     * @param v, view of GameView
     */
    public PlayerAmountController(RiskGame m, PlayerAmountGUI v)
    {
        this.model = m;
        this.view = v;
    }

    /**
     * ActionEvent handler for PlayerAmountGUI
     *
     * Gets player amount selected, and passes it as an int to GameView
     * Gameview opens PlayerNameGUI for the amount of players selected
     *
     * @param e, ActionEvent
     */
    public void actionPerformed(ActionEvent e)
    {
        // Get ActionEvent
        String o = e.getActionCommand();

        // Get number of players selected
        int players = Integer.parseInt(o);

        // Update Model
        model.setPlayers(players);
        System.out.println("Players: " + model.getPlayerAmount());

        // Close PlayerAmountGUI
        view.dispose();

        // Open PlayerAmountGUI
        next = new PlayerNameGUI(players);
        next.playerNameActionListener(new PlayerNameController(model, next));

        view.dispose();
    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------

/**
 *
 *
 * @author Braden Norton
 * @version 11/27/20
 */
class PlayerNameController implements ActionListener
{
    // Model & View
    private RiskGame model;
    private PlayerNameGUI view;

    // Next controller
    private BoardGUI next;

    // Previous Frame
    private PlayerAmountGUI prev;

    // Lists
    ArrayList<Boolean> playerIsAi;

    /**
     * Constructor
     *
     * @param m, model of RiskGame
     * @param v, view of GameView
     */
    public PlayerNameController(RiskGame m, PlayerNameGUI v)
    {
        this.model = m;
        this.view = v;
    }

    /**
     * ActionEvent handler
     *
     * @param e, ActionEvent
     */
    public void actionPerformed(ActionEvent e)
    {
        // Get ActionEvent
        String o = e.getActionCommand();

        if(o.equals("Submit"))
        {
            System.out.println("Submit selected");

            // Initialize Game
            model.initializeGame(view.getNames(), view.getAI());

            // Initialize View
            view.dispose();
            next = new BoardGUI(model.getPlayers(), model.getCurrentPlayer());
            next.boardActionListener(new BoardController(model,next), new BoardController(model,next));

            if(model.getCurrentTurn().isAI()){

            }
        }
        else if(o.equals("Back"))
        {
            System.out.println("Back selected");
            // Close PlayerNameGUI
            view.dispose();

            // Open PlayerAmountGUI
            prev = new PlayerAmountGUI();
            prev.playerAmountActionListener(new PlayerAmountController(model, prev));
        }
    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------

/**
 *
 *
 * @author Braden Norton
 * @version 11/27/20
 */
class BoardController implements ActionListener, ListSelectionListener
{
    // Variables
    private RiskGame model;
    private BoardGUI view;

    // Board phase frames
    private ReinforceGUI rPhase;
    private AttackGUI aPhase;
    private FortifyGUI fPhase;
    private aiGUI aiPhase;

    // Save Game
    private JFileChooser fileChooser;
    private ObjectOutputStream objectWriter;
    private ObjectInputStream objectReader;
    private BufferedReader reader;

    /**
     * Constructor
     *
     * @param m, model of RiskGame
     * @param v, view of GameView
     */
    public BoardController(RiskGame m, BoardGUI v) {
        this.model = m;
        this.view = v;

        // Check start phase
        checkPhase();
    }

    /**
     * ActionEvent handler
     *
     * @param e, ActionEvent
     */
    public void actionPerformed(ActionEvent e)
    {
        // Get ActionEvent
        String o = e.getActionCommand();

        if(o.equals("Reinforce"))
        {
            // Open ReinforceGUI
            rPhase = new ReinforceGUI(model.getCurrentPlayer(), model.getReinforcementAmount());
            rPhase.reinforceActionListener(new ReinforcePhaseController(model, rPhase, view));
        }
        else if(o.equals("Attack"))
        {
            aPhase = new AttackGUI(model.getCurrentPlayer());
            aPhase.attackActionListener(new AttackPhaseController(model, aPhase, view), new AttackPhaseController(model, aPhase, view));
        }
        else if(o.equals("Fortify"))
        {
            fPhase = new FortifyGUI(model.getCurrentPlayer());
            fPhase.fortifyActionListener(new FortifyPhaseController(model, fPhase, view),new FortifyPhaseController(model, fPhase, view));
        }
        else if(o.equals("AI Turn")){
            aiPhase = new aiGUI();
            aiPhase.aiActionListener(new aiPhaseController(model, aiPhase, view));
            //AI Button
            //AI Reinforce
            //Randomize A/F
                //AI Attack
                    //SetACDC
                    //Popup list
                    //AI Attack Stage
                //AI Fortify

        }
        else if(o.equals("End Turn"))
        {
            // Update Model
            model.nextTurn();
            model.setReinforcementAmount();

            // Update View
            view.nextTurn(model.getCurrentTurn());
            view.boardActionListener(new BoardController(model,view), new BoardController(model,view));
        }
        else if (o.equals("Save Game"))
        {
            try {
                saveGame(view.saveGame(), model);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else {
            // Quit Game Without saving
            view.dispose();
            view.quitGame();
        }
    }

    public void valueChanged(ListSelectionEvent e)
    {
        // Get list selected
        Object o = e.getSource();

        if(!e.getValueIsAdjusting())
        {
            if(o.equals(view.l1()))
            {
                view.getTroopsInCountry(model.getPlayers().get(0), view.getL1());
            }
            else if(o.equals(view.l2()))
            {
                view.getTroopsInCountry(model.getPlayers().get(1), view.getL2());
            }
            else if(o.equals(view.l3()))
            {
                view.getTroopsInCountry(model.getPlayers().get(2), view.getL3());
            }
            else if(o.equals(view.l4()))
            {
                view.getTroopsInCountry(model.getPlayers().get(3), view.getL4());
            }
            else if(o.equals(view.l5()))
            {
                view.getTroopsInCountry(model.getPlayers().get(4), view.getL5());
            }
            else if(o.equals(view.l6()))
            {
                view.getTroopsInCountry(model.getPlayers().get(5), view.getL6());
            }
        }
    }

    void checkPhase()
    {
        // Reinforcement has been completed, but fortify has not
        if(!model.reinforcementPhaseActive() && !model.fortifyStageActive())
        {
            view.reinforcementPhaseComplete();
        }
        // Fortify has been completed, user must end turn
        else if(model.fortifyStageActive())
        {
            view.fortifyPhaseComplete();
        }
    }

    void saveGame(String fileName, RiskGame model) throws IOException
    {
        objectWriter = new ObjectOutputStream(new FileOutputStream(fileName));
        objectWriter.writeObject(model);
        objectWriter.close();
    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------

/**
 *
 *
 * @author Braden Norton
 * @version 11/27/20
 */
class ReinforcePhaseController implements ActionListener
{
    // Variables
    private RiskGame model;
    private ReinforceGUI view;
    private BoardGUI board;

    /**
     * Constructor
     *
     * @param m, model of RiskGame
     * @param v, view of GameView
     */
    public ReinforcePhaseController(RiskGame m, ReinforceGUI v, BoardGUI b)
    {
        this.model = m;
        this.view = v;
        this.board = b;
    }

    /**
     * ActionEvent handler
     *
     * @param e, ActionEvent
     */
    public void actionPerformed(ActionEvent e)
    {
        // Get ActionEvent
        String o = e.getActionCommand();

        if(o.equals("Submit"))
        {
            System.out.println("Submit Selected");
            System.out.println("Amount of troops to reinforce " + view.getCountry() + ": " + view.getReinforceAmount());

            // Update Model
            model.reinforcementStage(view.getReinforceAmount(), view.getCountry());

            // Update View
            view.dispose();
            updateBoard();
            if(!model.reinforcementPhaseActive())
            {
                board.reinforcementPhaseComplete();
                board.revalidate();
            }
        }
        else if(o.equals("Back"))
        {
            System.out.println("Back Selected");
            view.dispose();
        }
    }

    void updateBoard()
    {
        board.updateTurnArea("Reinforcement: "+ view.getReinforceAmount()+" added to " + model.getReinforcedCountry());
        board.updateTurnArea("Remaining reinforcements: " + model.getRemainingReinforcements()+"\n");
    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------

/**
 *
 *
 * @author Braden Norton
 * @version 11/27/20
 */
class AttackPhaseController implements ActionListener, ListSelectionListener
{
    // Variables
    private RiskGame model;
    private AttackGUI view;
    private BoardGUI board;

    /**
     * Constructor
     *
     * @param m, model of RiskGame
     * @param v, view of GameView
     */
    public AttackPhaseController(RiskGame m, AttackGUI v, BoardGUI b)
    {
        this.model = m;
        this.view = v;
        this.board = b;
    }

    /**
     * ActionEvent handler
     *
     * @param e, ActionEvent
     */
    public void actionPerformed(ActionEvent e)
    {
        // Get ActionEvent
        String o = e.getActionCommand();

        if(o.equals("Battle"))
        {
            // Update Model
            model.setDefendCountry(view.getACListValue());
            model.setAttackCountry(view.getOCListValue());

                model.setACountryTroops();
                model.setDCountryTroops();

                // Update View
                view.chooseDiceStage(model.getAttackingPlayer(), model.getDefendingPlayer());
                view.setAttDice(model.allowedAttDice(model.getACountryTroops()));
                view.setDefDice(model.allowedDefDice(model.getDefenderTroops()));
            } else if (o.equals("Cancel")) {
                // Close view
                view.dispose();
            } else if (o.equals("Select Dice")) {
                // Update model
                model.setAttDice(view.getADiceAmount());
                model.setDefDice(view.getDDiceAmount());

                // Update view
                view.battleStage();
            } else if (o.equals("Roll")) {
                // Update Model
                model.attackStage(model.getAttacker(), model.getDefender());

                // Update View
                rollResults();
                updateBoard();
                view.disableRoll();
            } else {
                // Close AttackGUI
                view.dispose();
                board.updateStats(model.getPlayers());
                if (model.getAttackResult()) {
                    board.updateCountryLists(model.getCurrentPlayer(), model.getDefendingCountryOwner(), model.getDefender());
                    board.revalidate();
                }
            }
        }

    public void valueChanged(ListSelectionEvent e)
    {
        // Get list selected
        Object o = e.getSource();

        if(o.equals(view.getACList()))
        {
            if(!view.getACList().getValueIsAdjusting())
            {
                view.updateACTroops(model.getTroopsByName(view.getACList().getSelectedValue().toString()));
            }
        }

        if (o.equals(view.getOCList()))
        {
            if(!view.getOCList().getValueIsAdjusting())
            {
                // Update ac list for selected oc
                System.out.println("Selected: " + view.getOCListValue());
                view.removeACListener(this);
                view.updateACList(model.getAttackableCountries(view.getOCListValue()));
                view.updateOCTroops(model.getTroopsByName(view.getOCListValue()));
                view.acCountryActionListener(this);
            }
        }
    }

    /**
     * Updates the results text area in battle gui frame with results of roll
     * Displays: Attack dice roll, Defence dice roll, amount of attack troops lost, amount of defence troops lost
     *
     * @author Braden Norton
     */
    void rollResults()
    {
        if(model.getAttDiceAmount() == 1 || model.getDefDiceAmount() == 1)
        {
            view.setRollResult("Attack Roll: " + model.getARolls(0)+"\n");
            view.setRollResult("Defence Roll: " + model.getDRolls(0)+"\n");
        }
        else if((model.getAttDiceAmount() >= 2) && (model.getDefDiceAmount() == 2))
        {
            view.setRollResult("Attack Roll: " + model.getARolls(0)+ ", " + model.getARolls(1) +"\n");
            view.setRollResult("Defence Roll: " + model.getDRolls(0)+ ", " + model.getDRolls(1) +"\n");
        }

        view.setRollResult("Attack Losses: " + model.getAttLosses()+"\n");
        view.setRollResult("Defence Losses: " + model.getDefLosses());
        view.setBattleResults(model.getACountryTroops(), model.getDCountryTroops());
    }

    void updateBoard()
    {
        board.updateTurnArea("\n"+"Attack: " + model.getAttacker() + " attacking " + model.getDefender());
        board.updateTurnArea(model.getAttackingPlayer()+" has lost: "+model.getAttLosses()+" troops in "+model.getAttacker());
        board.updateTurnArea(model.getDefendingCountryOwner().getName()+" has lost: "+model.getDefLosses()+" troops in "+model.getDefender());
        if(model.getAttackResult())
        {
            board.updateTurnArea(model.getAttackingPlayer()+" has claimed: " + model.getDefender());
        }
    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------

/**
 *
 *
 * @author Braden Norton
 * @version 11/27/20
 */
class FortifyPhaseController implements ActionListener, ListSelectionListener
{
    // Variables
    private RiskGame model;
    private FortifyGUI view;
    private BoardGUI board;

    /**
     * Constructor
     *
     * @param m, model of RiskGame
     * @param v, view of GameView
     */
    public FortifyPhaseController(RiskGame m, FortifyGUI v, BoardGUI b)
    {
        this.model = m;
        this.view = v;
        this.board = b;
    }

    /**
     * ActionEvent handler
     *
     * @param e, ActionEvent
     */
    public void actionPerformed(ActionEvent e)
    {
        // Get ActionEvent
        String o = e.getActionCommand();

        if(o.equals("Move Troops"))
        {
            // Update Model
            model.setCurrentCountryName(view.getOwnedListValue());
            model.setFortifiedCountryName(view.getAdjListValue());

            // Update View
            view.setAmountToFortify(model.getValidMovementAmount(model.getCurrentCountryName()));
            view.fortifyAmountStage();
        }
        else if(o.equals("Submit Fortify"))
        {
            // Update Model
            model.fortifyStage(model.getCurrentCountryName(), model.getFortifiedCountryName(), (view.getFortifyTroopAmount() + 1));

            // Update View
            view.dispose();
            updateBoard();
            board.fortifyPhaseComplete();
        }
    }

    public void valueChanged(ListSelectionEvent e)
    {
        // Get list selected
        Object o = e.getSource();

        if(o.equals(view.getOwnedList()) && (!view.getOwnedList().getValueIsAdjusting()))
        {
            // Update adj list for selected oc
            view.setAdjList(model.getFortifiableCountries(view.getOwnedListValue()));
        }
    }

    void updateBoard()
    {
        board.updateTurnArea("\n"+"Fortify: " + model.getCurrentCountryName()+" fortified "+model.getFortifiedCountryName()+" by "+model.getFortifiedAmount());
        board.updateTurnArea("Troops in "+model.getCurrentCountryName()+": "+model.getTroopsByName(model.getCurrentCountryName()));
        board.updateTurnArea("Troops in "+model.getFortifiedCountryName()+": "+model.getTroopsByName(model.getFortifiedCountryName()));
        board.updateTurnArea("Turn ended - Please select 'End Turn' once you are done reviewing"+"\n");
    }
}

/**
 * @author Tyler Leung
 * @author Braxton Martin
 */
class aiPhaseController implements ActionListener{

    private RiskGame model;
    private aiGUI view;
    private BoardGUI board;

    public aiPhaseController(RiskGame m, aiGUI v, BoardGUI b){
        this.model = m;
        this.view = v;
        this.board = b;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
        model.aiReinforce();
        for(int i = 0; i<4; i++){ //AI attacks only 4 times to save time
        model.setACDC();
        view = new aiGUI();
        board.add(view.userDefDice());
        view.userDefDice();
        model.aiAttackStage(); 
        }
        model.aiFortify();  
    }

}
