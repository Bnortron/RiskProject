import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 * View class that represents the visualization of the data that RiskGame model contains
 *
 * @author Braden Norton
 * @version 11/17/20
 */
public class GameView extends JFrame
{
    // MenuGUI Options
    private JButton start, load, quit;

    // MenuGUI Panel
    private JPanel mPanel;

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

    /**
     * JPanel that displays "Start" and "Quit" options
     *
     * @return
     */
    private JPanel menuGUI()
    {
        // Main Panel
        mPanel = new JPanel();
        mPanel.setLayout(new GridLayout(4,1));

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

        load = new JButton("Load");
        load.setActionCommand("Load");

        quit = new JButton("Quit");
        quit.setActionCommand("Quit");

        // Add components to panel
        mPanel.add(lP); // add welcome messages
        mPanel.add(start);
        mPanel.add(load);
        mPanel.add(quit);

        // Return panel
        return mPanel;
    }

    void quitGame()
    {
        // Exit Popup
        JOptionPane.showMessageDialog(this, "Thank you for playing!");

        // Stop program once exit popup closed
        System.exit(0);
    }

    void loadFailed()
    {
        // Fail popup
        JFrame f = new JFrame();
        JOptionPane.showMessageDialog(f, "No file selected");
    }

    String loadGame()
    {
        JFrame loadFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to Open");

        int userSelection = fileChooser.showOpenDialog(loadFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            //System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            return ("" +fileToSave.getAbsolutePath());
        }
        else
        {
            return "No path selected";
        }
    }

    public void menuActionListener(ActionListener o)
    {
        start.addActionListener(o);
        load.addActionListener(o);
        quit.addActionListener(o);
    }
}

class PlayerAmountGUI extends JFrame
{
    // PlayerAmountGUI options
    private JButton p2,p3,p4,p5,p6;
    public PlayerAmountGUI()
    {
        setTitle("Risk GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 300));
        setLocationRelativeTo(null);
        setResizable(false);
        add(playerAmount());
        pack();
        setVisible(true);
        toFront();
    }

    private JPanel playerAmount()
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
        p2.setActionCommand("2");

        p3 = new JButton("Three Players");
        p3.setActionCommand("3");

        p4 = new JButton("Four Players");
        p4.setActionCommand("4");

        p5 = new JButton("Five Players");
        p5.setActionCommand("5");

        p6 = new JButton("Six Players");
        p6.setActionCommand("6");

        // Add to panel
        // Add components to panel
        pPanel.add(players);
        pPanel.add(p2);
        pPanel.add(p3);
        pPanel.add(p4);
        pPanel.add(p5);
        pPanel.add(p6);

        // Return panel
        return pPanel;
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
}

class PlayerNameGUI extends JFrame
{
    // Player amount
    private int players;

    // PlayerNameGUI options
    private JButton p2,p3,p4,p5,p6;

    // AI Selection Buttons
    private JCheckBox ai1, ai2, ai3, ai4, ai5, ai6;

    // Submit player name
    private JButton submit,menuReturn;

    // Player name input
    private JTextField name1,name2,name3,name4,name5,name6;

