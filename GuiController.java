import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Controller class that controls the data flow into model object and updates the view whenever data changes.
 * This class holds a different controller for each frame in GameView class
 *
 * Controllers:
 *
 * GUIController
 * LoadController
 * PlayerAmountController
 * PlayerNameController
 * BoardController
 * ReinforcementPhaseController
 * AttackPhaseController
 * FortifyPhaseController
 * aiPhaseController
 *
 *
 * @author Braden Norton
 * @version 11/17/20
 */
public class GuiController implements ActionListener
{
    // Risk model
    private RiskGame model;

    // GUI View
    private GameView view;

    // Next Frame
    private PlayerAmountGUI next;
    private LoadGUI load;

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
            // Open LoadGUI
            load = new LoadGUI();
            load.loadGUIActionListeners(new LoadController(model, load, view));
            view.setVisible(false);
        }
        else
        {
            view.quitGame();
        }
    }
}
//====================================================================================================//

/**
 * This class handles Users loading either a custom map or saved game file from LoadGUI and updates the model/opens next GUI
 *
 * @author Braden Norton
 */
class LoadController implements ActionListener
{
    // Risk model
    private RiskGame model;

    // LoadGUI view
    private LoadGUI view;

    // Menu view
    private GameView menu;

    // Loaded Model
    private RiskGame loadModel;

    // Load Game Frame
    private BoardGUI start;

    // Next Frame
    private PlayerAmountGUI customMap;

    // Load Game Reader
    private ObjectInputStream objectReader;

    /**
     * Constructor
     *
     * @param m Model
     * @param v View
     */
    public LoadController(RiskGame m, LoadGUI v, GameView menu)
    {
        this.menu = menu;
        this.model = m;
        this.view = v;
    }

