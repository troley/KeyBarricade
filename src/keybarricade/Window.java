package keybarricade;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JFrame {

    private final int OFFSET = 30;

    private GameBoard board;
    private JLabel levellabel;
    private JComboBox levelbox;
    private JButton start;
    private JPanel mainpanel;
    private JPanel fieldpanel;
    private JPanel buttonpanel;

    public Window(String title) {
        createGameWindow(this, title);
        startComponentsInit(); //initialize components "start" button included
        setFocusable(true);
        setResizable(false);
        
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (levelbox.getSelectedItem().equals("Level 1")) {
                    setupLevel(1);

                } else if (levelbox.getSelectedItem().equals("Level 2")) {
                    setupLevel(2);

                } else if (levelbox.getSelectedItem().equals("Level 3")) {
                    setupLevel(3);
                }
            }
        });

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (levelbox.getSelectedItem().equals("Level 1")) {
                        setupLevel(1);

                    } else if (levelbox.getSelectedItem().equals("Level 2")) {
                        setupLevel(2);

                    } else if (levelbox.getSelectedItem().equals("Level 3")) {
                        setupLevel(3);
                    }
                }
            }
        });
        setVisible(true);
    }

    private void createGameWindow(JFrame frame, String title) {
        frame.setTitle(title);
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    private void createGameWindow(JFrame frame, GameBoard board) {
        frame.remove(mainpanel);
        frame.setSize(board.getLevelWidth() + OFFSET, board.getLevelHeight() + 2 * OFFSET);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(board);
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

    private void setupLevel(int levelNumber) {
        board = new GameBoard(levelNumber);
        createGameWindow(Window.this, board);
        board.requestFocus();
    }
}
