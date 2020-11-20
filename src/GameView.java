import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.border.*;
import javax.swing.DefaultListModel;
import java.util.Observer;
import java.util.Observable;

/**
 * View class that represents the visualization of the data that RiskGame model contains
 *
 * @author Braden Norton
 * @version 11/17/20
 */
public class GameView extends JFrame
{
    // JFrame
    private JFrame mapState, attackFrame, diceF, battleF, fortifyF, setFortifyF;

    // Boolean for open JFrames
    private boolean mapStateOpen = false;
    private boolean attackFrameOpen = false;
    private boolean twoFramesOpen = false;
    private boolean selectFortifyAmount = false;

    // JPanels
    private JPanel mPanel,bPanel;

    // JButtons
    // Main menu buttons
    private JButton start,quit;

    // Player amount buttons
    private JButton p2,p3,p4,p5,p6;

    // Submit player name
    private JButton submit,menuReturn;

    // Game Board options
    private JButton countries,state,attack,fortify,endTurn;

    // JTextFields
    // Player name input
    private JTextField name1,name2,name3,name4,name5,name6;

    // GUI For Game Board
    // Game state
    private RiskGame model;

    private JList playerList, oc1,oc2,oc3,oc4,oc5,oc6,ocCurrent,aCountries;

    private JButton back,battle;

    private JTextField name,troops,countryTroops,continentTroops;

    private JLabel l1,l2,l3,l4,l5,l6;

    private JTextArea turn;

    private DefaultListModel dList;

    private JScrollPane jp1,jp2;

    // Attack GUI
    private JTextField ocTroops, acTroops;
    private int aResidingTroops;

    // Battle GUI
    private JButton roll, exitBattle, selectDice;
    private JTextField att, def, attTroops, defTroops,attDiceAmount,defDiceAmount;
    private JTextArea results;
    private JComboBox attDice, defDice;

    // Fortify GUI
    private JButton moveTroops, submitFortify;
    private JList owned,adj;
    private DefaultListModel adjModel;
    private JComboBox movableTroops;
    private JScrollPane ocSP, adjSP;

