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
    private JFrame attackFrame, diceF, battleF, fortifyF, setFortifyF;

    // Boolean for open JFrames
    private boolean mapStateOpen = false;
    private boolean attackFrameOpen = false;
    private boolean twoFramesOpen = false;
    private boolean selectFortifyAmount = false;

    // JPanels
    private JPanel mPanel,bPanel, topPanel, msPanel;

    // JButtons
    // Main menu buttons
    private JButton start,quit;

    // Player amount buttons
    private JButton p2,p3,p4,p5,p6;

    // AI Selection Buttons
    private JCheckBox ai1, ai2, ai3, ai4, ai5, ai6;

    // Submit player name
    private JButton submit,menuReturn;

    // Game Board options
    private JButton countries,state,attack,fortify,endTurn;

    // JTextFields
    // Player name input
    private JTextField name1,name2,name3,name4,name5,name6;

    // GUI For Game Board
    private JList playerList, oc1,oc2,oc3,oc4,oc5,oc6,ocCurrent,aCountries;

    private JButton back,battle;

    private JTextField name,troops,countryTroops,continentTroops;

    private JLabel l1,l2,l3,l4,l5,l6;

    private JTextArea turn;

    private JScrollPane jp1,jp2;

    // Attack GUI
    private JTextField ocTroops, acTroops;
    private DefaultListModel currentPlayerOC,dList;

    // Battle GUI
    private JButton roll, exitBattle, selectDice;
    private JTextField att, def, attTroops, defTroops,attDiceAmount,defDiceAmount;
    private JTextArea results;
    private JComboBox attDice, defDice;
    private DefaultListModel allowedAttDice, allowedDefDice;

    // Fortify GUI
    private JButton moveTroops, submitFortify,cancelFortify;
    private JList owned,adj;
    private DefaultListModel ocModel, adjModel;
    private JComboBox movableTroops;
    private JScrollPane ocSP, adjSP;

    // Model Data to represent
    private String cpName;
    private ArrayList<Player> allPlayers;
    private ArrayList<String> allPlayerNames;
    private DefaultListModel p1OC, p2OC, p3OC,p4OC,p5OC,p6OC;

    private int cpTroops;
    private int cpCountryBonus;
    private int cpContinentBonus;

    /**
     * Constructor for objects of class GUI
     */
    public GameView()
    {
        // Data Initialization
        allPlayerNames = new ArrayList<>();
        allPlayers = new ArrayList<>();

        setTitle("Risk GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setPreferredSize(new Dimension(300, 300));
        setLocationRelativeTo(null);
        setResizable(false);
        add(menuGUI());
        pack();
        setVisible(true);
        toFront();
    }

    /**
     * JPanel that displays "Start" and "Quit" options
     *
     * @return
     */
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

    /**
     * JPanel that displays the option for amount of players and whether they're AI
     *
     * @return
     */
    private JPanel playerAmountGUI()
    {
        // Main panel
        JPanel pPanel = new JPanel();
        pPanel.setLayout(new GridLayout(6,1));

        // Text display
        JLabel players = new JLabel("Please select number of players!");
        players.setFont(new Font("Serif", Font.BOLD, 18));
        players.setHorizontalAlignment(JLabel.CENTER);

        // AI Selection Buttons
        ai1 = new JCheckBox("AI");
        ai1.setActionCommand("Player1 AI");

        ai2 = new JCheckBox("AI");
        ai2.setActionCommand("Player2 AI");

        ai3 = new JCheckBox("AI");
        ai3.setActionCommand("Player3 AI");

        ai4 = new JCheckBox("AI");
        ai4.setActionCommand("Player4 AI");

        ai5 = new JCheckBox("AI");
        ai5.setActionCommand("Player5 AI");

        ai6 = new JCheckBox("AI");
        ai6.setActionCommand("Player6 AI");

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

    /**
     *
     *
     * @return JPanel with the options
     */
    private JPanel twoPlayersGUI()
    {
        // Main panel
        JPanel twoPanel = new JPanel();
        twoPanel.setLayout(new GridLayout(3,2));

        // JPanel
        JPanel tp = new JPanel();
        tp.setLayout(new GridLayout(1,2));

        JPanel tp2 = new JPanel();
        tp2.setLayout(new GridLayout(1,2));

        // JLabels
        JLabel n1 = new JLabel("Player 1 Name: ");
        JLabel n2 = new JLabel("Player 2 Name: ");

        // Add components to panel
        tp.add(name1);
        tp.add(ai1);

        tp2.add(name2);
        tp2.add(ai2);

        twoPanel.add(n1);
        twoPanel.add(tp);
        twoPanel.add(n2);
        twoPanel.add(tp2);
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

        // JPanel
        JPanel tp = new JPanel();
        tp.setLayout(new GridLayout(1,2));

        JPanel tp2 = new JPanel();
        tp2.setLayout(new GridLayout(1,2));

        JPanel tp3 = new JPanel();
        tp3.setLayout(new GridLayout(1,2));

        // Add components to panel
        tp.add(name1);
        tp.add(ai1);

        tp2.add(name2);
        tp2.add(ai2);

        tp3.add(name3);
        tp3.add(ai3);

        threePanel.add(n1);
        threePanel.add(tp);
        threePanel.add(n2);
        threePanel.add(tp2);
        threePanel.add(n3);
        threePanel.add(tp3);
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

        // JPanel
        JPanel tp1 = new JPanel();
        tp1.setLayout(new GridLayout(1,2));

        JPanel tp2 = new JPanel();
        tp2.setLayout(new GridLayout(1,2));

        JPanel tp3 = new JPanel();
        tp3.setLayout(new GridLayout(1,2));

        JPanel tp4 = new JPanel();
        tp4.setLayout(new GridLayout(1,2));

        JPanel tp5 = new JPanel();
        tp5.setLayout(new GridLayout(1,2));

        JPanel tp6 = new JPanel();
        tp6.setLayout(new GridLayout(1,2));

        // Add components to Panel
        tp1.add(name1);
        tp1.add(ai1);

        tp2.add(name2);
        tp2.add(ai2);

        tp3.add(name3);
        tp3.add(ai3);

        tp4.add(name4);
        tp4.add(ai4);

        fourPanel.add(n1);
        fourPanel.add(tp1);
        fourPanel.add(n2);
        fourPanel.add(tp2);
        fourPanel.add(n3);
        fourPanel.add(tp3);
        fourPanel.add(n4);
        fourPanel.add(tp4);
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

        // JPanel
        JPanel tp1 = new JPanel();
        tp1.setLayout(new GridLayout(1,2));

        JPanel tp2 = new JPanel();
        tp2.setLayout(new GridLayout(1,2));

        JPanel tp3 = new JPanel();
        tp3.setLayout(new GridLayout(1,2));

        JPanel tp4 = new JPanel();
        tp4.setLayout(new GridLayout(1,2));

        JPanel tp5 = new JPanel();
        tp5.setLayout(new GridLayout(1,2));

        JPanel tp6 = new JPanel();
        tp6.setLayout(new GridLayout(1,2));

        // Add components to Panel
        tp1.add(name1);
        tp1.add(ai1);

        tp2.add(name2);
        tp2.add(ai2);

        tp3.add(name3);
        tp3.add(ai3);

        tp4.add(name4);
        tp4.add(ai4);

        tp5.add(name5);
        tp5.add(ai5);

        fivePanel.add(n1);
        fivePanel.add(tp1);
        fivePanel.add(n2);
        fivePanel.add(tp2);
        fivePanel.add(n3);
        fivePanel.add(tp3);
        fivePanel.add(n4);
        fivePanel.add(tp4);
        fivePanel.add(n5);
        fivePanel.add(tp5);
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

        // JPanel
        JPanel tp1 = new JPanel();
        tp1.setLayout(new GridLayout(1,2));

        JPanel tp2 = new JPanel();
        tp2.setLayout(new GridLayout(1,2));

        JPanel tp3 = new JPanel();
        tp3.setLayout(new GridLayout(1,2));

        JPanel tp4 = new JPanel();
        tp4.setLayout(new GridLayout(1,2));

        JPanel tp5 = new JPanel();
        tp5.setLayout(new GridLayout(1,2));

        JPanel tp6 = new JPanel();
        tp6.setLayout(new GridLayout(1,2));

        // Add components to Panel
        tp1.add(name1);
        tp1.add(ai1);

        tp2.add(name2);
        tp2.add(ai2);

        tp3.add(name3);
        tp3.add(ai3);

        tp4.add(name4);
        tp4.add(ai4);

        tp5.add(name5);
        tp5.add(ai5);

        tp6.add(name6);
        tp6.add(ai6);

        sixPanel.add(n1);
        sixPanel.add(tp1);
        sixPanel.add(n2);
        sixPanel.add(tp2);
        sixPanel.add(n3);
        sixPanel.add(tp3);
        sixPanel.add(n4);
        sixPanel.add(tp4);
        sixPanel.add(n5);
        sixPanel.add(tp5);
        sixPanel.add(n6);
        sixPanel.add(tp6);
        sixPanel.add(submit);
        sixPanel.add(menuReturn);

        // Return Panel
        return sixPanel;
    }

    private JPanel boardResultsGUI()
    {
        // Create Panel
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,3));

        // Display amount of owned troops
        troops = new JTextField("" + cpTroops);
        troops.setEditable(false);
        troops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(cpName + "'s Troops"),BorderFactory.createEmptyBorder(5,5,5,5)));
        troops.setHorizontalAlignment(JTextField.CENTER);

        // Display amount of bonus troops from owned countries
        countryTroops = new JTextField("" + cpCountryBonus);
        countryTroops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(cpName+ "'s Reinforcements"),BorderFactory.createEmptyBorder(5,5,5,5)));
        countryTroops.setHorizontalAlignment(JTextField.CENTER);
        countryTroops.setEditable(false);

        // Display amount of bonus troops from owned continents
        continentTroops = new JTextField("" + cpContinentBonus);
        continentTroops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(cpName + "'s Bonus Troops"),BorderFactory.createEmptyBorder(5,5,5,5)));
        continentTroops.setHorizontalAlignment(JTextField.CENTER);
        continentTroops.setEditable(false);

        // JButtons
        // Attack
        attack = new JButton("Attack");
        attack.setActionCommand("Attack");

        // Fortify
        fortify = new JButton("Fortify");
        fortify.setActionCommand("Fortify");

        // End Turn
        endTurn = new JButton("End Turn");
        endTurn.setActionCommand("End Turn");

        // Add components to panel
        topPanel.add(attack);
        topPanel.add(fortify);
        topPanel.add(endTurn);
        topPanel.add(troops);
        topPanel.add(countryTroops);
        topPanel.add(continentTroops);

        // Return Panel
        return topPanel;
    }


    private JScrollPane boardGUI(String name)
    {
        // Create Panel
        bPanel = new JPanel();

        // JTextArea in JScrollPane
        // Display actions during player turns
        turn = new JTextArea("---------------\n");
        turn.setEditable(false);
        turn.append(name + "'s turn\n");
        JScrollPane jp = new JScrollPane(turn);
        turn.append("---------------\n");

        // Return Panel
        return jp;
    }


    private JPanel mapStateGUI()
    {
        msPanel = new JPanel();
        msPanel.setLayout(new BoxLayout(msPanel, BoxLayout.X_AXIS));


        l1 = new JLabel("Troops:  ");
        l2 = new JLabel("Troops:  ");
        l3 = new JLabel("Troops:  ");
        l4 = new JLabel("Troops:  ");
        l5 = new JLabel("Troops:  ");
        l6 = new JLabel("Troops:  ");

        if(allPlayerNames.size() >= 2)
        {
            // Set JPanel for each player
            JPanel player1 = new JPanel();
            player1.setLayout(new BoxLayout(player1, BoxLayout.Y_AXIS));
            player1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(allPlayers.get(0).getName() +" Owns:"),BorderFactory.createEmptyBorder(5,5,5,5)));

            JPanel player2 = new JPanel();
            player2.setLayout(new BoxLayout(player2, BoxLayout.Y_AXIS));
            player2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(allPlayers.get(1).getName()+" Owns:"),BorderFactory.createEmptyBorder(5,5,5,5)));

            // DefaultModelLists

            // JList for each players owned countries
            p1OC = new DefaultListModel();
            oc1 = new JList<>(p1OC);
            JScrollPane sp1 = new JScrollPane(oc1);

            p2OC = new DefaultListModel();
            oc2 = new JList<String>(p2OC);
            JScrollPane sp2 = new JScrollPane(oc2);

            // Add Components to panels
            player1.add(sp1);
            player1.add(l1);
            player2.add(sp2);
            player2.add(l2);
            msPanel.add(player1);
            msPanel.add(player2);

            if(allPlayers.size() >=3 )
            {
                JPanel player3 = new JPanel();
                player3.setLayout(new BoxLayout(player3, BoxLayout.Y_AXIS));
                player3.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(allPlayers.get(2).getName() +" Owns:"),BorderFactory.createEmptyBorder(5,5,5,5)));
                p3OC = new DefaultListModel();
                oc3 = new JList<>(p3OC);
                JScrollPane sp3 = new JScrollPane(oc3);
                player3.add(sp3);
                player3.add(l3);
                msPanel.add(player3);

                if(allPlayers.size() >=4)
                {
                    JPanel player4 = new JPanel();
                    player4.setLayout(new BoxLayout(player4, BoxLayout.Y_AXIS));
                    player4.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(allPlayers.get(3).getName() +" Owns:"),BorderFactory.createEmptyBorder(5,5,5,5)));
                    p4OC = new DefaultListModel();
                    oc4 = new JList<>(p4OC);
                    JScrollPane sp4 = new JScrollPane(oc4);
                    player4.add(sp4);
                    player4.add(l4);
                    msPanel.add(player4);

                    if(allPlayers.size() >= 5)
                    {
                        JPanel player5 = new JPanel();
                        player5.setLayout(new BoxLayout(player5, BoxLayout.Y_AXIS));
                        player5.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(allPlayers.get(4).getName() +" Owns:"),BorderFactory.createEmptyBorder(5,5,5,5)));
                        p5OC = new DefaultListModel();
                        oc5 = new JList<>(p5OC);
                        JScrollPane sp5 = new JScrollPane(oc5);
                        player5.add(sp5);
                        player5.add(l5);
                        msPanel.add(player5);

                        if(allPlayers.size() == 6)
                        {
                            JPanel player6 = new JPanel();
                            player6.setLayout(new BoxLayout(player6, BoxLayout.Y_AXIS));
                            player6.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(allPlayers.get(5).getName() +" Owns:"),BorderFactory.createEmptyBorder(5,5,5,5)));
                            p6OC = new DefaultListModel();
                            oc6 = new JList<>(p6OC);
                            JScrollPane sp6 = new JScrollPane(oc6);
                            player6.add(sp6);
                            player6.add(l6);
                            msPanel.add(player6);

                        }
                    }
                }
            }
        }

        return msPanel;
    }

    //
    private void attackGUI()
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
        currentPlayerOC = new DefaultListModel();
        ocCurrent = new JList<String>(currentPlayerOC);
        jp1 = new JScrollPane(ocCurrent);

        // Attackable countries
        dList = new DefaultListModel();
        aCountries = new JList<String>(dList);
        jp2 = new JScrollPane(aCountries);

        // Buttons
        back = new JButton("Cancel");
        back.setActionCommand("Cancel");

        battle = new JButton("Battle");
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

    // 2 Combo boxes
    private void chooseDiceGUI()
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
        attDice = new JComboBox();
        attDice.setActionCommand("Attack Dice");
        cbp1.add(attDice);

        JPanel cbp2 = new JPanel();
        cbp2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Defence Dice"),BorderFactory.createEmptyBorder(1,1,1,1)));
        defDice = new JComboBox();
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

    private void battleGUI()
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
        att = new JTextField();
        att.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Attacker"),BorderFactory.createEmptyBorder(1,1,1,1)));
        att.setHorizontalAlignment(JTextField.CENTER);
        att.setEditable(false);

        // Defending Country
        def = new JTextField();
        def.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Defender"),BorderFactory.createEmptyBorder(1,1,1,1)));
        def.setHorizontalAlignment(JTextField.CENTER);
        def.setEditable(false);

        // Troops in attacking country
        attTroops = new JTextField();
        attTroops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Troops"),BorderFactory.createEmptyBorder(1,1,1,1)));
        attTroops.setHorizontalAlignment(JTextField.CENTER);
        attTroops.setEditable(false);

        // Troops in defending country
        defTroops = new JTextField();
        defTroops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Troops"),BorderFactory.createEmptyBorder(1,1,1,1)));
        defTroops.setHorizontalAlignment(JTextField.CENTER);
        defTroops.setEditable(false);

        // Attack Dice Amount
        attDiceAmount = new JTextField();
        attDiceAmount.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Dice"),BorderFactory.createEmptyBorder(1,1,1,1)));
        attDiceAmount.setHorizontalAlignment(JTextField.CENTER);
        attDiceAmount.setEditable(false);

        // Defence Dice Amount
        defDiceAmount = new JTextField();
        defDiceAmount.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Dice"),BorderFactory.createEmptyBorder(1,1,1,1)));
        defDiceAmount.setHorizontalAlignment(JTextField.CENTER);
        defDiceAmount.setEditable(false);

        // Results of roll
        results = new JTextArea();
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

    private void fortifyGUI()
    {
        /**
         * // Fortify GUI
         * private JButton moveTroops, submitFortify;
         * private JList owned,adj;
         * private DefaultListModel adjModel;
         * private JComboBox movableTroops;
         * private JScrollPane ocSP, adjSP;
         */

        // Frame
        fortifyF = new JFrame();
        Container cp = fortifyF.getContentPane();
        cp.setLayout(new GridLayout(2,1));

        // Panel
        JPanel countriesListPanel = new JPanel();
        countriesListPanel.setLayout(new GridLayout(1,2));

        // Lists
        ocModel = new DefaultListModel();
        owned = new JList<String>(ocModel);
        ocSP = new JScrollPane(owned);
        ocSP.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Owned"),BorderFactory.createEmptyBorder(1,1,1,1)));

        adjModel = new DefaultListModel();
        adj = new JList<String>(adjModel);
        adjSP = new JScrollPane(adj);
        adjSP.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Fortifiable"),BorderFactory.createEmptyBorder(1,1,1,1)));

        // Button
        // Button Panel
        JPanel b = new JPanel();
        b.setLayout(new GridLayout(1,2));

        moveTroops = new JButton("Move Troops");
        moveTroops.setActionCommand("Move Troops");

        cancelFortify = new JButton("Cancel");
        cancelFortify.setActionCommand("Cancel Fortify");

        // Set Content Pane
        b.add(moveTroops);
        b.add(cancelFortify);

        countriesListPanel.add(ocSP);
        countriesListPanel.add(adjSP);

        cp.add(countriesListPanel);
        cp.add(b);

        // Finish Frame setup
        fortifyF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fortifyF.pack();
        fortifyF.setResizable(false);
        fortifyF.setVisible(true);
    }

    private void fortifyAmountGUI()
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
        movableTroops = new JComboBox();
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
     *
     * @author Braden Norton
     */
    public void setPanel(String s)
    {
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
            JPanel boardPanel = new JPanel();
            boardPanel.setLayout(new GridLayout(3,1));
            boardPanel.add(boardResultsGUI());
            boardPanel.add(boardGUI(cpName));
            boardPanel.add(mapStateGUI());
            add(boardPanel);
            setSize(1000,500);
        }
        revalidate();
    }

    /**
     * Method to open the various board options when their respective buttons are pressed.
     * Using an ActionListener/CommandWord setup, this method will open up the requested JFrame as a pop-up to the main board.
     *
     * @param s, commandWord assigned to the buttons
     *
     * @author Braden Norton
     */
    void boardOptions(String s)
    {
        if(s.equals("Attack"))
        {
            attackGUI();
            attackFrameOpen = true;
        }
        if(s.equals("Battle"))
        {
            attackFrame.dispose();
            chooseDiceGUI();
        }
        if(s.equals("Cancel"))
        {
            attackFrame.dispose();
        }
        if(s.equals("Select Dice"))
        {
            diceF.dispose();
            battleGUI();
        }
        if(s.equals("Roll"))
        {
            roll.setEnabled(false);
        }
        if(s.equals("Exit Battle"))
        {
            battleF.dispose();
            revalidate();
        }
        if(s.equals("Fortify"))
        {
            fortifyGUI();
        }
        if(s.equals("Cancel Fortify"))
        {
            fortifyF.dispose();
        }
        if(s.equals("Move Troops"))
        {
            selectFortifyAmount = true;
            fortifyF.dispose();
            fortifyAmountGUI();
        }
        if(s.equals("Submit Fortify"))
        {
            selectFortifyAmount = false;
            setFortifyF.dispose();
            fortify.setEnabled(false);
            attack.setEnabled(false);
        }
        if(s.equals("End Turn"))
        {
            getContentPane().removeAll();
            JPanel boardPanel = new JPanel();
            boardPanel.setLayout(new GridLayout(3,1));
            boardPanel.add(boardResultsGUI());
            boardPanel.add(boardGUI(cpName));
            boardPanel.add(mapStateGUI());
            add(boardPanel);
            setSize(1000,500);
            revalidate();
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

        // CheckBoxes
        ai1.addActionListener(o);
        ai2.addActionListener(o);
        ai3.addActionListener(o);
        ai4.addActionListener(o);
        ai5.addActionListener(o);
        ai6.addActionListener(o);

    }

    public void boardActionListener(ActionListener o)
    {
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
        if(allPlayers.size() == 3)
        {
            oc3.addListSelectionListener(o);
        }
        if(allPlayers.size() == 4)
        {
            oc3.addListSelectionListener(o);
            oc4.addListSelectionListener(o);
        }
        if(allPlayers.size() == 5)
        {
            oc3.addListSelectionListener(o);
            oc4.addListSelectionListener(o);
            oc5.addListSelectionListener(o);
        }
        if(allPlayers.size() == 6)
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
        back.addActionListener(o);
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
        cancelFortify.addActionListener(o);
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

    /**
     * PlayerAmountGUI: Getter and setter methods
     *
     * @author Braden Norton
     */
    public String getName1()
    {
        allPlayerNames.add(name1.getText());

        return name1.getText();
    }

    public String getName2()
    {
        allPlayerNames.add(name2.getText());
        return name2.getText();
    }

    public String getName3()
    {
        allPlayerNames.add(name3.getText());
        return name3.getText();
    }

    public String getName4()
    {
        allPlayerNames.add(name4.getText());
        return name4.getText();
    }

    public String getName5()
    {
        allPlayerNames.add(name5.getText());
        return name5.getText();
    }

    public String getName6()
    {
        allPlayerNames.add(name6.getText());
        return name6.getText();
    }

    Boolean getCB1()
    {
        if(ai1.isSelected()){return true;}
        else{return false;}
    }

    Boolean getCB2()
    {
        if(ai2.isSelected()){return true;}
        else{return false;}
    }

    Boolean getCB3()
    {
        if(ai3.isSelected()){return true;}
        else{return false;}
    }

    Boolean getCB4()
    {
        if(ai4.isSelected()){return true;}
        else{return false;}
    }

    Boolean getCB5()
    {
        if(ai5.isSelected()){return true;}
        else{return false;}
    }

    Boolean getCB6()
    {
        if(ai6.isSelected()){return true;}
        else{return false;}
    }

    void addPlayers(ArrayList<Player> p)
    {
        for(int i=0; i<p.size();++i)
        {
            allPlayers.add(p.get(i));
            System.out.println(p.get(i).getName() + " added!");
        }
        System.out.println(allPlayers.size());
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
    void setCurrentPlayerName(String s)
    {
        this.cpName = s;
    }

    void setCurrentPlayerTroops(int t)
    {
        this.cpTroops = t;
        //troops.setText("" + cpTroops);
    }

    void setCurrentPlayerReinforcements(int country, int continent)
    {
        this.cpCountryBonus = country;
        //countryTroops.setText(""+cpCountryBonus);

        this.cpContinentBonus = continent;
        //continentTroops.setText("" + cpContinentBonus);
    }

    void updateTurnArea(String s)
    {
        turn.append("\n"+s);
        turn.selectAll();
    }

    void setTroops(String s){ troops.setText("" + s); }


    void updateP1OCList(ArrayList<String> a)
    {
        if(!p1OC.isEmpty())
        {
            p1OC.clear();
        }

        for(int i =0; i<a.size(); ++i)
        {
            p1OC.addElement(a.get(i));
        }
    }

    void updateP2OCList(ArrayList<String> a)
    {
        if(!p2OC.isEmpty())
        {
            p2OC.clear();
        }
        for(int i =0; i<a.size(); ++i)
        {
            p2OC.addElement(a.get(i));
        }
    }

    void updateP3OCList(ArrayList<String> a)
    {
        if(!p3OC.isEmpty())
        {
            p3OC.clear();
        }
        for(int i =0; i<a.size(); ++i)
        {
            p3OC.addElement(a.get(i));
        }
    }

    void updateP4OCList(ArrayList<String> a)
    {
        if(!p4OC.isEmpty())
        {
            p4OC.clear();
        }
        for(int i =0; i<a.size(); ++i)
        {
            p4OC.addElement(a.get(i));
        }
    }

    void updateP5OCList(ArrayList<String> a)
    {
        if(!p5OC.isEmpty())
        {
            p5OC.clear();
        }
        for(int i =0; i<a.size(); ++i)
        {
            p5OC.addElement(a.get(i));
        }
    }

    void updateP6OCList(ArrayList<String> a)
    {
        if(!p6OC.isEmpty())
        {
            p6OC.clear();
        }
        for(int i =0; i<a.size(); ++i)
        {
            p6OC.addElement(a.get(i));
        }
    }

    /**
     * attackGUI: Getter and setter methods
     *
     * @author Braden Norton
     */
    JList getOCList() { return ocCurrent; }

    JList getACList() { return aCountries; }

    String getOCListValue(){ return ocCurrent.getSelectedValue().toString();}

    String getACListValue() { return aCountries.getSelectedValue().toString();}

    void updateOCList(ArrayList<String> l)
    {
        for(int i=0; i<l.size(); ++i)
        {
            currentPlayerOC.addElement(l.get(i));
        }
    }

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

    void updateACTroops(int t)
    {
        acTroops.setText("" + t);
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


    /**
     * Updates the battleGUI to reflect changes game state changes from battle
     *
     *
     * @author Braden Norton
     */

    void setAttDice(String[] s)
    {
        for(int i=0; i<s.length; ++i)
        {
            attDice.addItem(s[i]);
        }
    }

    void setDefDice(String[] s)
    {
        for(int i=0; i<s.length; ++i)
        {
            defDice.addItem(s[i]);
        }
    }

    void setAttackingCountry(String s)
    {
        att.setText(s);
    }

    void setDefendingCountry(String s)
    {
        def.setText(s);
    }

    void setTroopsInAttCountry(int t)
    {
        attTroops.setText("" + t);
    }

    void setTroopsInDefCountry(int t)
    {
        defTroops.setText("" + t);
    }

    void setAttDiceAmount(int t)
    {
        attDiceAmount.setText(""+t);
    }

    void setDefDiceAmount(int t)
    {
        defDiceAmount.setText(""+t);
    }

    void setRollResult(String s)
    {
        results.append(s);
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

    void setFortifyFromList(ArrayList<String> a)
    {
        for(int i=0; i<a.size();++i)
        {
            ocModel.addElement(a.get(i));
        }
    }

    void setAmountToFortify(ArrayList<String> s)
    {
        for(int i=0; i<s.size();++i)
        {
            movableTroops.addItem(s.get(i));
        }
    }


}
