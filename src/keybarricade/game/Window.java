package keybarricade.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Window class extends JFrame and creates the frame of the board.
 *
 * @author René Uhliar, Miladin Jeremić, Len van Kampen
 */
public class Window extends JFrame {

    private JLabel readMeLabel;
    private JLabel levellabel;
    private JComboBox levelbox;
    private JButton start;
    private JPanel mainpanel;
    private JPanel fieldpanel;
    private JPanel buttonpanel;

    /**
     * Constructs a new JFrame window where a game level can be selected and
     * started. Also has an check option to keep the window always on top.
     *
     * @param title the Window title.
     * @param board the GameBoard which is responsible for everything else after
     * the game has been started.
     */
    public Window(String title, final GameBoard board) {
        createGameWindow(title);
        startComponentsInit();
        setFocusable(true);
        setResizable(false);
        setVisible(true);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

    public int getLevelCount() {
        return levelbox.getItemCount();
    }

    // set some basic stuff up in the JFrame
    private void createGameWindow(String title) {
        this.setTitle(title);
        this.setSize(300, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    // initializes the components of the level select menu
    private void startComponentsInit() {
        levellabel = new JLabel("Enter level:");
        readMeLabel = new JLabel("For how to play instruction read README.txt");
        levelbox = new JComboBox();
        start = new JButton("Start");
        mainpanel = new JPanel(new BorderLayout());
        fieldpanel = new JPanel(new FlowLayout());
        buttonpanel = new JPanel(new FlowLayout());

        levelbox.addItem("Level 1");
        levelbox.addItem("Level 2");
        levelbox.addItem("Level 3");

        fieldpanel.add(levellabel);
        fieldpanel.add(levelbox);
        buttonpanel.add(start);
        buttonpanel.add(readMeLabel);
        mainpanel.add(fieldpanel, BorderLayout.NORTH);
        mainpanel.add(buttonpanel, BorderLayout.CENTER);
        add(mainpanel);
    }

    // should be called at the game start to setup the level objects etc.
    private void setupLevel(GameBoard board) {
        this.remove(mainpanel);
        board.initLevel(); // creates objects in the world AND sets the frame size so the game fits in it
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(board);
        board.requestFocus();
    }
}
