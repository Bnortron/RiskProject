import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * View class that represents the visualization of the data that RiskGame model contains
 *
 * Contains 8 nested classes that each represent a different frame for user input:
 *
 * LoadGUI: Options for loading saved game or custom game
 * PlayerAmountGUI: Options for the amount of players are playing a new game (2-6)
 * PlayerNameGUI: Options for the names of each player & whether they're AI or Human
 * BoardGUI: Options for Reinforce/Attack/Fortify phases, End Turn, Map state lists, AI Turn, and it displays the current player stats (Name, total troops, owned countries, owned continents, bonus troops)
 * ReinforceGUI: Options for the Reinforce phase (Move certain amount of reinforcements to selected country)
 * AttackGUI: Options for the Attack phase (which country to attack/how many dice to use), displays the battle results
 * FortifyGUI: Options for Fortify phase (which adj country to reinforce from a certain country, and how many troops to send)
 * aiGUI: Options for the AI turn
 *
 * @author Braden Norton
 * @version 11/17/20
 */
public class GameView extends JFrame
{
    // MenuGUI Options
    private JButton start, load, quit, customGame, savedGame;

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
        initializeOptions();
        add(menuGUI());
        pack();
        setVisible(true);
        toFront();
    }

    /**
     * Initialize the buttons & set their action command words
     *
     * @author Braden Norton
     */
    void initializeOptions()
    {
        // Buttons
        start = new JButton("Start");
        start.setActionCommand("Start");

        load = new JButton("Load");
        load.setActionCommand("Load");

        quit = new JButton("Quit");
        quit.setActionCommand("Quit");
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

        // Add components to panel
        mPanel.add(lP); // add welcome messages
        mPanel.add(start);
        mPanel.add(load);
        mPanel.add(quit);

        // Return panel
        return mPanel;
    }

    /**
     * Displays a JOptionPane that confirms the user has quit the game
     *
     * @author Braden Norton
     */
    void quitGame()
    {
        // Exit Popup
        JOptionPane.showMessageDialog(this, "Thank you for playing!");

        // Stop program once exit popup closed
        System.exit(0);
    }

    /**
     * Adds action listeners to the buttons
     *
     * @param o ActionListener object
     */
    public void menuActionListener(ActionListener o)
    {
        start.addActionListener(o);
        load.addActionListener(o);
        quit.addActionListener(o);
    }
}

//====================================================================================================//

class LoadGUI extends JFrame {
    // LoadGUI options
    private JButton customGame, savedGame, back;

    /**
     * Constructor
     *
     * @Braden Norton
     */
    public LoadGUI() {
        setTitle("Risk GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 300));
        setLocationRelativeTo(null);
        setResizable(false);
        add(loadTypes());
        pack();
        setVisible(true);
        toFront();
    }

    /**
     * Creates the panel w/load buttons (Custom game, Saved game, Back)
     *
     * @return load option panel
     * @author Braden Norton
     */
    private JPanel loadTypes() {
        // Create panels
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4, 1));

        // Create text display
        JLabel loadText = new JLabel("Please Choose Game Type:");
        loadText.setFont(new Font("Serif", Font.BOLD, 20));
        loadText.setHorizontalAlignment(JLabel.CENTER);

        // Create buttons
        customGame = new JButton("Load Custom Game");
        customGame.setActionCommand("custom");

        savedGame = new JButton("Load Saved Game");
        savedGame.setActionCommand("saved");

        back = new JButton("Back To Main Menu");
        back.setActionCommand("back");

        // Add components
        p.add(loadText);
        p.add(savedGame);
        p.add(customGame);
        p.add(back);

        // Return panel
        return p;
    }

    /**
     *
     * @param o added ActionListener to each button
     */
    public void loadGUIActionListeners(ActionListener o) {
        savedGame.addActionListener(o);
        customGame.addActionListener(o);
        back.addActionListener(o);
    }

    /**
     * Prompts user to select a file to load through the use of a JFileChooser, based on the type of file selected from the LoadGUI options, and saves that file path to a String
     *
     * If user selected "Saved Game": JFileChooser will allow user to open files that are saved under the .ser file type (Saved games use Serialization interface)
     * If user selected "Custom Game": JFileChooser will allow user to open files that are saved as .json type (Custom maps are in JSON format)
     * If user cancels: File location String will be set to "No path selected"
     *
     * @param type Either "saved" or "custom", per LoadGUI option action commands
     * @return String that holds the location of desired file path to be opened
     *
     * @author Braden Norton
     */
    String loadGame(String type)
    {
        // Create JFileChooser
        JFrame loadFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to Open");

        // Create Extension Filer
        if(type.equals("saved"))
        {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Serialized Files", "ser");
            fileChooser.setFileFilter(filter);
        }
        else if(type.equals("custom"))
        {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON File", "json");
            fileChooser.setFileFilter(filter);
        }

        if (fileChooser.showOpenDialog(loadFrame) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            //System.out.println("Save as file: " + fileToSave.getAbsolutePath());
            return ("" +fileToSave.getAbsolutePath());
        }
        else
        {
            return "No path selected";
        }
    }

    /**
     * Prompts user with JOptionPane alert if no file was selected for loading the game
     *
     * @author Braden Norton
     */
    void loadFailed()
    {
        // Fail popup
        JFrame f = new JFrame();
        JOptionPane.showMessageDialog(f, "No file selected");
    }
}

