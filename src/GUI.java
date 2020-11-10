import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.*;
import javax.swing.event.*;

/**
 * GUI Class used for Risk Game that extends the JFrame class and implements ActionListener.
 *
 * @author Braden Norton
 */

public class GUI extends JFrame implements ActionListener, ListSelectionListener
{
    // Main Game
    private RiskGame game;

    // JFrame
    private JFrame frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8;

    // ContentPane
    private Container contentPane;

    // JPanels
    private JPanel mPanel; // Main menu panel

    private JPanel pPanel; // Player selection panel

    private JPanel nPanel; // Name selection panel

    private JPanel bPanel; // Board panel

    // GUI Options
    // Main menu buttons
    private JButton start,quit;

    // Player amount buttons
    private JButton p2,p3,p4,p5,p6;

    // Player options for board
    private JButton menu,state,attack,fortify,endTurn,back;

    // Player name input
    private JTextField in;

    // GUI Data
    // Number of players
    private int playerNumber;
    private int count;

    // All players set
    private boolean loaded;

    // Player names
    private ArrayList<String> names;

    private Player currentPlayer;

    // Countries list
    private JList ocList;

    /**
     * Constructor for GUI menu
     */
    public GUI()
    {
        // Create Risk Game
        game = new RiskGame();

        // Initialize ArrayLists
        names = new ArrayList<String>();

        // Initialize counter
        count = 0;

        // Board not ready
        loaded = false;

        // Main menu GUI
        menuGUI();
    }

    private void setup()
    {
        // Initialize game
        game.initialize(playerNumber, names);

        // Start board GUI
        boardGUI();
    }

    /**
     * Main menu GUI that welcomes the user and propmts them to either start or quit the game
     */
    public void menuGUI()
    {
        // Set JFrame
        frame1 = new JFrame("Risk GUI menu");

        // ContentPane
        Container contentPane = frame1.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        // Main Panel
        mPanel = new JPanel();
        mPanel.setLayout(new GridLayout(4,1));

        // Text Display
        JLabel welcome = new JLabel("Welcome to Risk!");
        welcome.setFont(new Font("Serif", Font.BOLD, 18));
        welcome.setHorizontalAlignment(JLabel.CENTER);

        JLabel choose = new JLabel("Select an option below to start the game or quit!");

        // Buttons
        start = new JButton("Start");
        start.addActionListener(this);

        quit = new JButton("Quit");
        quit.addActionListener(this);

        // Add components to panel
        mPanel.add(welcome); // add welcome message
        mPanel.add(choose); // add choose option message
        mPanel.add(start);
        mPanel.add(quit);

        // Add to contentPane
        contentPane.add(mPanel);

        // Finish frame set up
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setResizable(false);
        frame1.setVisible(true);
    }

    /**
     * GUI that opens when user selects "Start" from the main GUI.
     *
     * Will prompt the user to select number of players and their names.
     * Once all players are setup, the game will begin.
     *
     */
    public void startGUI()
    {
        // Set JFrame
        frame2 = new JFrame("Player Selection GUI");

        // Main panel
        JPanel pPanel = new JPanel();
        pPanel.setLayout(new GridLayout(6,1));

        // ContentPane
        Container contentPane = frame2.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        // Text display
        JLabel players = new JLabel("Please select number of players!");
        players.setFont(new Font("Serif", Font.BOLD, 18));

        // Buttons
        p2 = new JButton("Two Players");
        p2.addActionListener(this);

        p3 = new JButton("Three Players");
        p3.addActionListener(this);

        p4 = new JButton("Four Players");
        p4.addActionListener(this);

        p5 = new JButton("Five Players");
        p5.addActionListener(this);

        p6 = new JButton("Six Players");
        p6.addActionListener(this);

        // Add components to panel
        pPanel.add(players);
        pPanel.add(p2);
        pPanel.add(p3);
        pPanel.add(p4);
        pPanel.add(p5);
        pPanel.add(p6);

        // Set contentPanel
        contentPane.add(pPanel);

        // Finish frame set up
        frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame2.pack();
        frame2.setResizable(false);
        frame2.setVisible(true);
    }