    public PlayerNameGUI(int players)
    {
        // set player amount
        this.players = players;

        // Frame set up
        setTitle("Risk GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Add JPanel for player amount
        initializeSettings();
        if(players == 2){ setPreferredSize(new Dimension(300,150)); add(twoPlayersGUI()); }
        else if(players == 3){ setPreferredSize(new Dimension(300,200)); add(threePlayersGUI()); }
        else if(players == 4){ setPreferredSize(new Dimension(300,250)); add(fourPlayersGUI()); }
        else if(players == 5){ setPreferredSize(new Dimension(300,300)); add(fivePlayersGUI()); }
        else{ setPreferredSize(new Dimension(300,300)); add(sixPlayersGUI()); }

        // Finish frame setup
        pack();
        setVisible(true);
        toFront();
    }

    /**
     * Initialize all the buttons, textboxes, and checkboxes
     *
     * @author Braden Norton
     */
    void initializeSettings()
    {
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
        p2.setActionCommand("2");

        p3 = new JButton("Three Players");
        p3.setActionCommand("3");

        p4 = new JButton("Four Players");
        p4.setActionCommand("4");

        p5 = new JButton("Five Players");
        p5.setActionCommand("5");

        p6 = new JButton("Six Players");
        p6.setActionCommand("6");

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
        submit = new JButton("Submit");
        submit.setActionCommand("Submit");

        menuReturn = new JButton("Back");
        menuReturn.setActionCommand("Back");
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

    ArrayList<String> getNames()
    {
        // Create list
        ArrayList<String> names = new ArrayList<>();

        // Get names
        names.add(name1.getText());
        names.add(name2.getText());
        if(players >2)
        {
            names.add(name3.getText());
            if(players > 3)
            {
                names.add(name4.getText());
                if(players > 4)
                {
                    names.add(name5.getText());
                    if(players > 5)
                    {
                        names.add(name6.getText());
                    }
                }
            }
        }
        System.out.println(names.toString());

        // Return list
        return names;
    }

    ArrayList<Boolean> getAI()
    {
        // Create list
        ArrayList<Boolean> ai = new ArrayList<>();

        // Get ai players
        if(ai1.isSelected()){ai.add(true);}
        else{ ai.add(false); }

        if(ai2.isSelected()){ ai.add(true); }
        else{ ai.add(false); }

        if(players > 2)
        {
            if(ai3.isSelected()){ ai.add(true); }
            else{ ai.add(false); }

            if(players > 3)
            {
                if(ai4.isSelected()){ ai.add(true); }
                else{ ai.add(false); }

                if(players > 4)
                {
                    if(ai5.isSelected()){ ai.add(true); }
                    else{ ai.add(false); }

                    if(players > 5)
                    {
                        if(ai6.isSelected()){ ai.add(true); }
                        else{ ai.add(false); }
                    }
                }
            }
        }

        System.out.println(ai.toString());

        // Return list
        return ai;
    }
}

class BoardGUI extends JFrame
{
    // BoardGUI options
    private JButton reinforce,attack,fortify, aiTurn,endTurn;

    private JTextField bonusTroops,troops, ownedCountries, ownedContinents;

    private JLabel l1,l2,l3,l4,l5,l6;

    private JPanel mPanel,bPanel, topPanel, msPanel, statPanel, turnHistoryPanel;

    private JTextArea turn, turnHistory;

    private JList playerList, oc1,oc2,oc3,oc4,oc5,oc6,ocCurrent,aCountries;

    private JMenuItem saveGame, quitGame;

    // Model Data to represent
    private String cpName;
    private ArrayList<Player> allPlayers;
    private DefaultListModel p1OC, p2OC, p3OC,p4OC,p5OC,p6OC;

    private Player currentPlayer;

    private int cpTroops;
    private int cpCountryAmount;
    private int cpContinentAmount;
    private int cpBonusReinforcements;
    private int turnNumber;

    public BoardGUI(ArrayList<Player> players, Player p)
    {
        // Set Player List
        this.currentPlayer = p;
        setCurrentPlayer(currentPlayer);
        this.allPlayers = players;

        // Setup Frame
        setTitle("Risk GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000,600));
        setLocationRelativeTo(null);
        setResizable(false);

        // Add Board stat panels
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(4,1));
        boardPanel.add(boardGUIPlayerStats());
        boardPanel.add(boardGUIButtons());
        boardPanel.add(boardGUI(cpName));
        boardPanel.add(mapStateGUI());
        add(boardPanel);
        addMenu();

        // Finish frame setup
        pack();
        setVisible(true);
        toFront();
    }

    void addMenu()
    {
        // Create Menu
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        saveGame = new JMenuItem("Save Game");
        saveGame.setActionCommand("Save Game");

        quitGame = new JMenuItem("Quit Game");
        quitGame.setActionCommand("Quit Game");

        // Add components
        menu.add(saveGame);
        menu.add(quitGame);
        menubar.add(menu);

        // Add menu to frame
        setJMenuBar(menubar);
    }

    private JPanel boardGUIButtons()
    {
        // Create Panel
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1,4));


        // JButtons
        // Reinforce
        reinforce = new JButton("Reinforce");
        reinforce.setActionCommand("Reinforce");
        if(currentPlayer.isAI()) reinforce.setEnabled(false);

        // Attack
        attack = new JButton("Attack");
        attack.setActionCommand("Attack");
        attack.setEnabled(false);

        // Fortify
        fortify = new JButton("Fortify");
        fortify.setActionCommand("Fortify");
        fortify.setEnabled(false);

        //AI Turn
        aiTurn = new JButton("Do AI Turn");
        aiTurn.setActionCommand("AI Turn");
        if(currentPlayer.isAI()){
            aiTurn.setEnabled(true);
        }else{
            aiTurn.setEnabled(false);
        }

        // End Turn
        endTurn = new JButton("End Turn");
        endTurn.setActionCommand("End Turn");
        endTurn.setEnabled(false);

        // Add components to panel
        topPanel.add(reinforce);
        topPanel.add(attack);
        topPanel.add(fortify);
        topPanel.add(endTurn);

        // Return Panel
        return topPanel;
    }