//====================================================================================================//

/**
 * Amount of players options
 *
 * @author Braden Norton
 */
class PlayerAmountGUI extends JFrame
{
    // PlayerAmountGUI options
    private JButton p2,p3,p4,p5,p6;

    /**
     * Constructor
     *
     * @author Braden Norton
     */
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

    /**
     * Creates panel for amount of players options
     *
     * @return created panel
     */
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

    /**
     *
     * @param o added ActionListeners to the player amount options
     */
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

/**
 * Player Names & whether they're AI or Human options
 *
 * @author Braden Norton
 */
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

    /**
     * Constructor
     *
     * @param players Amount of players selected from PlayerAmountGUI options
     */
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
     * @return JPanel with options (name,Ai/Human) for the amount of players selected in PlayerAmountGUI
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

    /**
     *
     * @return JPanel with options (name,Ai/Human) for the amount of players selected in PlayerAmountGUI
     */
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

    /**
     *
     * @return JPanel with options (name,Ai/Human) for the amount of players selected in PlayerAmountGUI
     */
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

    /**
     *
     * @return JPanel with options (name,Ai/Human) for the amount of players selected in PlayerAmountGUI
     */
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

    /**
     *
     * @return JPanel with options (name,Ai/Human) for the amount of players selected in PlayerAmountGUI
     */
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

    /**
     *
     * @param o ActionListener added to each option in PlayerNameGUI
     */
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

    /**
     * Saves the names entered into each JTextField to an ArrayList of Strings
     *
     * @return list of entered names
     *
     * @author Braden Norton
     */
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

    /**
     * Saves AI checkbox selections to an ArrayList of Booleans for each player
     *
     * Saves true to list if AI checkbox selected for that player
     * Saves false otherwise for that player
     *
     * @return list of AI players
     *
     * @author Braden Norton
     */
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

/**
 * Displays all the possible game options once the setup is complete (players chosen & map created)
 *
 * @author Braden Norton
 */
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

    /**
     * Constructor
     *
     * @param players list of players playing the game
     * @param p current player
     */
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

    /**
     * Adds menu to the BoardGUI frame with save and quit options
     *
     * @author
     */
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

