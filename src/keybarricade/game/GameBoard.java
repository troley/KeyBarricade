package keybarricade.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class handles the most operations together with KeyInput inner-class and
 * basically the drawing/game play happens in these classes.
 *
 * @author René Uhliar, Miladin Jeremić, Len van Kampen
 */
public class GameBoard extends JPanel {

    private final int WINDOW_OFFSET = 25;
    private final int OBJECT_SPACE = 35;

    private int levelWidth;
    private int levelHeight;
    private int levelNumber;
    private boolean completed;
    private ArrayList<GameObject> objects; // every GameObject will be stored in here
    private Window window;
    private Player player;

    /**
     * Constructs new GameBoard and initializes basic variables
     */
    public GameBoard() {
        objects = new ArrayList<>();
        window = new Window("Key barricade", this);
        levelWidth = 0;
        levelHeight = 0;
        addKeyListener(new KeyInput());
    }

    /**
     * Returns the levelWidth
     *
     * @return levelWidth
     */
    public int getLevelWidth() {
        return levelWidth;
    }

    /**
     * Returns the levelHeight
     *
     * @return levelHeight
     */
    public int getLevelHeight() {
        return levelHeight;
    }

    /**
     * Sets the level
     *
     * @param levelNumber the level number to be set
     */
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    /**
     * Gets the level number.
     *
     * @return levelNumber
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * initLevel creates the game, for example if on the startscreen level 2 is
     * selected, this method looks for the "level2.txt" file and fills the
     * objects ArrayList with all the objects read from that file. Also sets the
     * optimal window size.
     */
    public void initLevel() {
        int x = WINDOW_OFFSET;
        int y = WINDOW_OFFSET;
        String level = "";

        // basically does everything that has to be done before the initialization of new level
        if (completed) {
            objects.removeAll(objects);
            setLevelNumber(getLevelNumber() + 1);
            player.setKeyInBag(null);
            completed = false;
        }

        try {
            Scanner readLevel = new Scanner(new File("src/keybarricade/levels/level" + getLevelNumber() + ".txt").getAbsoluteFile());
            while (readLevel.hasNext()) {
                level += readLevel.next(); // put String read from the file into a string
            }
            for (int i = 0; i < level.length(); i++) {
                char object = level.charAt(i); // put character at (i) position into object

                switch (object) { // initialize in-game objects letter by letter and add them into objects ArrayList

                    case 'n': // indicates new line, increase y, set levelWidth equal to x and reset x
                        y += OBJECT_SPACE;

                        if (levelWidth < x) {
                            levelWidth = x; // after every row, make levelWidth as big as x
                        }

                        x = WINDOW_OFFSET; // reset x
                        break;

                    case 'f': // initialize a new floor object
                        objects.add(new Floor(x, y));
                        x += OBJECT_SPACE;
                        break;

                    case 'w': // initialize a new wall object
                        objects.add(new Wall(x, y));
                        x += OBJECT_SPACE;
                        break;

                    case 'u': // initialize new barricade object number 100
                        objects.add(new Barricade(x, y, ID.Object100));
                        x += OBJECT_SPACE;
                        break;

                    case 'i': // initialize new barricade object number 200
                        objects.add(new Barricade(x, y, ID.Object200));
                        x += OBJECT_SPACE;
                        break;

                    case 'o': // initialize new barricade object number 300
                        objects.add(new Barricade(x, y, ID.Object300));
                        x += OBJECT_SPACE;
                        break;

                    case '1': // initialize new key object number 100
                        objects.add(new Key(x, y, ID.Object100));
                        x += OBJECT_SPACE;
                        break;

                    case '2': // initialize new key object number 200
                        objects.add(new Key(x, y, ID.Object200));
                        x += OBJECT_SPACE;
                        break;

                    case '3': // initialize new key object number 300
                        objects.add(new Key(x, y, ID.Object300));
                        x += OBJECT_SPACE;
                        break;

                    case 'p': // initialize new player object
                        player = new Player(x, y);
                        objects.add(new Floor(x, y));
                        objects.add(player);
                        x += OBJECT_SPACE;
                        break;

                    case 'x': // initialize new finish object
                        objects.add(new Finish(x, y));
                        x += OBJECT_SPACE;
                        break;
                }
            }
            levelHeight = y; // make levelHeight equal to y
        } catch (FileNotFoundException e) {
            if (getLevelNumber() == window.getLevelCount() + 1) { // if the last level has been reached
                JOptionPane.showMessageDialog(this, "Congratulations you've won the game!", "You won!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else { // else if what ever else might happen other than reaching the last level
                JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(getLevelNumber());
                System.exit(0);
            }
        }
        // sets the window size so that the game level fits nicely in it
        window.setSize(getLevelWidth() + (WINDOW_OFFSET + 4), getLevelHeight() + (2 * WINDOW_OFFSET) + 4);
    }

    // builds the world which is finally painted by the overriden paint method.
    private void buildLevel(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);

        for (int i = 0; i < objects.size(); i++) {
            GameObject object = objects.get(i);

            g.drawImage(object.getImage(), object.getX(), object.getY(), this);

            // draw the image of player on top of these objects while standing on them
            if (object instanceof Key || object instanceof Finish) {
                if (player.standsOnObject(object)) {
                    g.drawImage(player.getImage(), object.getX(), object.getY(), this);
                }
            }
            if (object instanceof Finish) {
                if (player.standsOnObject(object)) {
                    completed = true; // level is completed
                }
            }
            if (object instanceof Key) {
                if (!player.standsOnObject(object)) {
                    if (object.getId() == ID.Object100) {
                        object.drawObjectString(object, "100", g); // draw 100 on a key with ID.Object100
                    } else if (object.getId() == ID.Object200) {
                        object.drawObjectString(object, "200", g); // draw 200 on a key with ID.Object200
                    } else if (object.getId() == ID.Object300) {
                        object.drawObjectString(object, "300", g); // draw 300 on a key with ID.Object300
                    }
                }
            }

            if (object instanceof Barricade) {
                if (object.getId() == ID.Object100) {
                    object.drawObjectString(object, "100", g); // draw 100 on a barricade with ID.Object100
                } else if (object.getId() == ID.Object200) {
                    object.drawObjectString(object, "200", g); // draw 200 on a barricade with ID.Object200
                } else if (object.getId() == ID.Object300) {
                    object.drawObjectString(object, "300", g); // draw 300 on a barricade with ID.Object300
                }
            }
        }

        // draw if key is true or false, and the object id of the key if not null
        g.setColor(Color.BLACK);
        g.setFont(new Font(null, Font.PLAIN, 12));
        if (player.getKeyInBag() != null) {
            g.drawString("Key: " + player.isKeyObtained() + ", Id: " + player.getKeyInBag().getId(), getWidth() / 2 - 65, 15);
        } else {
            g.drawString("Key: " + player.isKeyObtained(), getWidth() / 2 - 22, 15);
        }

        // draw black rectangles arround each object in the game to create a grid
        for (int k = WINDOW_OFFSET; k < levelWidth; k += OBJECT_SPACE) {
            for (int j = WINDOW_OFFSET; j < levelHeight; j += OBJECT_SPACE) {
                g.drawRect(k, j, OBJECT_SPACE, OBJECT_SPACE);
            }
        }
        // if the level has been completed, initialize the new level
        if (completed) {
            initLevel();
            repaint();
        }
    }