    private JPanel boardGUIPlayerStats()
    {
        // Create Panel
        statPanel = new JPanel();
        statPanel.setLayout(new GridLayout(1,4));

        // Display amount of owned troops
        troops = new JTextField("" + cpTroops);
        troops.setEditable(false);
        troops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(cpName + "'s Troops"),BorderFactory.createEmptyBorder(5,5,5,5)));
        troops.setHorizontalAlignment(JTextField.CENTER);

        // Display amount of owned countries
        ownedCountries = new JTextField("" + cpCountryAmount);
        ownedCountries.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(cpName+ "'s Countries"),BorderFactory.createEmptyBorder(5,5,5,5)));
        ownedCountries.setHorizontalAlignment(JTextField.CENTER);
        ownedCountries.setEditable(false);

        // Display amount of owned continents
        ownedContinents = new JTextField("" + cpContinentAmount);
        ownedContinents.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(cpName + "'s Continents"),BorderFactory.createEmptyBorder(5,5,5,5)));
        ownedContinents.setHorizontalAlignment(JTextField.CENTER);
        ownedContinents.setEditable(false);

        // Display amount of bonus troops from owned continents & countries
        bonusTroops = new JTextField("" + cpBonusReinforcements);
        bonusTroops.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(cpName + "'s Bonus Troops"),BorderFactory.createEmptyBorder(5,5,5,5)));
        bonusTroops.setHorizontalAlignment(JTextField.CENTER);
        bonusTroops.setEditable(false);

        // Add to panel
        statPanel.add(troops);
        statPanel.add(ownedCountries);
        statPanel.add(ownedContinents);
        statPanel.add(bonusTroops);

        // Return panel
        return statPanel;
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

    private JPanel turnHistory()
    {
        // Create Panel
        turnHistoryPanel = new JPanel();

        turnNumber = 1;
        turnHistory = new JTextArea("---------------\n");
        turnHistory.setEditable(false);
        turnHistory.append("Turn: " + turnNumber + "\n");
        JScrollPane jp = new JScrollPane(turn);
        turn.append("---------------\n");

        // Return panel
        return turnHistoryPanel;
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

        if(allPlayers.size() >= 2)
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
            for(int i=0; i<allPlayers.get(0).capturedCountriesToString().size(); ++i)
            {
                p1OC.addElement(allPlayers.get(0).capturedCountriesToString().get(i));
            }
            oc1 = new JList<>(p1OC);
            JScrollPane sp1 = new JScrollPane(oc1);

            p2OC = new DefaultListModel();
            for(int i=0; i<allPlayers.get(1).capturedCountriesToString().size(); ++i)
            {
                p2OC.addElement(allPlayers.get(1).capturedCountriesToString().get(i));
            }
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
                for(int i=0; i<allPlayers.get(2).capturedCountriesToString().size(); ++i)
                {
                    p3OC.addElement(allPlayers.get(2).capturedCountriesToString().get(i));
                }
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
                    for(int i=0; i<allPlayers.get(3).capturedCountriesToString().size(); ++i)
                    {
                        p4OC.addElement(allPlayers.get(3).capturedCountriesToString().get(i));
                    }
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
                        for(int i=0; i<allPlayers.get(4).capturedCountriesToString().size(); ++i)
                        {
                            p5OC.addElement(allPlayers.get(4).capturedCountriesToString().get(i));
                        }
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
                            for(int i=0; i<allPlayers.get(5).capturedCountriesToString().size(); ++i)
                            {
                                p6OC.addElement(allPlayers.get(5).capturedCountriesToString().get(i));
                            }
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
        //initializeLists();

        return msPanel;
    }

    public void boardActionListener(ActionListener e, ListSelectionListener o)
    {
        // ActionListeners
        reinforce.addActionListener(e);
        attack.addActionListener(e);
        fortify.addActionListener(e);
        aiTurn.addActionListener(e);
        endTurn.addActionListener(e);
        saveGame.addActionListener(e);
        quitGame.addActionListener(e);

        // ListSelectionListeners
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

    void setCurrentPlayer(Player p)
    {
        this.currentPlayer = p;
        this.cpName = p.getName();
        this.cpTroops = p.getTroops();
        this.cpBonusReinforcements = p.getContinentBonus() + p.getCountryBonus();
        this.cpCountryAmount = p.getCapturedCountries().size();
        this.cpContinentAmount = p.getCapturedContinents().size();
    }

    void initializeLists()
    {
        updateP1OCList(allPlayers.get(0).capturedCountriesToString());
        updateP2OCList(allPlayers.get(1).capturedCountriesToString());
        if(allPlayers.size() > 2)
        {
            updateP3OCList(allPlayers.get(2).capturedCountriesToString());
            if(allPlayers.size() > 3)
            {
                updateP4OCList(allPlayers.get(3).capturedCountriesToString());
                if(allPlayers.size() > 4)
                {
                    updateP5OCList(allPlayers.get(4).capturedCountriesToString());
                    if(allPlayers.size() > 5)
                    {
                        updateP6OCList(allPlayers.get(5).capturedCountriesToString());
                    }
                }
            }
        }
    }

    void updateP1OCList(ArrayList<String> a)
    {
        oc1.clearSelection();
        p1OC.clear();
        for(int i =0; i<a.size(); ++i)
        {
            p1OC.addElement(a.get(i));
        }
    }

    void updateP2OCList(ArrayList<String> a)
    {
        oc2.clearSelection();
        p2OC.clear();
        for(int i =0; i<a.size(); ++i)
        {
            p2OC.addElement(a.get(i));
        }
    }

    void updateP3OCList(ArrayList<String> a)
    {
        oc3.clearSelection();
        p3OC.clear();
        for(int i =0; i<a.size(); ++i)
        {
            p3OC.addElement(a.get(i));
        }
    }

    void updateP4OCList(ArrayList<String> a)
    {
        oc4.clearSelection();
        p4OC.clear();
        for(int i =0; i<a.size(); ++i)
        {
            p4OC.addElement(a.get(i));
        }
    }

    void updateP5OCList(ArrayList<String> a)
    {
        oc5.clearSelection();
        p5OC.clear();
        for(int i =0; i<a.size(); ++i)
        {
            p5OC.addElement(a.get(i));
        }
    }

    void updateP6OCList(ArrayList<String> a)
    {
        oc6.clearSelection();
        p6OC.clear();
        for(int i =0; i<a.size(); ++i)
        {
            p6OC.addElement(a.get(i));
        }
    }

    JList l1() { return oc1; }

    JList l2() { return oc2; }

    JList l3() { return oc3; }

    JList l4() { return oc4; }

    JList l5() { return oc5; }

    JList l6() { return oc6; }

    String getL1(){ return oc1.getSelectedValue().toString();}

    String getL2(){return oc2.getSelectedValue().toString();}

    String getL3() {return oc3.getSelectedValue().toString();}

    String getL4() {return oc4.getSelectedValue().toString();}

    String getL5(){return oc5.getSelectedValue().toString();}

    String getL6(){return oc6.getSelectedValue().toString();}

    void getTroopsInCountry(Player p, String s)
    {
        int n = p.getTurnPosition();
        for(Country c: p.getCapturedCountries())
        {
            if(c.getName().equals(s))
            {
                if(n==0){l1.setText("Troops: " + c.getTroops());}
                if(n==1){l2.setText("Troops: " + c.getTroops());}
                if(n==2){l3.setText("Troops: " + c.getTroops());}
                if(n==3){l4.setText("Troops: " + c.getTroops());}
                if(n==4){l5.setText("Troops: " + c.getTroops());}
                if(n==5){l6.setText("Troops: " + c.getTroops());}
            }
        }
    }

    void reinforcementPhaseComplete()
    {
        reinforce.setEnabled(false);
        attack.setEnabled(true);
        fortify.setEnabled(true);
        endTurn.setEnabled(true);
    }

    void fortifyPhaseComplete()
    {
        reinforce.setEnabled(false);
        attack.setEnabled(false);
        fortify.setEnabled(false);
        endTurn.setEnabled(true);
    }

    void updateTurnArea(String s)
    {
        turn.append(s+"\n");
        turn.selectAll();
    }

    void updateTurnHistory(String s)
    {
        turnNumber++;
        turnHistory.append("---------------\n");
        turnHistory.append("Turn: " + turnNumber + "\n");
        turn.append("---------------\n");
    }

    void nextTurn(Player p)
    {
        setCurrentPlayer(p);
        getContentPane().removeAll();
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(4,1));
        boardPanel.add(boardGUIPlayerStats());
        boardPanel.add(boardGUIButtons());
        boardPanel.add(boardGUI(cpName));
        boardPanel.add(mapStateGUI());
        add(boardPanel);
        revalidate();
    }

    void updateStats(ArrayList<Player> players)
    {
        this.allPlayers = players;
        troops.setText(""+currentPlayer.getTroops());
        ownedCountries.setText(""+currentPlayer.getCapturedCountries().size());
        ownedContinents.setText(""+currentPlayer.getCapturedContinents().size());
    }

    void updateCountryLists(Player att, Player def, String c)
    {
        if(att.getTurnPosition() == 0) { p1OC.addElement(c);}
        else if(att.getTurnPosition()==1) { p2OC.addElement(c);}
        else if(att.getTurnPosition()==2){ p3OC.addElement(c); }
        else if(att.getTurnPosition()==3){ p4OC.addElement(c); }
        else if(att.getTurnPosition()==4){ p5OC.addElement(c); }
        else if(att.getTurnPosition()==5){ p6OC.addElement(c); }

        if(def.getTurnPosition() == 0){ p1OC.removeElement(c); }
        else if(def.getTurnPosition()==1){ p2OC.removeElement(c); }
        else if(def.getTurnPosition()==2){ p3OC.removeElement(c); }
        else if(def.getTurnPosition()==3){ p4OC.removeElement(c); }
        else if(def.getTurnPosition()==4){ p5OC.removeElement(c); }
        else if(def.getTurnPosition()==5){ p6OC.removeElement(c); }
    }

    String saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            //System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            return ("" +fileToSave.getAbsolutePath());
        }
        else
        {
            return "No path selected";
        }
    }

    void quitGame()
    {
        // Exit Popup
        JOptionPane.showMessageDialog(this, "Thank you for playing!");

        // Stop program once exit popup closed
        System.exit(0);
    }
}