    /**
     * Constructor for objects of class GUI
     */
    public GameView()
    {
        setTitle("Risk GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 300));
        setLocationRelativeTo(null);
        setResizable(false);
        add(menuGUI());
        pack();
        setVisible(true);
        toFront();
    }

    private JPanel menuGUI()
    {
        // Main Panel
        mPanel = new JPanel();
        mPanel.setLayout(new GridLayout(3,1));

        // Text Display
        JPanel lP = new JPanel();
        lP.setLayout(new GridLayout(2,1));

        JLabel welcome = new JLabel("Welcome to Risk!");
        welcome.setFont(new Font("Serif", Font.BOLD, 20));
        welcome.setHorizontalAlignment(JLabel.CENTER);

        JLabel choose = new JLabel("Select an option below to start the game or quit!");
        choose.setFont(new Font("Serif", Font.BOLD, 12));
        choose.setHorizontalAlignment(JLabel.CENTER);

        lP.add(welcome);
        lP.add(choose);

        // Buttons
        start = new JButton("Start");
        start.setActionCommand("Start");

        quit = new JButton("Quit");
        quit.setActionCommand("Quit");

        // Add components to panel
        mPanel.add(lP); // add welcome messages
        mPanel.add(start);
        mPanel.add(quit);

        // Return panel
        return mPanel;
    }

    private JPanel playerAmountGUI()
    {
        // Main panel
        JPanel pPanel = new JPanel();
        pPanel.setLayout(new GridLayout(6,1));

        // Text display
        JLabel players = new JLabel("Please select number of players!");
        players.setFont(new Font("Serif", Font.BOLD, 18));
        players.setHorizontalAlignment(JLabel.CENTER);

        // Buttons
        p2 = new JButton("Two Players");
        p2.setActionCommand("Two");

        p3 = new JButton("Three Players");
        p3.setActionCommand("Three");

        p4 = new JButton("Four Players");
        p4.setActionCommand("Four");

        p5 = new JButton("Five Players");
        p5.setActionCommand("Five");

        p6 = new JButton("Six Players");
        p6.setActionCommand("Six");

        // Initialize player name selection GUI
        // JTextFields for all options
        name1 = new JTextField(1);
        name1.setEditable(true);
        name1.setActionCommand("Name1");

        name2 = new JTextField(1);
        name2.setEditable(true);
        name2.setActionCommand("Name2");

        name3 = new JTextField(1);
        name3.setEditable(true);
        name3.setActionCommand("Name3");

        name4 = new JTextField(1);
        name4.setEditable(true);
        name4.setActionCommand("Name4");

        name5 = new JTextField(1);
        name5.setEditable(true);
        name5.setActionCommand("Name5");

        name6 = new JTextField(1);
        name6.setEditable(true);
        name6.setActionCommand("Name6");

        // JButtons
        // JButton
        submit = new JButton("Submit");
        submit.setActionCommand("Submit");

        menuReturn = new JButton("Back");
        menuReturn.setActionCommand("menuReturn");

        // Add components to panel
        pPanel.add(players);
        pPanel.add(p2);
        pPanel.add(p3);
        pPanel.add(p4);
        pPanel.add(p5);
        pPanel.add(p6);

        // Return Panel
        return pPanel;
    }

    private JPanel twoPlayersGUI()
    {
        // Main panel
        JPanel twoPanel = new JPanel();
        twoPanel.setLayout(new GridLayout(3,1));

        // JLabels
        JLabel n1 = new JLabel("Player 1 Name: ");
        JLabel n2 = new JLabel("Player 2 Name: ");

        // Add components to panel
        twoPanel.add(n1);
        twoPanel.add(name1);
        twoPanel.add(n2);
        twoPanel.add(name2);
        twoPanel.add(submit);
        twoPanel.add(menuReturn);

        // Return Panel
        return twoPanel;
    }

    private JPanel threePlayersGUI()
    {
        // Create main panel
        JPanel threePanel = new JPanel();
        threePanel.setLayout(new GridLayout(4,2));

        // JLabels
        JLabel n1 = new JLabel("Player 1 Name: ");
        JLabel n2 = new JLabel("Player 2 Name: ");
        JLabel n3 = new JLabel("Player 3 Name: ");

        // Add components to panel
        threePanel.add(n1);
        threePanel.add(name1);
        threePanel.add(n2);
        threePanel.add(name2);
        threePanel.add(n3);
        threePanel.add(name3);
        threePanel.add(submit);
        threePanel.add(menuReturn);

        // Return panel
        return threePanel;
    }

    private JPanel fourPlayersGUI()
    {
        // Create main panel
        JPanel fourPanel = new JPanel();
        fourPanel.setLayout(new GridLayout(5,2));

        // JLabels
        JLabel n1 = new JLabel("Player 1 Name: ");
        JLabel n2 = new JLabel("Player 2 Name: ");
        JLabel n3 = new JLabel("Player 3 Name: ");
        JLabel n4 = new JLabel("Player 4 Name: ");

        // Add components to Panel
        fourPanel.add(n1);
        fourPanel.add(name1);
        fourPanel.add(n2);
        fourPanel.add(name2);
        fourPanel.add(n3);
        fourPanel.add(name3);
        fourPanel.add(n4);
        fourPanel.add(name4);
        fourPanel.add(submit);
        fourPanel.add(menuReturn);

        // Return panel
        return fourPanel;
    }

    private JPanel fivePlayersGUI()
    {
        // Create main panel
        JPanel fivePanel = new JPanel();
        fivePanel.setLayout(new GridLayout(6,2));

        // JLabels
        JLabel n1 = new JLabel("Player 1 Name: ");
        JLabel n2 = new JLabel("Player 2 Name: ");
        JLabel n3 = new JLabel("Player 3 Name: ");
        JLabel n4 = new JLabel("Player 4 Name: ");
        JLabel n5 = new JLabel("Player 5 Name: ");

        // Add components to Panel
        fivePanel.add(n1);
        fivePanel.add(name1);
        fivePanel.add(n2);
        fivePanel.add(name2);
        fivePanel.add(n3);
        fivePanel.add(name3);
        fivePanel.add(n4);
        fivePanel.add(name4);
        fivePanel.add(n5);
        fivePanel.add(name5);
        fivePanel.add(submit);
        fivePanel.add(menuReturn);

        // Return panel
        return fivePanel;
    }

    private JPanel sixPlayersGUI()
    {
        // Create main Panel
        JPanel sixPanel = new JPanel();
        sixPanel.setLayout(new GridLayout(7,2));

        // JLabels
        JLabel n1 = new JLabel("Player 1 Name: ");
        JLabel n2 = new JLabel("Player 2 Name: ");
        JLabel n3 = new JLabel("Player 3 Name: ");
        JLabel n4 = new JLabel("Player 4 Name: ");
        JLabel n5 = new JLabel("Player 5 Name: ");
        JLabel n6 = new JLabel("Player 6 Name: ");

        // Add components to Panel
        sixPanel.add(n1);
        sixPanel.add(name1);
        sixPanel.add(n2);
        sixPanel.add(name2);
        sixPanel.add(n3);
        sixPanel.add(name3);
        sixPanel.add(n4);
        sixPanel.add(name4);
        sixPanel.add(n5);
        sixPanel.add(name5);
        sixPanel.add(n6);
        sixPanel.add(name6);
        sixPanel.add(submit);
        sixPanel.add(menuReturn);

        // Return Panel
        return sixPanel;
    }

    // fix 5 model calls
    private JPanel boardGUI(RiskGame m)
    {
        this.model = m;
        setSize(500,250);
        // Create Panel
        bPanel = new JPanel();
        bPanel.setLayout(new GridLayout(2,3));

        // Current Players name
        String s = m.getCurrentTurn().getName();

        // JTextArea in JScrollPane
        // Initally show turn order, then it'll display actions during player turns
        turn = new JTextArea("Turn Order\n");
        turn.setEditable(false);
        turn.append("---------------\n");
        JScrollPane jp = new JScrollPane(turn);
        for(int i=0; i<m.getPlayerAmount(); ++i)
        {
            turn.append("Turn " + (i+1) + ": " + m.getPlayers().get(i).getName());
            turn.append("\n");
        }
        turn.append("---------------\n");
        JScrollBar sb = jp.getVerticalScrollBar();
        sb.setValue( sb.getMaximum() );

        // JLabels
        //name = new JTextField(s + "'s turn");
        //name.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(m.getNames().get(1)+" Owns:"),BorderFactory.createEmptyBorder(5,5,5,5)));

        troops = new JTextField("" + model.getCurrentTurn().getTroops());
        troops.setEditable(false);
        troops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(m.getCurrentTurn().getName()+"'s Troops"),BorderFactory.createEmptyBorder(5,5,5,5)));
        troops.setHorizontalAlignment(JTextField.CENTER);