    /**
     * GUI to select the player names.
     *
     * Uses a JTextField to get the input of the names.
     */
    private void playerGUI(int n)
    {
        // Set JFrame
        frame3 = new JFrame("Player Selection GUI");

        // Main panel
        nPanel = new JPanel();
        nPanel.setLayout(new GridLayout(2,1));

        // New panel to hold JLabel & JTextField
        JPanel pNum = new JPanel();
        pNum.setLayout(new BoxLayout(pNum,BoxLayout.X_AXIS));

        // ContentPane
        Container contentPane = frame3.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        // JTextField for input
        in = new JTextField(5);
        in.setEditable(true);
        in.setHorizontalAlignment(JTextField.LEFT);
        in.addActionListener(this);

        // Text Display
        JLabel names = new JLabel("Please enter player names!");
        names.setFont(new Font("Serif", Font.BOLD, 18));

        JLabel player = new JLabel("Player " + n + ": ");

        // Add JLabel & JTextField to panel
        pNum.add(player);
        pNum.add(in);

        // Add to nPanel
        nPanel.add(names);
        nPanel.add(pNum);

        // Set contentPane
        contentPane.add(nPanel);

        // Finish frame set up
        frame3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame3.pack();
        frame3.setResizable(false);
        frame3.setVisible(true);
    }

    /**
     *
     */
    private void boardGUI()
    {
        // Get current Player
        currentPlayer(game);

        // Get owned countries
        ArrayList<String> oc = new ArrayList<String>();
        ArrayList<Country> c = new ArrayList<Country>(currentPlayer.getCapturedCountries());
        for(int i = 0; i < c.size(); ++i)
        {
            oc.add(c.get(i).getName());
        }
        String[] s = oc.toArray(new String[0]);
        ocList = new JList<String>(s);
        ocList.addListSelectionListener(this);



        // Set JFrame
        frame4 = new JFrame("Board GUI");

        // ContentPane
        Container contentPane = frame4.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        // Panels
        // Main Board panel
        bPanel = new JPanel();
        bPanel.setLayout(new BoxLayout(bPanel,BoxLayout.X_AXIS));

        // Left side of bPanel
        JPanel lPanel = new JPanel();
        lPanel.setLayout(new GridLayout(4,1));

        // Right side of bPanel
        JPanel rPanel = new JPanel();
        rPanel.setLayout(new GridLayout(4,1));

        //JLabel Indicators
        // Whose Turn
        JLabel turn = new JLabel("Turn: " + currentPlayer.getName());

        // Amount of troops
        JLabel tNum = new JLabel("Troops Ammount: " + currentPlayer.getTroops());

        // Countries owned
        JPanel listPanel = new JPanel();
        JLabel ownedC = new JLabel("Countries owned: ");
        JScrollPane scroll = new JScrollPane(ocList);
        listPanel.add(ownedC);
        listPanel.add(scroll);


        // Buttons
        // Menu
        menu = new JButton("Menu");
        menu.addActionListener(this);

        // State of map
        state = new JButton("State of Map");
        state.addActionListener(this);

        // Attack
        attack = new JButton("Attack");
        attack.addActionListener(this);

        // Fortify
        fortify = new JButton("Fortify");
        fortify.addActionListener(this);

        // End Turn
        endTurn = new JButton("End Turn");
        endTurn.addActionListener(this);

        // Set up bPanel
        // Left side
        lPanel.add(menu);
        lPanel.add(turn);
        lPanel.add(tNum);
        lPanel.add(listPanel);

        // Right side
        rPanel.add(state);
        rPanel.add(attack);
        rPanel.add(fortify);
        rPanel.add(endTurn);

        // Add L and R
        bPanel.add(lPanel);
        bPanel.add(rPanel);

        // Set contentPane
        contentPane.add(bPanel);

        // Finish frame set up
        frame4.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame4.pack();
        frame4.setResizable(false);
        frame4.setVisible(true);
    }