class ReinforceGUI extends JFrame
{
    // ReinforceGUI options
    private JButton submit, back;

    private DefaultListModel ocModel;

    private ArrayList<String> troopAmount;

    private Player cp;

    private JList ocList;

    private JScrollPane sp;

    private JComboBox movableTroops;

    public ReinforceGUI(Player cp, ArrayList<String> validMovement)
    {
        // Set list
        this.troopAmount = validMovement;
        this.cp = cp;

        // Setup Frame
        setTitle("Risk GUI");
        setLocationRelativeTo(null);
        setResizable(false);
        setPreferredSize(new Dimension(300,300));
        add(reinforcePanel());

        // Finish frame setup
        pack();
        setVisible(true);
        toFront();
    }

    private JPanel reinforcePanel()
    {
        // Create panel
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3,1));

        // Set up owned countries list
        ocModel = new DefaultListModel();

        for(int i=0; i<cp.capturedCountriesToString().size(); ++i)
        {
            ocModel.addElement(cp.capturedCountriesToString().get(i));
        }

        ocList = new JList<String>(ocModel);
        sp = new JScrollPane(ocList);
        sp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Owned"),BorderFactory.createEmptyBorder(1,1,1,1)));

        // Set up troop amount ComboBox
        JPanel cb = new JPanel();
        cb.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Move"),BorderFactory.createEmptyBorder(1,1,1,1)));
        String[] array = troopAmount.toArray(new String[troopAmount.size()]);
        movableTroops = new JComboBox(array);
        movableTroops.setActionCommand("Movable Troops");
        cb.add(movableTroops);

        // Set up buttons
        JPanel b = new JPanel();
        b.setLayout(new GridLayout(1,2));

        submit = new JButton("Submit");
        submit.setActionCommand("Submit");

        back = new JButton("Back");
        back.setActionCommand("Back");

        b.add(submit);
        b.add(back);

        // Add options to panel
        p.add(sp);
        p.add(cb);
        p.add(b);

        // Return panel
        return p;
    }

    void reinforceActionListener(ActionListener o)
    {
        movableTroops.addActionListener(o);
        submit.addActionListener(o);
        back.addActionListener(o);
    }

    String getCountry()
    {
        return ocList.getSelectedValue().toString();
    }

    int getReinforceAmount()
    {
        String value = (String)movableTroops.getSelectedItem();
        return Integer.parseInt(value);
    }
}