    /**
     *
     * @return Panel that holds all the buttons on the board
     *
     * @author Braden Norton
     */
    private JPanel boardGUIButtons()
    {
        // Create Panel
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1,5));

        // JButtons
        // Reinforce
        reinforce = new JButton("Reinforce");
        reinforce.setActionCommand("Reinforce");

        // Attack
        attack = new JButton("Attack");
        attack.setActionCommand("Attack");

        // Fortify
        fortify = new JButton("Fortify");
        fortify.setActionCommand("Fortify");

        //AI Turn
        aiTurn = new JButton("Do AI Turn");
        aiTurn.setActionCommand("AI Turn");
        

        // End Turn
        endTurn = new JButton("End Turn");
        endTurn.setActionCommand("End Turn");

        //Button Enables
        if(currentPlayer.isAI()){
            reinforce.setEnabled(false);
            attack.setEnabled(false);
            fortify.setEnabled(false);
            aiTurn.setEnabled(true);
            endTurn.setEnabled(false);
        } else {
            reinforce.setEnabled(true);
            attack.setEnabled(false);
            fortify.setEnabled(false);
            aiTurn.setEnabled(false);
            endTurn.setEnabled(false);
        }

        // Add components to panel
        topPanel.add(reinforce);
        topPanel.add(attack);
        topPanel.add(fortify);
        topPanel.add(endTurn);
        topPanel.add(aiTurn);

        // Return Panel
        return topPanel;
    }

    /**
     *
     * @return Panel that displays the current player stats (name, troops, owned countries/continents)
     *
     * @author Braden Norton
     */
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

    /**
     *
     * @param name Current player name
     * @return JScrollPane that displays the current players turn
     *
     * @author Braden Norton
     */
    private JScrollPane boardGUI(String name)
    {
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

    /**
     *
     * @return Panel that holds the lists of owned countries for each player in the game
     *
     * @author Braden Norton
     */
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

    /**
     * Add listeners for the amount of players in the game
     *
     * @param e Actionlisteners added to the BoardGUI buttons
     * @param o ListSelectionListeners added to the BoardGUI lists (map state)
     */
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

    /**
     * Updates the player stats panel w/the stats of the player passed
     *
     * @param p Player to display stats of
     */
    void setCurrentPlayer(Player p)
    {
        this.currentPlayer = p;
        this.cpName = p.getName();
        this.cpTroops = p.getTroops();
        this.cpBonusReinforcements = p.getContinentBonus() + p.getCountryBonus();
        this.cpCountryAmount = p.getCapturedCountries().size();
        this.cpContinentAmount = p.getCapturedContinents().size();
    }

    /**
     * @return the list that holds player 1's owned countries
     *
     * @author Braden Norton
     */
    JList l1() { return oc1; }

    /**
     * @return the list that holds player 2's owned countries
     *
     * @author Braden Norton
     */
    JList l2() { return oc2; }

    /**
     * @return the list that holds player 3's owned countries
     *
     * @author Braden Norton
     */
    JList l3() { return oc3; }

    /**
     * @return the list that holds player 4's owned countries
     *
     * @author Braden Norton
     */
    JList l4() { return oc4; }

    /**
     * @return the list that holds player 5's owned countries
     *
     * @author Braden Norton
     */
    JList l5() { return oc5; }

    /**
     * @return the list that holds player 6's owned countries
     *
     * @author Braden Norton
     */
    JList l6() { return oc6; }

    /**
     * @return selected country in player 1's list as a String
     *
     * @author Braden Norton
     */
    String getL1(){ return oc1.getSelectedValue().toString();}

    /**
     * @return selected country in player 2's list as a String
     *
     * @author Braden Norton
     */
    String getL2(){return oc2.getSelectedValue().toString();}

    /**
     * @return selected country in player 3's list as a String
     *
     * @author Braden Norton
     */
    String getL3() {return oc3.getSelectedValue().toString();}

    /**
     * @return selected country in player 4's list as a String
     *
     * @author Braden Norton
     */
    String getL4() {return oc4.getSelectedValue().toString();}

    /**
     * @return selected country in player 5's list as a String
     *
     * @author Braden Norton
     */
    String getL5(){return oc5.getSelectedValue().toString();}

    /**
     * @return selected country in player 6's list as a String
     *
     * @author Braden Norton
     */
    String getL6(){return oc6.getSelectedValue().toString();}

    /**
     * Get the amount of troops residing in the selected country, and update the appropriate players TextArea underneath the list with the value
     *
     * @param p Player that owns the selected country
     * @param s Name of the selected country
     *
     * @author Braden Norton
     */
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

    /**
     * Enables other phase buttons once reinforcement phase is complete
     *
     * @author Braden Norton
     */
    void reinforcementPhaseComplete()
    {
        reinforce.setEnabled(false);
        attack.setEnabled(true);
        fortify.setEnabled(true);
        endTurn.setEnabled(true);
    }

    /**
     * Disables all buttons except for End Turn once a player completes Fortify phase
     *
     * @author Braden Norton
     */
    void fortifyPhaseComplete()
    {
        reinforce.setEnabled(false);
        attack.setEnabled(false);
        fortify.setEnabled(false);
        endTurn.setEnabled(true);
    }

    /**
     * Updates the results ScrollPane on main board with given string & automatically scrolls to bottom of the Pane
     *
     * @param s String of text to add to Pane
     *
     * @author Braden Norton
     */
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

    /**
     * Reset the board for the next player when the previous player ends their turn
     *
     * @param p new Current Player
     *
     * @author Braden Norton
     */
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

    /**
     * Update the amount of players playing, and the current player stats
     *
     * @param players
     *
     * @author Braden Norton
     */
    void updateStats(ArrayList<Player> players)
    {
        this.allPlayers = players;
        troops.setText(""+currentPlayer.getTroops());
        ownedCountries.setText(""+currentPlayer.getCapturedCountries().size());
        ownedContinents.setText(""+currentPlayer.getCapturedContinents().size());
    }

    /**
     * Updates the country lists in game state panel if the attacking player claimed ownership of the defending country during the Attack phase
     *
     * @param att Attacking player
     * @param def Defending player
     * @param c Name of country that attacker gained/defender lost
     *
     * @author Braden Norton
     */
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

    /**
     * Prompts user with JFileChooser & saves user provided save path as String with .ser extension
     *
     * @return String that contains the desired file's save path
     *
     * @author Braden Norton
     */
    String saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Game");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            return ("" +fileToSave.getAbsolutePath());
        }
        else
        {
            return "No path selected";
        }
    }

    /**
     * User selected quit from the board menu (quit w/out saving)
     * Game closes and prompts user w/JOptionPane informing them that the game has been exited
     *
     * @author Braden Norton
     */
    void quitGame()
    {
        // Exit Popup
        JOptionPane.showMessageDialog(this, "Thank you for playing!");

        // Stop program once exit popup closed
        System.exit(0);
    }
}