    // no needless repaints after every single key press even if colliding with object (slightly improves performance)
    private void directionRepaint(int keyEventDirection) {
        if (player.getFacingDirection() != keyEventDirection) {
            repaint();
        }
    }

    // processes all the KeyEvents the player can press on the keyboard.
    class KeyInput extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            int input = e.getKeyCode();
            switch (input) {

                case KeyEvent.VK_UP: // move up key
                    if (!completed) {
                        directionRepaint(KeyEvent.VK_UP);
                        player.setFacingDirection(KeyEvent.VK_UP);
                        player.setPlayerImage("../imgs/playerUp.png");
                        if (player.checkCollision(objects, player.getFacingDirection())
                                || player.getY() <= OBJECT_SPACE) { // if there's collision or player tries to get out of gamebounds, dont let player move
                            return;
                        }
                        player.move(0, -OBJECT_SPACE);
                    }
                    break;

                case KeyEvent.VK_DOWN: // move down key
                    if (!completed) {
                        directionRepaint(KeyEvent.VK_DOWN);
                        player.setFacingDirection(KeyEvent.VK_DOWN);
                        player.setPlayerImage("../imgs/playerDown.png");
                        if (player.checkCollision(objects, player.getFacingDirection())
                                || player.getY() >= getLevelHeight() - OBJECT_SPACE) { // if there's collision or player tries to get out of gamebounds, dont let player move
                            return;
                        }
                        player.move(0, OBJECT_SPACE);
                    }
                    break;

                case KeyEvent.VK_LEFT: // move left key
                    if (!completed) {
                        directionRepaint(KeyEvent.VK_LEFT);
                        player.setFacingDirection(KeyEvent.VK_LEFT);
                        player.setPlayerImage("../imgs/playerLeft.png");
                        if (player.checkCollision(objects, player.getFacingDirection())
                                || player.getX() <= OBJECT_SPACE) { // if there's collision or player tries to get out of gamebounds, dont let player move
                            return;
                        }
                        player.move(-OBJECT_SPACE, 0);
                    }
                    break;

                case KeyEvent.VK_RIGHT: // move right key
                    if (!completed) {
                        directionRepaint(KeyEvent.VK_RIGHT);
                        player.setFacingDirection(KeyEvent.VK_RIGHT);
                        player.setPlayerImage("../imgs/playerRight.png");
                        if (player.checkCollision(objects, player.getFacingDirection())
                                || player.getX() >= getLevelWidth() - OBJECT_SPACE) { // if there's collision or player tries to get out of gamebounds, dont let player move
                            return;
                        }
                        player.move(OBJECT_SPACE, 0);
                    }
                    break;

                case KeyEvent.VK_E: // pickup the key key
                    for (int i = 0; i < objects.size(); i++) {
                        GameObject item = objects.get(i);
                        if (item instanceof Key) {
                            if (player.standsOnObject(item)) {
                                player.setKeyInBag(new Key(player.getX(), player.getY(), item.getId()));
                                player.setKeyObtained(true);
                                objects.remove(item);
                            }
                        }
                    }
                    break;

                case KeyEvent.VK_Q: // use the key key
                    if (player.isKeyObtained()) {
                        player.useKey(objects, player.getFacingDirection()); // use the key on the barricade if it fits
                    }
                    break;

                case KeyEvent.VK_R: // restart the level key 
                    int restart = JOptionPane.showConfirmDialog(
                            GameBoard.this, "Restart game?", "Restart Game", JOptionPane.YES_NO_OPTION);
                    if (restart == JOptionPane.YES_OPTION) {
                        restartLevel();
                    }
                    break;

                case KeyEvent.VK_ESCAPE: // exit the game key
                    int exit = JOptionPane.showConfirmDialog(
                            GameBoard.this, "Do you really want to exit?", "Exit Game", JOptionPane.YES_NO_OPTION);
                    if (exit == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                    break;
            }
            repaint();
        }
    }

    /**
     * If the player of the game pressed the "r" key, and selects "yes", the
     * level well be reset to when it first started, the barricades and keys
     * will be restored.
     */
    private void restartLevel() {
        objects.clear();
        player.setKeyInBag(null);
        player.setKeyObtained(false);
        completed = false;
        initLevel();
    }

    // paints the game world
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildLevel(g);
    }
}
