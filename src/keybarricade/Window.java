package keybarricade;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JFrame {

    private JLabel levellabel;
    private JComboBox levelbox;
    private JButton start;
    private JPanel mainpanel;
    private JPanel fieldpanel;
    private JPanel buttonpanel;

    public Window(String title, GameBoard board) {
        createGameWindow(title);
        startComponentsInit(); //initialize components "start" button included
        setFocusable(true);

        start.addActionListener(new ActionListener() {
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
        setVisible(true);
    }

    private void createGameWindow(String title) {
        this.setTitle(title);
        this.setSize(300, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void startComponentsInit() {
        levellabel = new JLabel("Enter level:");
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
        mainpanel.add(fieldpanel, BorderLayout.NORTH);
        mainpanel.add(buttonpanel, BorderLayout.CENTER);
        add(mainpanel);
    }

    private void setupLevel(GameBoard board) {
        this.remove(mainpanel);
        board.initWorld(); // creates objects in the world AND sets the frame size so the game fits in it
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(board);
        board.requestFocus();
    }
}