/**
 * Displays Reinforce Phase options
 *
 * @author Braden Norton
 */
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

    /**
     * Constructor
     *
     * @param cp Current Player
     * @param validMovement List of eligible troop movement amounts as Strings
     */
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

    /**
     *
     * @return Panel that displays the options for submitting a Reinforcement (which country to reinforce and how many troops) or to return back to the board
     *
     * @author Braden Norton
     */
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

    /**
     *
     * @param o Action listeners added to the ReinforceGUI options
     *
     * @author Braden Norton
     */
    void reinforceActionListener(ActionListener o)
    {
        movableTroops.addActionListener(o);
        submit.addActionListener(o);
        back.addActionListener(o);
    }

    /**
     * @return Selected country from owned country list
     *
     * @author Braden Norton
     */
    String getCountry()
    {
        return ocList.getSelectedValue().toString();
    }

    /**
     * @return Selected value from valid movable troops list, casted to an Integer
     *
     * @author Braden Norton
     */
    int getReinforceAmount()
    {
        String value = (String)movableTroops.getSelectedItem();
        return Integer.parseInt(value);
    }
}

/**
 * Displays all the options during the Attack Phase
 *
 * @author Braden Norton
 */
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

    /**
     * Constructor
     *
     * @param cp Current Player
     *
     * @author Braden Norton
     */
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

    /**
     * Creates all the options that can be displayed during the attack phase
     *
     * @author Braden Norton
     */
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

    /**
     * @return Panel that displays a list of countries that the current player owns, a list of countries they can attack depending on the selected owned country & button to select attack/def countries
     *
     * @author Braden Norton
     */
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

    /**
     * @return Panel with amount of dice that the attacker and defender can use depending on the selected countries from attackPanel
     *
     * @author Braden Norton
     */
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

    /**
     * @return Panel with attacker/defender stats, results window, and buttons to roll the dice and initiate attack or exit attack
     *
     * @author Braden Norton
     */
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

    /**
     * Sets the attacking & defending player names and opens dice selection panel when countries are selected from attackPanel
     * @param aPlayer Attacking player
     * @param dPlayer Defending player
     *
     * @author Braden Norton
     */
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

    /**
     * Opens battlePanel when dice are selected from dice selection panel
     *
     * @author Braden Norton
     */
    void battleStage()
    {
        getContentPane().removeAll();
        add(battlePanel());
        revalidate();
    }

    /**
     * @param o listeners added to buttons in AttackGUI
     * @param e listeners added to lists in AttackGUI
     */
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

    /**
     * Removes list selection listener of the Attackable country list when updating the list once a new owned country is selected
     * @param o Listener removed
     *
     * @author Braden Norton
     */
    public void removeACListener(ListSelectionListener o) { aCountries.removeListSelectionListener(o); }

    /**
     * @param o listener added to attackable country list
     *
     * @author Braden Norton
     */
    public void acCountryActionListener(ListSelectionListener o){aCountries.addListSelectionListener(o);}

    /**
     * @return List object for owned countries
     *
     * @author Braden Norton
     */
    JList getOCList() { return ocCurrent; }

    /**
     * @return List object for attackable countries
     *
     * @author Braden Norton
     */
    JList getACList() { return aCountries; }

    /**
     * @return Country name selected from owned country list as String
     *
     * @author Braden Norton
     */
    String getOCListValue(){ return ocCurrent.getSelectedValue().toString();}

    /**
     * @return Country name selected from attackable country list as String
     *
     * @author Braden Norton
     */
    String getACListValue() { return aCountries.getSelectedValue().toString();}

    /**
     * Updates the attackable country list when a new owned country is selected to the attackable countries from the new owned country selection
     *
     * @param l list of attackable country names from selected owned country
     */
    void updateACList(String[] l)
    {
        dList.clear();
        for(int i=0; i<l.length; ++i)
        {
            dList.addElement(l[i]);
        }
    }

    /**
     * Sets the amount of troops in the attacking country
     * @param s troop amount
     *
     * @author Braden Norton
     */
    void updateOCTroops(int s)
    {
        ocTroops.setText(""+s);
    }

    /**
     * Sets the amount of troops in the defending country
     * @param t troop amount
     *
     * @author Braden Norton
     */
    void updateACTroops(int t)
    {
        acTroops.setText("" + t);
    }

    /**
     * Sets the amount of allowed dice for the attacker
     * @param s List of allowed dice options
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

    /**
     * Sets the amount of allowed dice for the defender
     * @param s List of allowed dice options
     *
     * @author Braden Norton
     */
    void setDefDice(String[] s)
    {
        System.out.println(s.length);
        for(int i=0; i<s.length; ++i)
        {
            defDice.addItem(s[i]);
        }
    }

    /**
     * Sets the amount of allowed dice for the AI
     * @param s List of allowed dice options
     */
    void setAIDefDice(String[] s)
    {
        int temp = 0;
        for(int i=0; i<s.length; ++i)
        {
            ++temp;
        }
        System.out.println(temp);
    }

    /**
     * @return amount of the dice the attacker will use as a String from the combo box
     */
    String getADiceAmount() { return attDice.getSelectedItem().toString(); }

    /**
     * @return amount of the dice the defender will use as a String from the combo box
     */
    String getDDiceAmount() { return defDice.getSelectedItem().toString(); }

    /**
     * Update the battlePanel results window
     * @param s String to add to panel
     */
    void setRollResult(String s)
    {
        results.append(s);
    }

    /**
     * Update the battlePanel troop amount for attacker/defender after the roll
     * @param a troops remaining in attacking country
     * @param d troops remaining in defending country
     *
     * @author Braden Norton
     */
    void setBattleResults(int a, int d)
    {
        attTroops.setText("" + a);
        defTroops.setText("" + d);
    }

    /**
     * Disable the roll button once the battle has ended & all dice were rolled
     *
     * @author Braden Norton
     */
    void disableRoll()
    {
        roll.setEnabled(false);
    }
}

