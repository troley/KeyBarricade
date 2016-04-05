package keybarricade;

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
 * This class handles the most operations and basically the game play happens in
 * this class.
 *
 * @author René Uhliar, Miladin Jeremić, Len van Kampen
 */
public class GameBoard extends JPanel {

    private final int WINDOW_OFFSET = 25;
    private final int OBJECT_SPACE = 35;

    private int levelWidth;
    private int levelHeight;
    private int currentFacingDirection;
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
        currentFacingDirection = 0;
        addKeyListener(new KeyInput());
        setFocusable(true);
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
     * The getCurrentFacingDirection is the direction the player is facing, this
     * is equal to the last key is pressed.
     *
     * Example: If the player of the game pressed the down arrow on the
     * keyboard, and there's a floor or key on the space under the player, the
     * player moves there and the current facing direction is downwards, then if
     * the player has a key and wants to open a barricade on the right of the
     * player, the player first needs to press the right arrow key on the
     * keyboard, else the barricade won't open even if the player has the right
     * key for the barricade.
     *
     * @return
     */
    public int getCurrentFacingDirection() {
        return currentFacingDirection;
    }

    /**
     * initWorld creates the game, for example if on the startscreen level 2 is
     * selected, this method looks for the "level2.txt" file and fills the
     * objects ArrayList with all the objects read from that file. Also sets the
     * optimal window size.
     *
     * The file characters representing GameObjects:
     *
     * n - new line (to increase the Y size of the window) p - player f - floor
     * w - wall x - finish u - barricade with the object number 100 i -
     * barricade with the object number 200 o - barricade with the object number
     * 300 1 - key with the object number 100 2 - key with the object number 200
     * 3 - key with the object number 300
     */
    public void initWorld() {
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
            Scanner readLevel = new Scanner(new File("src/keybarricade/level" + getLevelNumber() + ".txt").getAbsoluteFile());
            while (readLevel.hasNext()) {
                level += readLevel.next();
            }
            for (int i = 0; i < level.length(); i++) {
                char object = level.charAt(i);

                switch (object) {
                    case 'n':
                        y += OBJECT_SPACE;

                        if (levelWidth < x) {
                            levelWidth = x;
                        }

                        x = WINDOW_OFFSET;
                        break;
                        
                    case 'f':
                        objects.add(new Floor(x, y));
                        x += OBJECT_SPACE;
                        break;
                        
                    case 'w':
                        objects.add(new Wall(x, y));
                        x += OBJECT_SPACE;
                        break;

                    case 'u': // barricade object number 100
                        objects.add(new Barricade(x, y, ID.Object100));
                        x += OBJECT_SPACE;
                        break;

                    case 'i': // barricade object number 200
                        objects.add(new Barricade(x, y, ID.Object200));
                        x += OBJECT_SPACE;
                        break;

                    case 'o': // barricade object number 300
                        objects.add(new Barricade(x, y, ID.Object300));
                        x += OBJECT_SPACE;
                        break;

                    case '1': // key object number 100
                        objects.add(new Key(x, y, ID.Object100));
                        x += OBJECT_SPACE;
                        break;

                    case '2': // key object number 200
                        objects.add(new Key(x, y, ID.Object200));
                        x += OBJECT_SPACE;
                        break;

                    case '3': // key object number 300
                        objects.add(new Key(x, y, ID.Object300));
                        x += OBJECT_SPACE;
                        break;

                    case 'p':
                        player = new Player(x, y);
                        objects.add(new Floor(x, y));
                        objects.add(player);
                        x += OBJECT_SPACE;
                        break;

                    case 'x':
                        objects.add(new Finish(x, y));
                        x += OBJECT_SPACE;
                        break;
                }
            }
            levelHeight = y;
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
                    completed = true;
                    repaint();
                }
            }
            if (object instanceof Key) {
                if (!player.standsOnObject(object)) {
                    if (object.getId() == ID.Object100) {
                        object.drawObjectString(object, "100", g);
                    } else if (object.getId() == ID.Object200) {
                        object.drawObjectString(object, "200", g);
                    } else if (object.getId() == ID.Object300) {
                        object.drawObjectString(object, "300", g);
                    }
                }
            }

            if (object instanceof Barricade) {
                if (object.getId() == ID.Object100) {
                    object.drawObjectString(object, "100", g);
                } else if (object.getId() == ID.Object200) {
                    object.drawObjectString(object, "200", g);
                } else if (object.getId() == ID.Object300) {
                    object.drawObjectString(object, "300", g);
                }
            }
        }

        // draw if key is true or false and the object id of the key if true
        g.setColor(Color.BLACK);
        g.setFont(new Font(null, Font.PLAIN, 12));
        if (player.getKeyInBag() != null) {
            g.drawString("Key: " + player.isKeyObtained() + ", Id: " + player.getKeyInBag().getId(), getWidth() / 2 - 65, 15);
        } else {
            g.drawString("Key: " + player.isKeyObtained(), getWidth() / 2 - 22, 15);
        }

        // draw black rectangles arround each object in the game
        for (int k = WINDOW_OFFSET; k < this.getWidth() - WINDOW_OFFSET * 1; k += OBJECT_SPACE) {
            for (int j = WINDOW_OFFSET; j < this.getHeight() - WINDOW_OFFSET * 1; j += OBJECT_SPACE) {
                g.setColor(Color.BLACK);
                g.drawRect(k, j, OBJECT_SPACE, OBJECT_SPACE);
            }
        }

        // if the level has been completed, initialize the new level
        if (completed) {
            initWorld();
        }
    }
    // no needless repaints after every single key press even if colliding with object (better performance)
    private void directionRepaint(int keyEventDirection) {
        if(currentFacingDirection != keyEventDirection) {
            repaint();
        }
    }

    // processes all the KeyEvents the player can press on the keyboard.
    class KeyInput extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            int input = e.getKeyCode();
            switch (input) {

                case KeyEvent.VK_UP:
                    if (!completed) {
                        directionRepaint(KeyEvent.VK_UP);
                        currentFacingDirection = KeyEvent.VK_UP;
                        player.setPlayerImage("playerUp.png");
                        if (player.checkCollision(objects, currentFacingDirection)
                                || player.getY() <= OBJECT_SPACE) {
                            return;
                        }
                        player.move(0, -OBJECT_SPACE);
                    }
                break;
                    
                case KeyEvent.VK_DOWN:
                    if (!completed) {
                        directionRepaint(KeyEvent.VK_DOWN);
                        currentFacingDirection = KeyEvent.VK_DOWN;
                        player.setPlayerImage("playerDown.png");
                        if (player.checkCollision(objects, currentFacingDirection)
                                || player.getY() >= getLevelHeight() - OBJECT_SPACE) {
                            return;
                        }
                        player.move(0, OBJECT_SPACE);
                    }
                    break;

                case KeyEvent.VK_LEFT:
                    if (!completed) {
                        directionRepaint(KeyEvent.VK_LEFT);
                        currentFacingDirection = KeyEvent.VK_LEFT;
                        player.setPlayerImage("playerLeft.png");
                        if (player.checkCollision(objects, currentFacingDirection)
                                || player.getX() <= OBJECT_SPACE) {
                            return;
                        }
                        player.move(-OBJECT_SPACE, 0);
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (!completed) {
                        directionRepaint(KeyEvent.VK_RIGHT);
                        currentFacingDirection = KeyEvent.VK_RIGHT;
                        player.setPlayerImage("playerRight.png");
                        if (player.checkCollision(objects, currentFacingDirection)
                                || player.getX() >= getLevelWidth() - OBJECT_SPACE) {
                            return;
                        }
                        player.move(OBJECT_SPACE, 0);
                    }
                    break;

                case KeyEvent.VK_E:
                    for (int i = 0; i < objects.size(); i++) {
                        GameObject item = objects.get(i);
                        if (item instanceof Key) {
                            if (player.standsOnObject(item)) {
                                player.setKeyInBag(new Key(player.getX(), player.getY(), item.getId()));
                                objects.remove(item);
                                player.setKeyObtained(true);
                            }
                        }
                    }
                    break;

                case KeyEvent.VK_Q:
                    if (player.isKeyObtained()) {
                        player.useKey(objects, GameBoard.this.getCurrentFacingDirection());
                    }
                    break;

                case KeyEvent.VK_R:
                    int restart = JOptionPane.showConfirmDialog(
                            GameBoard.this, "Restart game?", "Restart Game", JOptionPane.YES_NO_OPTION);
                    if (restart == JOptionPane.YES_OPTION) {
                        restartLevel();
                    }
                    break;

                case KeyEvent.VK_ESCAPE:
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
     * level gets reset to when it first started, the barricades and keys gets
     * restored.
     */
    private void restartLevel() {
        objects.clear();
        player.setKeyInBag(null);
        player.setKeyObtained(false);
        completed = false;
        initWorld();
    }
    
    // paints the game world
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildLevel(g);
    }
}