    /**
     *
     */
    private void menuButtonGui()
    {
        frame5 = new JFrame("Menu Button GUI");
        Container contentPane = frame5.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        JLabel q = new JLabel("Menu Options: ");

        quit = new JButton("Quit");
        quit.addActionListener(this);

        back = new JButton("Back");
        back.addActionListener(this);

        contentPane.add(q);
        contentPane.add(back);
        contentPane.add(quit);

        // Finish frame set up
        frame5.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame5.pack();
        frame5.setResizable(false);
        frame5.setVisible(true);
    }

    /**
     *
     */
    private void mapStateGUI()
    {
        frame6 = new JFrame("State of Map");
        Container contentPane = frame5.getContentPane();
        contentPane.setLayout(new GridBagLayout());


    }

    /**
     *
     */
    private void nameSelect()
    {
        // All players havent selected their names
        ++count;
        if(names.size() < playerAmount())
        {
            playerGUI(count);
        }
        // All players have chosen their names
        else
        {
            // Leave name selection GUI
            frame3.dispose();

            // Print all players names
            for(int i=0; i<names.size(); ++i)
            {
                System.out.println("Player " + (i+1) + ": " + names.get(i));
            }

            // Intialize board w/players
            Collections.shuffle(names);
            setup();
        }

    }

    /**
     * Getter method that returns the number of users playing Risk.
     */
    private int playerAmount()
    {
        return playerNumber;
    }

    private void currentPlayer(RiskGame g)
    {
        System.out.println(g.getCurrentPlayer().getName());
        currentPlayer = g.getCurrentPlayer();
    }

    /**
     * This action listener is called when the user clicks / enters
     * information using the GUI.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object o = e.getSource();

        // Start selected
        if(o == start)
        {
            frame1.dispose();
            System.out.println("Start game!");
            startGUI();
        }

        if(o == quit)
        {
            System.out.println("Game quit! Thanks for playing!");
            System.exit(0);
        }

        if(o == p2)
        {
            // Set player number
            playerNumber = 2;
            System.out.println("Number of players selected: " + playerAmount());

            // Open player name selection GUI
            frame2.dispose();
            nameSelect();
        }

        if(o == p3)
        {
            playerNumber = 3;
            System.out.println("Number of players selected: " + playerAmount());

            // Open player name selection GUI
            frame2.dispose();
            nameSelect();
        }

        if(o == p4)
        {
            playerNumber = 4;
            System.out.println("Number of players selected: " + playerAmount());

            // Open player name selection GUI
            frame2.dispose();
            nameSelect();
        }

        if(o == p5)
        {
            playerNumber = 5;
            System.out.println("Number of players selected: " + playerAmount());

            // Open player name selection GUI
            frame2.dispose();
            nameSelect();
        }

        if(o == p6)
        {
            playerNumber = 6;
            System.out.println("Number of players selected: " + playerAmount());

            // Open player name selection GUI
            frame2.dispose();
            nameSelect();
        }

        if(o == in)
        {
            // Add input to Array of player names
            names.add(in.getText());
            System.out.println("Player added!");

            // Reset JTextField
            in.setText(""); // resets text box

            frame3.dispose();
            nameSelect();
        }

        if(o == menu)
        {
            menuButtonGui();
        }

        if(o == back)
        {
            frame5.dispose();
        }

        if(o == state)
        {

        }

        if(o == attack)
        {

        }

        if(o == fortify)
        {

        }
    }

    /**
     * This actions listener is called when the user selects an
     * item from a list, i.e., an item from the JList
     */
    public void valueChanged(ListSelectionEvent le)
    {
        Object o = le.getSource();

        // Item from itemList selected
        if(o == ocList)
        {
            if(!ocList.getValueIsAdjusting()) // when user has selected an item
            {
                System.out.println("JList item selected: " + ocList.getSelectedValue());
            }
        }
    }
}