class AttackGUI extends JFrame
{
    // Current Player
    private Player cp;

    // AttackGUI options
    private JButton battle, roll, back, exitBattle, selectDice;

    private JList ocCurrent, aCountries;

    private JScrollPane jp1, jp2;

    private JTextField ocTroops, acTroops, att, def, attTroops, defTroops,attDiceAmount,defDiceAmount, ap, dp;

    private DefaultListModel currentPlayerOC,dList,allowedAttDice, allowedDefDice;

    private JTextArea results;

    private JPanel cbp1, cbp2;

    private JComboBox attDice, defDice;

    // Battle Stats
    private String attackingCountry, defendingCountry, attackingCountryTroops, defendingCountryTroops, attPlayer, defPlayer;

    public AttackGUI(Player cp)
    {
        // Set current player
        this.cp = cp;

        // Setup Frame
        setTitle("Risk GUI");
        setLocationRelativeTo(null);
        setResizable(false);
        setPreferredSize(new Dimension(500, 400));
        initializeOptions();
        add(attackPanel());

        // Finish frame setup
        pack();
        setVisible(true);
        toFront();
    }

    void initializeOptions()
    {
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
        for(int i=0; i<cp.capturedCountriesToString().size(); ++i)
        {
            currentPlayerOC.addElement(cp.capturedCountriesToString().get(i));
        }
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

        selectDice = new JButton("Select Dice Amount");
        selectDice.setActionCommand("Select Dice");

        roll = new JButton("Roll");
        roll.setActionCommand("Roll");

        exitBattle = new JButton("Exit Battle");
        exitBattle.setActionCommand("Exit Battle");

        // JComboBox
        cbp1 = new JPanel();
        cbp1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Attack Dice"),BorderFactory.createEmptyBorder(1,1,1,1)));
        attDice = new JComboBox();
        attDice.setActionCommand("Attack Dice");
        cbp1.add(attDice);

        cbp2 = new JPanel();
        cbp2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Defence Dice"),BorderFactory.createEmptyBorder(1,1,1,1)));
        defDice = new JComboBox();
        defDice.setActionCommand("Defence Dice");
        cbp2.add(defDice);

        // JTextFields
        // Attacking Country
        att = new JTextField();
        att.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Country"),BorderFactory.createEmptyBorder(1,1,1,1)));
        att.setHorizontalAlignment(JTextField.CENTER);
        att.setEditable(false);

        // Defending Country
        def = new JTextField();
        def.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Country"),BorderFactory.createEmptyBorder(1,1,1,1)));
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

        // Attacking Player Name
        ap = new JTextField();
        ap.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Attacker"),BorderFactory.createEmptyBorder(1,1,1,1)));
        ap.setHorizontalAlignment(JTextField.CENTER);
        ap.setEditable(false);

        // Defending Player Name
        dp = new JTextField();
        dp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Defender"),BorderFactory.createEmptyBorder(1,1,1,1)));
        dp.setHorizontalAlignment(JTextField.CENTER);
        dp.setEditable(false);


        // Results of roll
        results = new JTextArea();
        results.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Results"),BorderFactory.createEmptyBorder(1,1,1,1)));
        results.setEditable(false);
    }

