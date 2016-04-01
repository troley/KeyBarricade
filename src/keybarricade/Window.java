package keybarricade;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Window class extends JFrame and creates the frame of the board.
 * 
 * @author René Uhliar, Miladin Jeremic, Len van Kampen
 */
public class Window extends JFrame {

    private JLabel levellabel;
    private JComboBox levelbox;
    private JButton start;
    private JPanel mainpanel;
    private JPanel fieldpanel;
    private JPanel buttonpanel;
    private JCheckBox topCheck;

    /**
     * The Window constructor takes the parameters title and board 
     * and has an inner-class "actionPerformed" where the menu starts 
     * and where the level gets selected and game gets started.
     * 
     * 
     * @param title
     * @param board
     */
    public Window(String title, final GameBoard board) {
        createGameWindow(title);
        startComponentsInit(); //initialize components "start" button included
        /*
        some standard parameters, makes the game focusable, rezizable, and is shown on the monitor.
        */
        setFocusable(true);
        setResizable(false);
        setVisible(true);
        /**
         * The Inner-class, the selected level starts if the player clicks on 
         * the start button.
         * If the square "always on top" is selected, the game window will always 
         * be seen on the monitor.
         */
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (topCheck.isSelected()) {
                    setAlwaysOnTop(true);
                }
                    if (levelbox.getSelectedItem().equals("Level 1")) {
                        board.setLevelNumber(1);
                        setupLevel(board);
                    } else if (levelbox.getSelectedItem().equals("Level 2")) {
                        board.setLevelNumber(2);
                        setupLevel(board);
                    } else if (levelbox.getSelectedItem().equals("Level 3")) {
                        board.setLevelNumber(3);
                        setupLevel(board);
                    }
                }
        });
    }
    
    /**
     * createGameWindow sets some standard parameters as:
     * the title of the game, width and height of the game screen,
     * closes the game and shuts the game down if the window is closed.
     * 
     * @param title 
     */
    private void createGameWindow(String title) {
        this.setTitle(title);
        this.setSize(300, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    /**
     * startComponentsInit class adds components of
     * the level select menu together.
     */
    private void startComponentsInit() {
        levellabel = new JLabel("Enter level:");
        levelbox = new JComboBox();
        start = new JButton("Start");
        topCheck = new JCheckBox("Always on top");
        mainpanel = new JPanel(new BorderLayout());
        fieldpanel = new JPanel(new FlowLayout());
        buttonpanel = new JPanel(new FlowLayout());

        levelbox.addItem("Level 1");
        levelbox.addItem("Level 2");
        levelbox.addItem("Level 3");

        fieldpanel.add(levellabel);
        fieldpanel.add(levelbox);
        buttonpanel.add(topCheck);
        buttonpanel.add(start);
        mainpanel.add(fieldpanel, BorderLayout.NORTH);
        mainpanel.add(buttonpanel, BorderLayout.CENTER);
        add(mainpanel);
    }

    /**
     * The setupLevel class takes board as parameter, this gets called
     * if the level is selected and the start button is pressed.
     * it removes the first screen and creates the game on the level which is selected.
     * @param board 
     */
    private void setupLevel(GameBoard board) {
        this.remove(mainpanel);
        board.initWorld(); // creates objects in the world AND sets the frame size so the game fits in it
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(board);
        board.requestFocus();
    }
}