/**
 * Displays the options for the Fortify Phase
 *
 * @author Braden Norton
 */
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

    /**
     * Constructor
     *
     * @param p current player
     *
     * @author Braden Norton
     */
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

    /**
     * Create the options for the fortify phase
     *
     * @author Braden Norton
     */
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

    /**
     * @return Panel with the lists for the user to select which country to Fortify from owned countries and which country to fortify from
     *
     * @author Braden Norton
     */
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

    /**
     * @return panel that prompts user to select how many troops to fortify with
     *
     * @author Braden Norton
     */
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

    /**
     * Opens fortifyAmountPanel once the countries are selected
     *
     * @author Braden Norton
     */
    void fortifyAmountStage()
    {
        getContentPane().removeAll();
        add(fortifyAmountPanel());
        revalidate();
    }

    /**
     * @param o Listeners added to buttons
     * @param e Listeners added to lists
     *
     * @author Braden Norton
     */
    public void fortifyActionListener(ActionListener o, ListSelectionListener e)
    {
        movableTroops.addActionListener(o);
        submitFortify.addActionListener(o);
        moveTroops.addActionListener(o);
        cancelFortify.addActionListener(o);
        owned.addListSelectionListener(e);
        adj.addListSelectionListener(e);
    }

    /**
     * @return List object of owned countries
     *
     * @author Braden Norton
     */
    JList getOwnedList() { return owned; }

    /**
     * @return gets selected country in owned country list as String
     *
     * @author Braden Norton
     */
    String getOwnedListValue() { return owned.getSelectedValue().toString(); }

    /**
     * @return gets selected country in adjacent owned country list as String
     *
     * @author Braden Norton
     */
    String getAdjListValue() { return adj.getSelectedValue().toString(); }

    /**
     * Sets the adjacently owned country list to contain all the adjacently owned countries of a selected owned country
     *
     * @param a list of adjacently owned countries
     *
     * @author Braden Norton
     */
    void setAdjList(ArrayList<String> a)
    {
        adjModel.clear();
        for(int i=0; i<a.size(); ++i)
        {
            adjModel.addElement(a.get(i));
        }
    }

    /**
     * Sets amount to fortify combo box w/the valid troop movement options
     *
     * @param s valid troop movement options list
     *
     * @author Braden Norton
     */
    void setAmountToFortify(ArrayList<String> s)
    {
        for(int i=0; i<s.size();++i)
        {
            movableTroops.addItem(s.get(i));
        }
    }

    /**
     * @return User selected troop movement amount from movable troops combo box
     *
     * @author Braden Norton
     */
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
        submit.addActionListener(o);
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