        countryTroops = new JTextField("" + m.getCountryBonus());
        countryTroops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(m.getCurrentTurn().getName()+"'s Reinforcements"),BorderFactory.createEmptyBorder(5,5,5,5)));
        countryTroops.setHorizontalAlignment(JTextField.CENTER);
        countryTroops.setEditable(false);

        continentTroops = new JTextField("" + m.getContinentBonus());
        continentTroops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(m.getCurrentTurn().getName()+"'s Bonus Troops"),BorderFactory.createEmptyBorder(5,5,5,5)));
        continentTroops.setHorizontalAlignment(JTextField.CENTER);
        continentTroops.setEditable(false);

        // JButtons
        // State of map
        state = new JButton("State of Map");
        state.setActionCommand("State");

        // Attack
        attack = new JButton("Attack");
        attack.setActionCommand("Attack");

        // Fortify
        fortify = new JButton("Fortify");
        fortify.setActionCommand("Fortify");

        // End Turn
        endTurn = new JButton("End Turn");
        endTurn.setActionCommand("End Turn");

        JPanel left = new JPanel();
        left.setLayout(new GridLayout(2,1));
        left.add(state);
        left.add(endTurn);

        JPanel right = new JPanel();
        right.setLayout(new GridLayout(2,1));
        right.add(attack);
        right.add(fortify);

        // Add components to panel
        bPanel.add(troops);
        bPanel.add(countryTroops);
        bPanel.add(continentTroops);
        bPanel.add(left);
        bPanel.add(jp);
        bPanel.add(right);

        // Return Panel
        return bPanel;
    }

    private void mapStateGUI(RiskGame m)
    {
        // Create JFrame
        mapState = new JFrame("State of map");
        mapState.setPreferredSize(new Dimension(400, 400));


        JPanel msPanel = new JPanel();
        msPanel.setLayout(new BoxLayout(msPanel, BoxLayout.X_AXIS));
        Container contentPane = mapState.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));


        l1 = new JLabel("Troops:  ");
        l2 = new JLabel("Troops:  ");
        l3 = new JLabel("Troops:  ");
        l4 = new JLabel("Troops:  ");
        l5 = new JLabel("Troops:  ");
        l6 = new JLabel("Troops:  ");

        if(m.getPlayerAmount() >= 2)
        {
            // Set JPanel for each player
            JPanel player1 = new JPanel();
            player1.setLayout(new BoxLayout(player1, BoxLayout.Y_AXIS));
            player1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(m.getNames().get(0) +" Owns:"),BorderFactory.createEmptyBorder(5,5,5,5)));

            JPanel player2 = new JPanel();
            player2.setLayout(new BoxLayout(player2, BoxLayout.Y_AXIS));
            player2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(m.getNames().get(1)+" Owns:"),BorderFactory.createEmptyBorder(5,5,5,5)));

            // JList for each players owned countries
            oc1 = new JList<String>(m.getPlayers().get(0).capturedCountriesToString().toArray(new String[0]));
            JScrollPane sp1 = new JScrollPane(oc1);
            oc2 = new JList<String>(m.getPlayers().get(1).capturedCountriesToString().toArray(new String[0]));
            JScrollPane sp2 = new JScrollPane(oc2);

            // Add Components to panels
            player1.add(sp1);
            player1.add(l1);
            player2.add(sp2);
            player2.add(l2);
            msPanel.add(player1);
            msPanel.add(player2);

            if(m.getPlayerAmount() >=3 )
            {
                mapState.setPreferredSize(new Dimension(600, 300));
                JPanel player3 = new JPanel();
                player3.setLayout(new BoxLayout(player3, BoxLayout.Y_AXIS));
                player3.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(m.getNames().get(2) +" Owns:"),BorderFactory.createEmptyBorder(5,5,5,5)));
                oc3 = new JList<String>(m.getPlayers().get(2).capturedCountriesToString().toArray(new String[0]));
                JScrollPane sp3 = new JScrollPane(oc3);
                player3.add(sp3);
                player3.add(l3);
                msPanel.add(player3);

                if(m.getPlayerAmount() >=4)
                {
                    mapState.setPreferredSize(new Dimension(800, 300));
                    JPanel player4 = new JPanel();
                    player4.setLayout(new BoxLayout(player4, BoxLayout.Y_AXIS));
                    player4.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(m.getNames().get(3) +" Owns:"),BorderFactory.createEmptyBorder(5,5,5,5)));
                    oc4 = new JList<String>(m.getPlayers().get(3).capturedCountriesToString().toArray(new String[0]));
                    JScrollPane sp4 = new JScrollPane(oc4);
                    player4.add(sp4);
                    player4.add(l4);
                    msPanel.add(player4);

                    if(m.getPlayerAmount() >= 5)
                    {
                        mapState.setPreferredSize(new Dimension(1000, 300));
                        JPanel player5 = new JPanel();
                        player5.setLayout(new BoxLayout(player5, BoxLayout.Y_AXIS));
                        player5.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(m.getNames().get(4) +" Owns:"),BorderFactory.createEmptyBorder(5,5,5,5)));
                        oc5 = new JList<String>(m.getPlayers().get(4).capturedCountriesToString().toArray(new String[0]));
                        JScrollPane sp5 = new JScrollPane(oc5);
                        player5.add(sp5);
                        player5.add(l5);
                        msPanel.add(player5);

                        if(m.getPlayerAmount() == 6)
                        {
                            mapState.setPreferredSize(new Dimension(1200, 300));
                            JPanel player6 = new JPanel();
                            player6.setLayout(new BoxLayout(player6, BoxLayout.Y_AXIS));
                            player6.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(m.getNames().get(5) +" Owns:"),BorderFactory.createEmptyBorder(5,5,5,5)));
                            oc6 = new JList<String>(m.getPlayers().get(5).capturedCountriesToString().toArray(new String[0]));
                            JScrollPane sp6 = new JScrollPane(oc6);
                            player6.add(sp6);
                            player6.add(l6);
                            msPanel.add(player6);

                        }
                    }
                }
            }
        }

        contentPane.add(msPanel);
        // Finish frame set up
        mapState.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mapState.pack();
        mapState.setResizable(false);
        mapState.setVisible(true);
    }

    private void attackGUI(RiskGame m)
    {
        // Create Frame
        attackFrame = new JFrame();
        attackFrame.setPreferredSize(new Dimension(500, 300));

        Container cp = attackFrame.getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));

        // Create Panel
        JPanel aPanel = new JPanel();
        aPanel.setLayout(new BoxLayout(aPanel, BoxLayout.Y_AXIS));
        aPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Own:"),BorderFactory.createEmptyBorder(5,5,5,5)));

        JPanel bPanel = new JPanel();
        bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.Y_AXIS));
        bPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Can Attack:"),BorderFactory.createEmptyBorder(5,5,5,5)));

        JPanel cPanel = new JPanel();
        cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
        cPanel.setSize(200,150);

        JPanel dPanel = new JPanel();
        dPanel.setLayout(new BoxLayout(dPanel, BoxLayout.Y_AXIS));
        dPanel.setSize(200,150);

        // Create JTextArea
        ocTroops = new JTextField();
        ocTroops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Residing Troops"),BorderFactory.createEmptyBorder(1,1,1,1)));
        ocTroops.setEditable(false);

        acTroops = new JTextField();
        acTroops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Residing Troops"),BorderFactory.createEmptyBorder(1,1,1,1)));
        acTroops.setEditable(false);

        // Create JLists
        // Owned Countries
        ocCurrent = new JList<String>(m.getCurrentTurn().capturedCountriesToString().toArray(new String[0]));
        jp1 = new JScrollPane(ocCurrent);

        // Attackable countries
        dList = new DefaultListModel();
        aCountries = new JList<String>(dList);
        jp2 = new JScrollPane(aCountries);

        // Buttons
        back = new JButton("Cancel");
        back.setActionCommand("Cancel");

        battle = new JButton("Attack");
        battle.setActionCommand("Battle");

        // Add components
        aPanel.add(jp1);
        aPanel.add(ocTroops);

        bPanel.add(jp2);
        bPanel.add(acTroops);

        cPanel.add(aPanel);
        cPanel.add(battle);

        dPanel.add(bPanel);
        dPanel.add(back);

        // Finish Setup
        cp.add(cPanel);
        cp.add(dPanel);

        attackFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        attackFrame.pack();
        attackFrame.setResizable(false);
        attackFrame.setVisible(true);
    }

    private void chooseDiceGUI(RiskGame m)
    {
        // Frame
        diceF = new JFrame();
        diceF.setPreferredSize(new Dimension(250, 200));
        Container cp = diceF.getContentPane();
        cp.setLayout(new GridLayout(2,1));

        // Panel
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

        // JComboBox
        JPanel cbp1 = new JPanel();
        cbp1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Attack Dice"),BorderFactory.createEmptyBorder(1,1,1,1)));
        attDice = new JComboBox(m.allowedAttDice(m.getAttackerTroops()));
        attDice.setActionCommand("Attack Dice");
        cbp1.add(attDice);

        JPanel cbp2 = new JPanel();
        cbp2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Defence Dice"),BorderFactory.createEmptyBorder(1,1,1,1)));
        defDice = new JComboBox(m.allowedDefDice(m.getDefenderTroops()));
        defDice.setActionCommand("Defence Dice");
        cbp2.add(defDice);

        // JButton
        selectDice = new JButton("Select Dice Amount");
        selectDice.setActionCommand("Select Dice");

        // Add to panel
        p.add(cbp1);
        p.add(cbp2);
        cp.add(p);
        cp.add(selectDice);

        // Finish frame setup
        diceF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        diceF.pack();
        diceF.setResizable(false);
        diceF.setVisible(true);
    }

    private void battleGUI(RiskGame m)
    {
        // Create JFrame
        battleF = new JFrame();
        battleF.setPreferredSize(new Dimension(700, 250));
        Container cp = battleF.getContentPane();
        cp.setLayout(new BorderLayout());

        // Create Panels
        JPanel attPanel = new JPanel();
        attPanel.setPreferredSize(new Dimension(100, 200));
        attPanel.setLayout(new GridLayout(3,1));

        JPanel midPanel = new JPanel();
        midPanel.setLayout(new GridLayout(2,1));

        JPanel defPanel = new JPanel();
        defPanel.setPreferredSize(new Dimension(100, 200));
        defPanel.setLayout(new GridLayout(3,1));

        // JTextFields
        // Attacking Country
        att = new JTextField(m.getAttacker());
        att.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Attacker"),BorderFactory.createEmptyBorder(1,1,1,1)));
        att.setHorizontalAlignment(JTextField.CENTER);
        att.setEditable(false);

        // Defending Country
        def = new JTextField(m.getDefender());
        def.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Defender"),BorderFactory.createEmptyBorder(1,1,1,1)));
        def.setHorizontalAlignment(JTextField.CENTER);
        def.setEditable(false);

        // Troops in attacking country
        attTroops = new JTextField("" + model.getACountryTroops());
        attTroops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Troops"),BorderFactory.createEmptyBorder(1,1,1,1)));
        attTroops.setHorizontalAlignment(JTextField.CENTER);
        attTroops.setEditable(false);

        // Troops in defending country
        defTroops = new JTextField("" + model.getACountryTroops());
        defTroops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Troops"),BorderFactory.createEmptyBorder(1,1,1,1)));
        defTroops.setHorizontalAlignment(JTextField.CENTER);
        defTroops.setEditable(false);

        // Attack Dice Amount
        attDiceAmount = new JTextField("" + model.getAttDiceAmount());
        attDiceAmount.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Dice"),BorderFactory.createEmptyBorder(1,1,1,1)));
        attDiceAmount.setHorizontalAlignment(JTextField.CENTER);
        attDiceAmount.setEditable(false);

        // Defence Dice Amount
        defDiceAmount = new JTextField("" + model.getDefDiceAmount());
        defDiceAmount.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Dice"),BorderFactory.createEmptyBorder(1,1,1,1)));
        defDiceAmount.setHorizontalAlignment(JTextField.CENTER);
        defDiceAmount.setEditable(false);

        // Results of roll
        results = new JTextArea("Battle For " + model.getDefender()+"\n");
        results.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Results"),BorderFactory.createEmptyBorder(1,1,1,1)));
        results.setEditable(false);

        // JButton
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new GridLayout(1,2));

        roll = new JButton("Roll");
        roll.setActionCommand("Roll");

        exitBattle = new JButton("Exit Battle");
        exitBattle.setActionCommand("Exit Battle");

        bPanel.add(roll);
        bPanel.add(exitBattle);

        // Add components
        attPanel.add(att);
        attPanel.add(attTroops);
        attPanel.add(attDiceAmount);

        midPanel.add(results);
        midPanel.add(bPanel);

        defPanel.add(def);
        defPanel.add(defTroops);
        defPanel.add(defDiceAmount);

        cp.add(attPanel, BorderLayout.WEST);
        cp.add(midPanel, BorderLayout.CENTER);
        cp.add(defPanel, BorderLayout.EAST);

        // Finish Setup
        battleF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        battleF.pack();
        battleF.setResizable(false);
        battleF.setVisible(true);
    }

    private void fortifyGUI(RiskGame m)
    {
        /**
         * // Fortify GUI
         * private JButton moveTroops, submitFortify;
         * private JList owned,adj;
         * private DefaultListModel adjModel;
         * private JComboBox movableTroops;
         * private JScrollPane ocSP, adjSP;
         */
        this.model = m;

        // Frame
        fortifyF = new JFrame();
        Container cp = fortifyF.getContentPane();
        cp.setLayout(new GridLayout(2,1));

        // Panel
        JPanel countriesListPanel = new JPanel();
        countriesListPanel.setLayout(new GridLayout(1,2));

        // Lists
        owned = new JList<String>(model.getCurrentTurn().capturedCountriesToString().toArray(new String[0]));
        ocSP = new JScrollPane(owned);
        ocSP.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Owned"),BorderFactory.createEmptyBorder(1,1,1,1)));

        adjModel = new DefaultListModel();
        adj = new JList<String>(adjModel);
        adjSP = new JScrollPane(adj);
        adjSP.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Fortifiable"),BorderFactory.createEmptyBorder(1,1,1,1)));

        // Button
        moveTroops = new JButton("Move Troops");
        moveTroops.setActionCommand("Move Troops");

        // Set Content Pane
        countriesListPanel.add(ocSP);
        countriesListPanel.add(adjSP);

        cp.add(countriesListPanel);
        cp.add(moveTroops);

        // Finish Frame setup
        fortifyF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fortifyF.pack();
        fortifyF.setResizable(false);
        fortifyF.setVisible(true);
    }

    private void fortifyAmountGUI(RiskGame m)
    {
        // Frame
        setFortifyF = new JFrame();
        Container cp = setFortifyF.getContentPane();
        cp.setLayout(new GridLayout(2,1));

        // Button
        submitFortify = new JButton("Submit");
        submitFortify.setActionCommand("Submit Fortify");

        // ComboBox
        JPanel cb = new JPanel();
        cb.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Move"),BorderFactory.createEmptyBorder(1,1,1,1)));
        ArrayList<String> validTroops = new ArrayList<>(model.getValidMovementAmount(model.getCurrentCountryName()));
        movableTroops = new JComboBox(validTroops.toArray());
        movableTroops.setActionCommand("Movable Troops");
        cb.add(movableTroops);

        // Set content pane
        cp.add(cb);
        cp.add(submitFortify);

        // Finish frame setup
        setFortifyF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setFortifyF.pack();
        setFortifyF.setResizable(false);
        setFortifyF.setVisible(true);
    }

    /**
     * Navigator through the main menu while setting the game up using mapped command words on main menu JButtons.
     * The main menu has various options that require user input: Start/Quit, Number of players, Human/AI player type, player usernames
     * This method uses a new JPanel for each option to keep the JFrame simple and uncluttered
     *
     * @param s, command word assigned to menu button
     * @param m, Risk model of game that deals with data
     *
     * @author Braden Norton
     */
    public void setPanel(String s, RiskGame m)
    {
        //getContentPane().removeAll();
        if(s.equals("Start"))
        {
            getContentPane().removeAll();
            add(playerAmountGUI());
        }
        if(s.equals("menuReturn"))
        {
            getContentPane().removeAll();
            setSize(300,300);
            add(playerAmountGUI());
        }
        if(s.equals("Two"))
        {
            getContentPane().removeAll();
            setSize(300,150);
            add(twoPlayersGUI());
        }
        if(s.equals("Three"))
        {
            getContentPane().removeAll();
            setSize(300,200);
            add(threePlayersGUI());
        }
        if(s.equals("Four"))
        {
            getContentPane().removeAll();
            setSize(300,250);
            add(fourPlayersGUI());
        }
        if(s.equals("Five"))
        {
            getContentPane().removeAll();
            setSize(300,300);
            add(fivePlayersGUI());
        }
        if(s.equals("Six"))
        {
            getContentPane().removeAll();
            setSize(300,300);
            add(sixPlayersGUI());
        }
        if(s.equals("Submit"))
        {
            getContentPane().removeAll();
            setSize(750,200);
            add(boardGUI(m));
            revalidate();
        }
        revalidate();
    }

    /**
     * Method to open the various board options when their respective buttons are pressed.
     * Using an ActionListener/CommandWord setup, this method will open up the requested JFrame as a pop-up to the main board.
     *
     * @param s, commandWord assigned to the buttons
     * @param m, model of main game
     *
     * @author Braden Norton
     */
    void boardOptions(String s, RiskGame m)
    {
        if(s.equals("State"))
        {
            if(attackFrameOpen){attackFrame.dispose(); attackFrameOpen = false;}
            mapStateGUI(m);
            mapStateOpen = true;
        }
        if(s.equals("Attack"))
        {
            if(mapStateOpen){mapState.dispose(); mapStateOpen = false;}
            attackGUI(m);
            attackFrameOpen = true;
        }
        if(s.equals("Battle"))
        {
            attackFrame.dispose();
            chooseDiceGUI(m);
        }
        if(s.equals("Select Dice"))
        {
            diceF.dispose();
            battleGUI(m);
        }
        if(s.equals("Roll"))
        {
            updateBattle(m);
            roll.setEnabled(false);
        }
        if(s.equals("Exit Battle"))
        {
            battleF.dispose();

            // Print results to results window on main board
            if(model.getAttackResult()){updateTurnArea(model.getCurrentTurn().getName() + " has claimed " + model.getDefender());}
            else{updateTurnArea(model.getCurrentTurn().getName() + " has exited the attack!");}

            // Adjust number of troops
            setTroops(""+model.getCurrentTurn().getTroops());
            revalidate();
        }
        if(s.equals("Fortify"))
        {
            fortifyGUI(m);
        }
        if(s.equals("Move Troops"))
        {
            selectFortifyAmount = true;
            fortifyF.dispose();
            fortifyAmountGUI(m);
        }
        if(s.equals("Submit Fortify"))
        {
            selectFortifyAmount = false;
            setFortifyF.dispose();
            updateTurnArea(model.getCurrentCountryName() + " has fortified " + model.getFortifiedCountryName() + " by " + model.getFortifiedAmount() + " troops!");
            fortify.setEnabled(false);
            attack.setEnabled(false);
        }
    }

    /**
     * All the ActionListeners for the various components within possible GUI options
     * GUIs: Main menu, player selection menu, main board, attack, battle, dice select, map state
     *
     * @author Braden Norton
     */
    public void menuActionListener(ActionListener o)
    {
        // Main menu
        start.addActionListener(o);
        quit.addActionListener(o);
    }

    public void playerAmountActionListener(ActionListener o)
    {
        // Player menu
        p2.addActionListener(o);
        p3.addActionListener(o);
        p4.addActionListener(o);
        p5.addActionListener(o);
        p6.addActionListener(o);
    }

    public void playerNameActionListener(ActionListener o)
    {
        // JTextFields
        name1.addActionListener(o);
        name2.addActionListener(o);
        name3.addActionListener(o);
        name4.addActionListener(o);
        name5.addActionListener(o);
        name6.addActionListener(o);

        // Buttons
        submit.addActionListener(o);
        menuReturn.addActionListener(o);
    }

    public void boardActionListener(ActionListener o)
    {
        state.addActionListener(o);
        attack.addActionListener(o);
        fortify.addActionListener(o);
        endTurn.addActionListener(o);
    }

    public void battleActionListener(ActionListener o)
    {
        roll.addActionListener(o);
        exitBattle.addActionListener(o);
    }

    public void mapStateActionListener(ListSelectionListener o)
    {
        oc1.addListSelectionListener(o);
        oc2.addListSelectionListener(o);
        if(model.getPlayerAmount() == 3)
        {
            oc3.addListSelectionListener(o);
        }
        if(model.getPlayerAmount() == 4)
        {
            oc3.addListSelectionListener(o);
            oc4.addListSelectionListener(o);
        }
        if(model.getPlayerAmount() == 5)
        {
            oc3.addListSelectionListener(o);
            oc4.addListSelectionListener(o);
            oc5.addListSelectionListener(o);
        }
        if(model.getPlayerAmount() == 6)
        {
            oc3.addListSelectionListener(o);
            oc4.addListSelectionListener(o);
            oc5.addListSelectionListener(o);
            oc6.addListSelectionListener(o);
        }
    }

    public void attackActionListener(ActionListener o)
    {
        battle.addActionListener(o);
    }

    public void chooseDiceActionListener(ActionListener o)
    {
        selectDice.addActionListener(o);
        attDice.addActionListener(o);
        defDice.addActionListener(o);
    }

    public void fortifyActionListener(ActionListener o)
    {
        if(selectFortifyAmount)
        {
            submitFortify.addActionListener(o);
            movableTroops.addActionListener(o);
        }
        moveTroops.addActionListener(o);
    }

    /**
     * ListSelectionListeners for the various JList components
     *
     * @author Braden Norton
     */
    public void acCountryActionListener(ListSelectionListener o)
    {
        aCountries.addListSelectionListener(o);
    }

    public void removeACCountryActionListener(ListSelectionListener o)
    {
        aCountries.removeListSelectionListener(o);
    }

    public void attackListsActionListener(ListSelectionListener o)
    {
        ocCurrent.addListSelectionListener(o);
    }

    public void fortifyListsActionListener(ListSelectionListener o)
    {
        owned.addListSelectionListener(o);
        adj.addListSelectionListener(o);
    }

    public void removeAdjActionListener(ListSelectionListener o)
    {
        adj.removeListSelectionListener(o);
    }

    /**
     * PlayerAmountGUI: Getter and setter methods
     *
     * @author Braden Norton
     */
    public String getName1()
    {
        return name1.getText();
    }

    public String getName2()
    {
        return name2.getText();
    }

    public String getName3()
    {
        return name3.getText();
    }

    public String getName4()
    {
        return name4.getText();
    }

    public String getName5()
    {
        return name5.getText();
    }

    public String getName6()
    {
        return name6.getText();
    }

    /**
     * mapStateGUI: Getter and setter methods
     *
     * @author Braden Norton
     */
    int getPlayer1List(){return oc1.getSelectedIndex();}

    String getL1(){ return oc1.getSelectedValue().toString();}

    int getPlayer2List(){return oc2.getSelectedIndex();}

    String getL2(){return oc2.getSelectedValue().toString();}

    int getPlayer3List(){return oc3.getSelectedIndex();}

    String getL3() {return oc3.getSelectedValue().toString();}

    int getPlayer4List(){return oc4.getSelectedIndex();}

    String getL4() {return oc4.getSelectedValue().toString();}

    int getPlayer5List(){return oc5.getSelectedIndex();}

    String getL5(){return oc5.getSelectedValue().toString();}

    int getPlayer6List(){return oc6.getSelectedIndex();}

    String getL6(){return oc6.getSelectedValue().toString();}

    void setL1(int n){l1.setText("Troops: " + n);}

    void setL2(int n){l2.setText("Troops: " + n);}

    void setL3(int n){l3.setText("Troops: " + n);}

    void setL4(int n){l4.setText("Troops: " + n);}

    void setL5(int n){l5.setText("Troops: " + n);}

    void setL6(int n){l6.setText("Troops: " + n);}

    /**
     * boardGUI: Getter and setter methods
     *
     * @author Braden Norton
     */
    void updateTurnArea(String s)
    {
        //turn.append("\n---------------");
        turn.append("\n"+s);
        turn.selectAll();
    }

    void setTroops(String s){ troops.setText("" + s); }

    /**
     * attackGUI: Getter and setter methods
     *
     * @author Braden Norton
     */
    JList getOCList() { return ocCurrent; }

    JList getACList() { return aCountries; }

    int getOCListIndex() {return ocCurrent.getSelectedIndex();}

    String getOCListValue(){ return ocCurrent.getSelectedValue().toString();}

    int getACListIndex() { return aCountries.getSelectedIndex();}

    String getACListValue() { return aCountries.getSelectedValue().toString();}

    /**
     * Displays the possible attackable countries
     * Depends on what country is selected from the owned countries list
     *
     * @author Braden Norton
     */
    void updateACList(String[] l)
    {
        dList.clear();
        for(int i=0; i<l.length; ++i)
        {
            dList.addElement(l[i]);
        }
    }

    void updateACTroops()
    {
        acTroops.setText("" + model.getTroopsByName(aCountries.getSelectedValue().toString()));
    }

    void setOCTroops(int s) { ocTroops.setText(""+s); }

    /**
     * battleGUI: Getter and setter methods
     *
     *
     * @author Braden Norton
     */
    String getADiceAmount() { return attDice.getSelectedItem().toString(); }

    String getDDiceAmount() { return defDice.getSelectedItem().toString(); }

    String getAttName() { return att.getText(); }

    String getDefName() { return def.getText(); }

    void setATroopAmount() { attTroops.setText("" + model.getACountryTroops()); }

    void setDTroopAmount() { defTroops.setText("" + model.getDCountryTroops());}

    /**
     * Updates the results text area in battle gui frame with results of roll
     * Displays: Attack dice roll, Defence dice roll, amount of attack troops lost, amount of defence troops lost
     *
     * @author Braden Norton
     */
    void updateResult()
    {
        results.setText("");
        if(model.getAttDiceAmount() == 1 || model.getDefDiceAmount() == 1)
        {
            results.append("Attack Roll: " + model.getARolls(0)+"\n");
            results.append("Defence Roll: " + model.getDRolls(0)+"\n");
        }
        else if((model.getAttDiceAmount() == 2) && (model.getDefDiceAmount() == 2))
        {
            results.append("Attack Roll: " + model.getARolls(0)+ ", " + model.getARolls(1) +"\n");
            results.append("Defence Roll: " + model.getDRolls(0)+ ", " + model.getDRolls(1) +"\n");
        }

        results.append("Attack Losses: " + model.getAttLosses()+"\n");
        results.append("Defence Losses: " + model.getDefLosses());
    }

    /**
     * Updates the battleGUI to reflect changes game state changes from battle
     *
     * @param m, model of the game
     *
     * @author Braden Norton
     */
    private void updateBattle(RiskGame m)
    {
        this.model = m;
        setATroopAmount();
        setDTroopAmount();
        updateResult();
    }

    /**
     * fortifyGUI: Getter and setter methods
     *
     * @author Braden Norton
     */
    String getOwnedListValue() { return owned.getSelectedValue().toString(); }

    String getAdjListValue() { return adj.getSelectedValue().toString(); }

    JList getOwnedList() { return owned; }

    JList getAdjList() { return adj; }

    int getFortifyTroopAmount() { return movableTroops.getSelectedIndex(); }

    void setAdjList(ArrayList<String> a)
    {
        adjModel.clear();
        for(int i=0; i<a.size(); ++i)
        {
            adjModel.addElement(a.get(i));
        }
    }

}