    private JPanel attackPanel()
    {
        // Create Panel
        JPanel attackPanel = new JPanel();
        attackPanel.setLayout(new GridLayout(2,2));

        // Create Panel
        JPanel aPanel = new JPanel();
        aPanel.setLayout(new BoxLayout(aPanel, BoxLayout.Y_AXIS));
        aPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Own:"),BorderFactory.createEmptyBorder(5,5,5,5)));

        JPanel bPanel = new JPanel();
        bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.Y_AXIS));
        bPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Can Attack:"),BorderFactory.createEmptyBorder(5,5,5,5)));

        JPanel cPanel = new JPanel();
        cPanel.setLayout(new GridLayout(2,1));

        JPanel dPanel = new JPanel();
        dPanel.setLayout(new GridLayout(2,1));

        // Add components
        aPanel.add(jp1);
        cPanel.add(ocTroops);
        cPanel.add(battle);

        bPanel.add(jp2);
        dPanel.add(acTroops);
        dPanel.add(back);

        attackPanel.add(aPanel);
        attackPanel.add(bPanel);
        attackPanel.add(cPanel);
        attackPanel.add(dPanel);

        // Return Panel
        return attackPanel;
    }

    JPanel chooseDicePanel()
    {
        // Set att/def selected countries
        this.attackingCountry = ocCurrent.getSelectedValue().toString();
        this.defendingCountry = aCountries.getSelectedValue().toString();

        // Set att/def troops
        this.attackingCountryTroops = ocTroops.getText();
        this.defendingCountryTroops = acTroops.getText();

        // Create Panels
        JPanel cdPanel = new JPanel();
        cdPanel.setLayout(new GridLayout(3,1));

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

        JPanel players = new JPanel();
        players.setLayout(new GridLayout(1,2));

        // Add to panel
        players.add(ap);
        players.add(dp);

        p.add(cbp1);
        p.add(cbp2);

        cdPanel.add(players);
        cdPanel.add(p);
        cdPanel.add(selectDice);

        // Return Panel
        return cdPanel;
    }

    JPanel battlePanel()
    {
        // Set att/def stats
        att.setText(attackingCountry);
        def.setText(defendingCountry);

        attTroops.setText(attackingCountryTroops);
        defTroops.setText(defendingCountryTroops);

        defDiceAmount.setText(getDDiceAmount());
        attDiceAmount.setText(getADiceAmount());

        // Create Panels
        JPanel battleP = new JPanel();
        battleP.setLayout(new BorderLayout());

        JPanel attPanel = new JPanel();
        attPanel.setPreferredSize(new Dimension(100, 200));
        attPanel.setLayout(new GridLayout(4,1));

        JPanel midPanel = new JPanel();
        midPanel.setLayout(new GridLayout(2,1));

        JPanel defPanel = new JPanel();
        defPanel.setPreferredSize(new Dimension(100, 200));
        defPanel.setLayout(new GridLayout(4,1));

        JPanel bPanel = new JPanel();
        bPanel.setLayout(new GridLayout(1,2));

        // Add contents
        bPanel.add(roll);
        bPanel.add(exitBattle);

        // Add components
        attPanel.add(ap);
        attPanel.add(att);
        attPanel.add(attTroops);
        attPanel.add(attDiceAmount);

        midPanel.add(results);
        midPanel.add(bPanel);

        defPanel.add(dp);
        defPanel.add(def);
        defPanel.add(defTroops);
        defPanel.add(defDiceAmount);

        battleP.add(attPanel, BorderLayout.WEST);
        battleP.add(midPanel, BorderLayout.CENTER);
        battleP.add(defPanel, BorderLayout.EAST);

        // Return Panel
        return battleP;
    }

    void chooseDiceStage(String aPlayer, String dPlayer)
    {
        // Set Attacking & Defending player
        ap.setText(aPlayer);
        dp.setText(dPlayer);

        // Set DiceGUI
        getContentPane().removeAll();
        add(chooseDicePanel());
        revalidate();
    }

    void battleStage()
    {
        getContentPane().removeAll();
        add(battlePanel());
        revalidate();
    }

    public void attackActionListener(ActionListener o, ListSelectionListener e)
    {
        battle.addActionListener(o);
        back.addActionListener(o);
        roll.addActionListener(o);
        exitBattle.addActionListener(o);
        selectDice.addActionListener(o);

        aCountries.addListSelectionListener(e);
        ocCurrent.addListSelectionListener(e);
    }

    public void removeACListener(ListSelectionListener o) { aCountries.removeListSelectionListener(o); }

    public void acCountryActionListener(ListSelectionListener o){aCountries.addListSelectionListener(o);}

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

    void updateACList(String[] l)
    {
        dList.clear();
        for(int i=0; i<l.length; ++i)
        {
            dList.addElement(l[i]);
        }
    }

    void updateOCTroops(int s)
    {
        ocTroops.setText(""+s);
    }

    void updateACTroops(int t)
    {
        acTroops.setText("" + t);
    }

    void setAttDice(String[] s)
    {
        for(int i=0; i<s.length; ++i)
        {
            attDice.addItem(s[i]);
        }
    }

    void setDefDice(String[] s)
    {
        System.out.println(s.length);
        for(int i=0; i<s.length; ++i)
        {
            defDice.addItem(s[i]);
        }
    }

    void setAIDefDice(String[] s)
    {
        int temp = 0;
        for(int i=0; i<s.length; ++i)
        {
            ++temp;
        }
        System.out.println(temp);
    }

    String getADiceAmount() { return attDice.getSelectedItem().toString(); }

    String getDDiceAmount() { return defDice.getSelectedItem().toString(); }

    void setRollResult(String s)
    {
        results.append(s);
    }

    void setBattleResults(int a, int d)
    {
        attTroops.setText("" + a);
        defTroops.setText("" + d);
    }

    void disableRoll()
    {
        roll.setEnabled(false);
    }
}