    /**
     * Action handler for view buttons
     *
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        // Get ActionEvent
        String o = e.getActionCommand();

        if(o.equals("saved") || o.equals("custom"))
        {
            try {
                loadGame(o);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else
        {
            // Open Menu
            menu.setVisible(true);

            // Close LoadGUI
            view.dispose();
        }
    }

    /**
     * Loads a save file or custom game file, depending on the file type specified by the user
     *
     * @param type File type determined by the buttons on LoadGUI
     * @throws IOException
     * @throws ClassNotFoundException
     * @author Braden Norton
     */
    void loadGame(String type) throws Exception {
        // Get Save File
        String loadFile = view.loadGame(type);

        System.out.println("File: "+ loadFile);

        // Load Model
        if(!loadFile.equals("No path selected") && type.equals("saved"))
        {
            // Read File
            objectReader = new ObjectInputStream(new FileInputStream(loadFile));
            loadModel = (RiskGame)objectReader.readObject();
            objectReader.close();

            // Initialize Game
            start = new BoardGUI(loadModel.getPlayers(), loadModel.getCurrentPlayer());
            start.boardActionListener(new BoardController(loadModel, start), new BoardController(loadModel, start));

            // Close loadGUI
            view.dispose();
        }
        else if(!loadFile.equals("No path selected") && type.equals("custom"))
        {
            // Create the custom map
            model.setupMap(loadFile);
            model.setCustomMapLoaded();

            // Open PlayerAmountGUI
            customMap = new PlayerAmountGUI();
            customMap.playerAmountActionListener(new PlayerAmountController(model, customMap));

            // Close menuGUI
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
 * Handles user input from PlayerAmountGUI & updates model/view from input data
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
 * Handles user actions on PlayerNameGUI & updates model/view with input data
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
            if(model.getCustomMapLoaded())
            {
                model.initializeCustomGame(view.getNames(), view.getAI());
            }
            else
            {
                model.initializeGame(view.getNames(), view.getAI());
            }

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
 * Handles user selection on BoardGUI & updates model/view using the input data
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
            view.aiPhaseComplete();
            aiPhase = new aiGUI();
            aiPhase.aiActionListener(new aiPhaseController(model, aiPhase, view));
            model.aiReinforce();
            view.updateTurnArea("Reinforcement: "+ model.getReinforcedAI() +" added to " + model.getReinforcedCountry());
            model.setACDC();
            aiPhase.setDefDice(model.allowedDefDice(model.getDefenderTroops()));
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

    /**
     * List selection listener for the game state lists
     *
     * When a country in one of the lists is selected, the name of the player who owns the list & the country selected, are passed to GameView
     * GameView then updates a text area with the amount of troops present in the selected country, which is displayed under the list
     *
     * @param e
     * @author Braden Norton
     */
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

    /**
     * Checks the model to see whether the current player is in the Reinforce, Attack, or Foritfy phase
     * The boardGUI buttons are then updated through GameView to reflect the phase
     *
     * Reinforce: Reinforce button enabled, Attack, Fortify, End Turn buttons disabled (can't do anything with your turn until all reinforcements placed)
     * Attack: Attack, Fortify, End Turn buttons are enabled, Reinforce button disabled (can only reinforce @ start of turn)
     * Fortify (Fortify has been clicked & troops were moved): End Turn button enabled, Reinforce, Attack, Fortify buttons all disabled (Turn is over after you Fortify)
     *
     * @author Braden Norton
     */
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

    /**
     * Menu selection option that allows player to save the current state of the game
     *
     * @param fileName String obtained from JFileChooser of the desired save file name
     * @param model Current state of the RiskModel model, saves all it's data
     * @throws IOException Input/Output error occurs during the save
     */
    void saveGame(String fileName, RiskGame model) throws IOException
    {
        objectWriter = new ObjectOutputStream(new FileOutputStream(fileName+".ser"));
        objectWriter.writeObject(model);
        objectWriter.close();
    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------

/**
 * Handles user input for ReinforceGUI and updates model/view with input data
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
 * Handles user input for AttackGUI and updates model/view with input data
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

    /**
     * List selection handler for the selection of countries phase in AttackGUI
     *
     * @param e country in list was selected
     */
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
     * Updates the AttackGUI results window w/the results of the roll
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

    /**
     * Updates the main BoardGUI turn action window with the final results of the Attack (lost troops/country)
     *
     * @author Braden Norton
     */
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
 * Handles user input for FortifyGUI and updates model/view with input data
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

    /**
     * List selection handler
     * Updates lists during the select country part of the Fortify phase
     *
     * @param e when user selects a list
     */
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
 * Handles user input for aiGUI and updates model/view with input data
 *
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
        String o = e.getActionCommand();
        if(o.equals("Submit")){
            model.setDefDice(view.getDefDice());
            view.dispose();
            model.aiAttackStage(); 
            board.updateStats(model.getPlayers());
            board.updateTurnArea("\n"+"Attack: " + model.getAttacker() + " attacking " + model.getDefender());
            board.updateTurnArea(model.getAttackingPlayer()+" has lost: "+model.getAttLosses()+" troops in "+model.getAttacker());
            board.updateTurnArea(model.getDefendingCountryOwner().getName()+" has lost: "+model.getDefLosses()+" troops in "+model.getDefender());
            if(model.getAttackResult())
            {
                board.updateTurnArea(model.getAttackingPlayer()+" has claimed: " + model.getDefender());
            }  
            model.aiFortify();
            board.updateTurnArea("\n"+"Fortify: " + model.getCurrentCountryName()+" fortified "+model.getFortifiedCountryName()+" by "+model.getFortifiedAmount());
            board.updateTurnArea("Troops in "+model.getCurrentCountryName()+": "+model.getTroopsByName(model.getCurrentCountryName()));
            board.updateTurnArea("Troops in "+model.getFortifiedCountryName()+": "+model.getTroopsByName(model.getFortifiedCountryName()));
            model.nextTurn();
        } 
    }
}