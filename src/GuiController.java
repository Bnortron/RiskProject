import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 * Controller class that controls the data flow into model object and updates the view whenever data changes.
 *
 * @author Braden Norton
 * @version 11/17/20
 */
public class GuiController implements ActionListener, ListSelectionListener
{
    // Risk model
    private RiskGame model;

    // GUI View
    private GameView view;

    // Boolean
    private boolean mapStateOpen = false;

    // List of player names
    ArrayList<String> names;

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
        view.menuActionListener(this);
    }

    /**
     * Handler for events on GUI buttons/comboboxs
     *
     * @author Braden Norton
     */
    public void actionPerformed(ActionEvent e)
    {
        String o = e.getActionCommand();


        if(o.equals("Start"))
        {
            System.out.println("Start game");
            startSelected(o);
        }
        if(o.equals("menuReturn"))
        {
            System.out.println("Back to mm");
            menuReturn(o);
        }
        if(o.equals("Two"))
        {
            System.out.println("Two player game");
            model.setPlayers(2);
            view.setPanel(o);
            view.playerNameActionListener(this);
        }
        if(o.equals("Three"))
        {
            System.out.println("Three player game");
            model.setPlayers(3);
            view.setPanel(o);
            view.playerNameActionListener(this);
        }
        if(o.equals("Four"))
        {
            System.out.println("Four player game");
            model.setPlayers(4);
            view.setPanel(o);
            view.playerNameActionListener(this);
        }
        if(o.equals("Five"))
        {
            System.out.println("Five player game");
            model.setPlayers(5);
            view.setPanel(o);
            view.playerNameActionListener(this);
        }
        if(o.equals("Six"))
        {
            System.out.println("Six player game");
            model.setPlayers(6);
            view.setPanel(o);
            view.playerNameActionListener(this);
        }
        if(o.equals("Submit"))
        {
            namesSelected(o);
        }
        if(o.equals("Attack"))
        {
            attackSelected(o);
        }
        if(o.equals("Battle"))
        {
            battleSelected(o);
        }
        if(o.equals("Select Dice"))
        {
            diceSelected(o);
        }
        if(o.equals("Roll"))
        {
            rollSelected(o);
        }
        if(o.equals("Exit Battle"))
        {
            exitBattleSelected(o);
        }
        if(o.equals("Fortify"))
        {
            fortifySelected(o);
        }
        if(o.equals("Move Troops"))
        {
            moveTroopsSelected(o);
        }
        if(o.equals("Submit Fortify"))
        {
            submitFortifySelected(o);
        }
        if(o.equals("End Turn"))
        {
            endTurnSelected(o);
        }
    }

    /**
     * Handler for selection events in GUI JLists
     *
     * @author Braden Norton
     */
    public void valueChanged(ListSelectionEvent e)
    {
        Object o = e.getSource();
        // Once an option in list is selected
        if(!e.getValueIsAdjusting())
        {
            if(model.getPlayerAmount() == 2)
            {
                if(view.getPlayer1List() == -1){}
                else{getTroopsInCountry(view.getL1(), 0);}

                if(view.getPlayer2List() == -1){}
                else{getTroopsInCountry(view.getL2(), 1);}
            }
            if(model.getPlayerAmount() == 3)
            {
                if(view.getPlayer1List() == -1){}
                else{getTroopsInCountry(view.getL1(), 0);}

                if(view.getPlayer2List() == -1){}
                else{getTroopsInCountry(view.getL2(), 1);}

                if(view.getPlayer3List() == -1){}
                else{getTroopsInCountry(view.getL3(), 2);}
            }
            if(model.getPlayerAmount() == 4)
            {
                if(view.getPlayer1List() == -1){}
                else{getTroopsInCountry(view.getL1(), 0);}

                if(view.getPlayer2List() == -1){}
                else{getTroopsInCountry(view.getL2(), 1);}

                if(view.getPlayer3List() == -1){}
                else{getTroopsInCountry(view.getL3(), 2);}

                if(view.getPlayer4List() == -1){}
                else{getTroopsInCountry(view.getL4(), 3);}
            }
            if(model.getPlayerAmount() == 5)
            {
                if(view.getPlayer1List() == -1){}
                else{getTroopsInCountry(view.getL1(), 0);}

                if(view.getPlayer2List() == -1){}
                else{getTroopsInCountry(view.getL2(), 1);}

                if(view.getPlayer3List() == -1){}
                else{getTroopsInCountry(view.getL3(), 2);}

                if(view.getPlayer4List() == -1){}
                else{getTroopsInCountry(view.getL4(), 3);}

                if(view.getPlayer5List() == -1){}
                else{getTroopsInCountry(view.getL5(), 4);}
            }
            if(model.getPlayerAmount() == 6)
            {
                if(view.getPlayer1List() == -1){}
                else{getTroopsInCountry(view.getL1(), 0);}

                if(view.getPlayer2List() == -1){}
                else{getTroopsInCountry(view.getL2(), 1);}

                if(view.getPlayer3List() == -1){}
                else{getTroopsInCountry(view.getL3(), 2);}

                if(view.getPlayer4List() == -1){}
                else{getTroopsInCountry(view.getL4(), 3);}

                if(view.getPlayer5List() == -1){}
                else{getTroopsInCountry(view.getL5(), 4);}

                if(view.getPlayer6List() == -1){}
                else{getTroopsInCountry(view.getL6(), 5);}
            }
        }

        if (o.equals(view.getACList()))
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
                view.removeACCountryActionListener(this);
                view.updateACList(model.getAttackableCountries(view.getOCListValue()));
                view.setOCTroops(model.getTroopsByName(view.getOCListValue()));
                view.acCountryActionListener(this);
            }
        }

        if(o.equals(view.getOwnedList()))
        {
            if(!view.getOwnedList().getValueIsAdjusting())
            {
                // Update adj list for selected oc
                view.setAdjList(model.getFortifiableCountries(view.getOwnedListValue()));
            }
        }
    }

    /**
     * Retrieves the captured countries of a player using their name and turn position
     * The amount of troops for selected country from GUI is displayed within a JTextField
     *
     * @param s, name of player
     * @param n, player turn index
     *
     * @author Braden Norton
     */
    private void getTroopsInCountry(String s, int n)
    {
        // Array list of player n's owned countries
        for(Country c: model.getPlayers().get(n).getCapturedCountries())
        {
            if(c.getName().equals(s))
            {
                if(n==0){view.setL1(c.getTroops());}
                if(n==1){view.setL2(c.getTroops());}
                if(n==2){view.setL3(c.getTroops());}
                if(n==3){view.setL4(c.getTroops());}
                if(n==4){view.setL5(c.getTroops());}
                if(n==5){view.setL6(c.getTroops());}
            }
        }
    }

    /**
     * Adds players from GUI name selection screen to the model
     *
     * @author Braden Norton
     */
    private void addNames()
    {
        model.addPlayer(view.getName1());
        //System.out.println("Name 1 added");

        model.addPlayer(view.getName2());
        //System.out.println("Name 2 added");

        if(!view.getName3().isEmpty())
        {
            //System.out.println("Name 3 added");
            model.addPlayer(view.getName3());
        }
        if(!view.getName4().isEmpty())
        {
            //System.out.println("Name 4 added");
            model.addPlayer(view.getName4());
        }
        if(!view.getName5().isEmpty())
        {
            //System.out.println("Name 5 added");
            model.addPlayer(view.getName5());
        }
        if(!view.getName6().isEmpty())
        {
            //System.out.println("Name 6 added");
            model.addPlayer(view.getName6());
        }
    }

    /**
     * Handles "Start" button selection
     *
     *
     */
    void startSelected(String s)
    {
        view.setPanel(s);
        view.playerAmountActionListener(this);
    }

    /**
     * Handles "menuReturn" button selection
     *
     *
     */
    void menuReturn(String s)
    {
        view.setPanel(s);
        view.playerAmountActionListener(this);
    }

    /**
     * Handles submission of player names & starts game
     *
     *
     */
    void namesSelected(String s)
    {
        addNames();
        model.initializeGame();
        view.addPlayers(model.getPlayers());
        model.reinforcementStage(model.getCurrentTurn());
        updateTurn();
        view.setPanel(s);
        view.boardActionListener(this);
        view.mapStateActionListener(this);
        updateOCLists();
    }

    /**
     * Updates view to reflect current player
     *
     *
     */
    void updateTurn()
    {
        Player p = model.getCurrentTurn();
        System.out.println(model.getCurrentTurn().getName() + "'s turn!");
        view.setCurrentPlayerName(p.getName());
        view.setCurrentPlayerTroops(p.getTroops());
        view.setCurrentPlayerReinforcements(p.getCountryBonus(), p.getContinentBonus());
    }

    /**
     * Updates view with current list of players
     *
     *
     */
    void updateOCLists()
    {
        view.updateP1OCList(model.getPlayers().get(0).capturedCountriesToString());
        view.updateP2OCList(model.getPlayers().get(1).capturedCountriesToString());
        if(model.getPlayers().size() >= 3)
        {
            view.updateP3OCList(model.getPlayers().get(2).capturedCountriesToString());
            if(model.getPlayers().size() >= 4)
            {
                view.updateP4OCList(model.getPlayers().get(3).capturedCountriesToString());
                if(model.getPlayers().size() >= 5)
                {
                    view.updateP5OCList(model.getPlayers().get(4).capturedCountriesToString());
                    if(model.getPlayers().size() == 6)
                    {
                        view.updateP6OCList(model.getPlayers().get(5).capturedCountriesToString());
                    }
                }
            }
        }
    }

    /**
     * Handles Attack button action
     *
     *
     */
    void attackSelected(String s)
    {
        view.boardOptions(s);
        view.updateOCList(model.getCurrentTurn().capturedCountriesToString());
        view.attackActionListener(this);
        view.attackListsActionListener(this);
        view.acCountryActionListener(this);
    }

    /**
     * Handles Battle button action
     *
     *
     */
    void battleSelected(String s)
    {
        // Set Attacking and Defending country
        model.setDefendCountry(view.getACListValue());
        model.setAttackCountry(view.getOCListValue());

        // Set troops
        model.setACountryTroops();
        model.setDCountryTroops();

        view.boardOptions(s);

        // Set Dice
        view.setAttDice(model.allowedAttDice(model.getACountryTroops()));
        view.setDefDice(model.allowedDefDice(model.getDefenderTroops()));

        view.chooseDiceActionListener(this);
    }

    /**
     * Handles Choose Dice button action
     *
     *
     */
    void diceSelected(String s)
    {
        // Set Number of dice from JComboBox
        model.setAttDice(view.getADiceAmount());
        model.setDefDice(view.getDDiceAmount());

        view.boardOptions(s);
        view.battleActionListener(this);

        // Set Battle Stats
        view.setAttackingCountry(model.getAttacker());
        view.setDefendingCountry(model.getDefender());
        view.setTroopsInAttCountry(model.getACountryTroops());
        view.setTroopsInDefCountry(model.getACountryTroops());
        view.setAttDiceAmount(model.getAttDiceAmount());
        view.setDefDiceAmount(model.getDefDiceAmount());
    }



    /**
     * Handles roll button selection
     *
     * @param s
     */
    void rollSelected(String s)
    {
        model.attackStage(model.getAttacker(), model.getDefender());
        view.boardOptions(s);
        view.attackActionListener(this);
        view.attackListsActionListener(this);
        view.acCountryActionListener(this);

        // Update view
        updateResult();
    }

    /**
     * Updates the results text area in battle gui frame with results of roll
     * Displays: Attack dice roll, Defence dice roll, amount of attack troops lost, amount of defence troops lost
     *
     * @author Braden Norton
     */
    void updateResult()
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

        view.setTroopsInAttCountry(model.getACountryTroops());
        view.setTroopsInDefCountry(model.getDCountryTroops());
    }

    /**
     * Handles Exit Battle Selection
     *
     *
     */
    void exitBattleSelected(String s)
    {
        model.endAttackPhase();
        view.boardOptions(s);
        updateMainBoardResults();
    }

    /**
     * Post battle results to the main game board
     *
     */
    void updateMainBoardResults()
    {
        // If attack was successful
        if(model.getAttackResult())
        {
            // Print win result
            view.updateTurnArea(model.getCurrentTurn().getName() + " has claimed " + model.getDefender() + " from " + model.getDefendingCountryOwner());

            // Adjust country ownership
            updateOCLists();
        }
        // No countries lost/claimed
        else
        {
            view.updateTurnArea(model.getCurrentTurn().getName() + " has exited the attack!");
        }

        // Adjust number of troops
        view.setTroops(""+model.getCurrentTurn().getTroops());
    }

    /**
     * Handles fortify button selection
     *
     *
     */
    void fortifySelected(String s)
    {
        view.boardOptions(s);
        view.fortifyActionListener(this);
        view.fortifyListsActionListener(this);

        // Set country lists
        view.setFortifyFromList(model.getCurrentTurn().capturedCountriesToString());
    }

    /**
     * Handles Move Troops button selection
     *
     *
     */
    void moveTroopsSelected(String s)
    {
        // Set Country values from lists
        model.setCurrentCountryName(view.getOwnedListValue());
        System.out.println("Current Country: " + model.getCurrentCountryName());
        model.setFortifiedCountryName(view.getAdjListValue());
        System.out.println("Fortifying: " + model.getFortifiedCountryName());

        view.boardOptions(s);
        view.fortifyActionListener(this);

        // Set Allowable Fortify Amount
        view.setAmountToFortify(model.getValidMovementAmount(model.getCurrentCountryName()));
    }

    /**
     * Handles Submit Fortify button selection
     *
     *
     */
    void submitFortifySelected(String s)
    {
        model.fortifyStage(model.getCurrentCountryName(), model.getFortifiedCountryName(), (view.getFortifyTroopAmount() + 1));
        view.boardOptions(s);
        view.updateTurnArea(model.getCurrentCountryName() + " has fortified " + model.getFortifiedCountryName() + " by " + model.getFortifiedAmount() + " troops!");
        view.updateTurnArea("Please Select 'End Turn'!");

        // Adjust country ownership
        updateOCLists();
    }

    /**
     * Handles End Turn button selection
     *
     *
     */
    void endTurnSelected(String s)
    {
        model.nextTurn();
        model.reinforcementStage(model.getCurrentTurn());
        updateTurn();
        view.boardOptions(s);
        view.boardActionListener(this);
        view.mapStateActionListener(this);
        updateOCLists();
    }
}