class FortifyGUI extends JFrame
{
    // Current player
    Player cp;

    // FortifyGUI options
    private JButton moveTroops, submitFortify,cancelFortify;
    private JList owned,adj;
    private DefaultListModel ocModel, adjModel;
    private JComboBox movableTroops;
    private JScrollPane ocSP, adjSP;

    public FortifyGUI(Player p)
    {
        // Set current player
        this.cp = p;

        // Setup Frame
        setTitle("Risk GUI");
        setLocationRelativeTo(null);
        setResizable(false);
        setPreferredSize(new Dimension(500, 400));
        initializeOptions();
        add(countrySelectPanel());

        // Finish frame setup
        pack();
        setVisible(true);
        toFront();
    }

    void initializeOptions()
    {
        // Lists
        ocModel = new DefaultListModel();
        for(int i=0; i<cp.capturedCountriesToString().size(); ++i)
        {
            ocModel.addElement(cp.capturedCountriesToString().get(i));
        }
        owned = new JList<String>(ocModel);
        ocSP = new JScrollPane(owned);
        ocSP.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Owned"),BorderFactory.createEmptyBorder(1,1,1,1)));

        adjModel = new DefaultListModel();
        adj = new JList<String>(adjModel);
        adjSP = new JScrollPane(adj);
        adjSP.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Fortifiable"),BorderFactory.createEmptyBorder(1,1,1,1)));

        // Button
        moveTroops = new JButton("Move Troops");
        moveTroops.setActionCommand("Move Troops");

        cancelFortify = new JButton("Cancel");
        cancelFortify.setActionCommand("Cancel Fortify");

        submitFortify = new JButton("Submit");
        submitFortify.setActionCommand("Submit Fortify");

        // ComboBox
        JPanel cb = new JPanel();
        cb.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Move"),BorderFactory.createEmptyBorder(1,1,1,1)));
        movableTroops = new JComboBox();
        movableTroops.setActionCommand("Movable Troops");
        cb.add(movableTroops);
    }

