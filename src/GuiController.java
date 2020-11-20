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

    /**
     * Constructor for controller
     *
     * @param RiskGame m, model holding all the data
     * @param GameView v, GUI that displays changes in model
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
            view.setPanel(o, model);
            view.playerAmountActionListener(this);
        }
        if(o.equals("menuReturn"))
        {
            System.out.println("Back to mm");
            view.setPanel(o, model);
            view.playerAmountActionListener(this);
        }
        if(o.equals("Two"))
        {
            System.out.println("Two player game");
            model.setPlayers(2);
            view.setPanel(o, model);
            view.playerNameActionListener(this);
        }
        if(o.equals("Three"))
        {
            System.out.println("Three player game");
            model.setPlayers(3);
            view.setPanel(o, model);
            view.playerNameActionListener(this);
        }
        if(o.equals("Four"))
        {
            System.out.println("Four player game");
            model.setPlayers(4);
            view.setPanel(o, model);
            view.playerNameActionListener(this);
        }
        if(o.equals("Five"))
        {
            System.out.println("Five player game");
            model.setPlayers(5);
            view.setPanel(o, model);
            view.playerNameActionListener(this);
        }
        if(o.equals("Six"))
        {
            System.out.println("Six player game");
            model.setPlayers(6);
            view.setPanel(o, model);
            view.playerNameActionListener(this);
        }
        if(o.equals("Submit"))
        {
            addNames();
            model.initializeGame();
            view.setPanel(o, model);
            view.boardActionListener(this);
        }
        if(o.equals("State"))
        {
            mapStateOpen = true;
            view.boardOptions(o, model);
            view.mapStateActionListener(this);
        }
        if(o.equals("Attack"))
        {
            mapStateOpen = false;
            view.boardOptions(o, model);
            view.attackActionListener(this);
            view.attackListsActionListener(this);
            view.acCountryActionListener(this);
        }
        if(o.equals("End Turn"))
        {

        }
        if(o.equals("Battle"))
        {
            // Set Attacking and Defending country
            model.setDefendCountry(view.getACListValue());
            model.setAttackCountry(view.getOCListValue());

            // Set troops
            model.setACountryTroops();
            model.setDCountryTroops();

            view.boardOptions(o, model);
            view.chooseDiceActionListener(this);
        }
        if(o.equals("Select Dice"))
        {
            // Set Number of dice from JComboBox
            model.setAttDice(view.getADiceAmount());
            model.setDefDice(view.getDDiceAmount());
            view.boardOptions(o, model);
            view.battleActionListener(this);
        }
        if(o.equals("Roll"))
        {
            model.attackStage(model.getAttacker(), model.getDefender());
            view.boardOptions(o, model);
            view.attackActionListener(this);
            view.attackListsActionListener(this);
            view.acCountryActionListener(this);
        }
        if(o.equals("Exit Battle"))
        {
            // End Attack Phase
            model.endAttackPhase();
            view.boardOptions(o, model);
        }
        if(o.equals("Fortify"))
        {
            view.boardOptions(o, model);
            view.fortifyActionListener(this);
            view.fortifyListsActionListener(this);
        }
        if(o.equals("Move Troops"))
        {
            // Set Country values from lists
            model.setCurrentCountryName(view.getOwnedListValue());
            System.out.println("Current Country: " + model.getCurrentCountryName());
            model.setFortifiedCountryName(view.getAdjListValue());
            System.out.println("Fortifying: " + model.getFortifiedCountryName());

            view.boardOptions(o, model);
            view.fortifyActionListener(this);
        }
        if(o.equals("Submit Fortify"))
        {
            model.fortifyStage(model.getCurrentCountryName(), model.getFortifiedCountryName(), (view.getFortifyTroopAmount() + 1));
            view.boardOptions(o, model);
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
            // State of map GUI is open
            if(mapStateOpen)
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
        }

        if (o.equals(view.getACList()))
        {
            if(!view.getACList().getValueIsAdjusting())
            {
                view.updateACTroops();
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
     * @param String s, name of player
     * @param int n, player turn index
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
}