    private JPanel countrySelectPanel()
    {
        // Create Panels
        JPanel fPanel = new JPanel();
        fPanel.setLayout(new GridLayout(2,1));

        JPanel countriesListPanel = new JPanel();
        countriesListPanel.setLayout(new GridLayout(1,2));

        JPanel b = new JPanel();
        b.setLayout(new GridLayout(1,2));

        // Add contents
        b.add(moveTroops);
        b.add(cancelFortify);

        countriesListPanel.add(ocSP);
        countriesListPanel.add(adjSP);

        fPanel.add(countriesListPanel);
        fPanel.add(b);

        // Return panel
        return fPanel;
    }

    private JPanel fortifyAmountPanel()
    {
        setSize(new Dimension(300, 200));
        // Create Panels
        JPanel fPanel = new JPanel();
        fPanel.setLayout(new GridLayout(2,1));

        JPanel cb = new JPanel();
        cb.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Move"),BorderFactory.createEmptyBorder(1,1,1,1)));

        // Add contents
        cb.add(movableTroops);

        fPanel.add(cb);
        fPanel.add(submitFortify);

        // Return Panel
        return fPanel;
    }

    void fortifyAmountStage()
    {
        getContentPane().removeAll();
        add(fortifyAmountPanel());
        revalidate();
    }

    public void fortifyActionListener(ActionListener o, ListSelectionListener e)
    {
        movableTroops.addActionListener(o);
        submitFortify.addActionListener(o);
        moveTroops.addActionListener(o);
        cancelFortify.addActionListener(o);
        owned.addListSelectionListener(e);
        adj.addListSelectionListener(e);
    }

    JList getOwnedList() { return owned; }

    String getOwnedListValue() { return owned.getSelectedValue().toString(); }

    String getAdjListValue() { return adj.getSelectedValue().toString(); }

    void setAdjList(ArrayList<String> a)
    {
        adjModel.clear();
        for(int i=0; i<a.size(); ++i)
        {
            adjModel.addElement(a.get(i));
        }
    }

    void setAmountToFortify(ArrayList<String> s)
    {
        for(int i=0; i<s.size();++i)
        {
            movableTroops.addItem(s.get(i));
        }
    }

    int getFortifyTroopAmount() { return movableTroops.getSelectedIndex(); }
}

/**
 * Allows For Selection Of User Defend Dice Against AI
 * 
 * @author Tyler Leung
 * @author Braxton Martin
 * 
 */
class aiGUI extends JFrame{
    private JPanel p;
    private JComboBox cb1;
    private JButton submit;
    public aiGUI(){
        setTitle("Risk GUI");
        setLocationRelativeTo(null);
        setResizable(false);
        setPreferredSize(new Dimension(500, 400));
        add(userDefDice());

        pack();
        setVisible(true);
        toFront();
    }
    public void aiActionListener(ActionListener o){
        cb1.addActionListener(o);
    }
    public JPanel userDefDice(){
        p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Select Defender Dice - "),BorderFactory.createEmptyBorder(1,1,1,1)));
        cb1 = new JComboBox();
        submit = new JButton("Submit");
        submit.setActionCommand("Submit");
        p.add(cb1);
        p.add(submit);
        return p;
    }
    
    public void setDefDice(String[] s)
    {
        System.out.println(s.length);
        for(int i=0; i<s.length; ++i)
        {
            cb1.addItem(s[i]);
        }
    }

    public String getDefDice(){
        return cb1.getSelectedItem().toString();
    }
}
