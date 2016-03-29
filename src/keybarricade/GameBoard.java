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

public class GameBoard extends JPanel {

    private final int WINDOW_OFFSET = 25;
    private final int OBJECT_SPACE = 35;

    private int levelWidth;
    private int levelHeight;
    private int currentFacingDirection;
    private int levelNumber;
    private boolean completed;
    private ArrayList<GameObject> objects;
    private Window window;
    private Player player;
    private Key key;

    public GameBoard() {
        objects = new ArrayList<>();
        window = new Window("Key barricade", this);
        levelWidth = 0;
        levelHeight = 0;
        currentFacingDirection = 0;
        addKeyListener(new KeyInput());
        setFocusable(true);
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getCurrentFacingDirection() {
        return currentFacingDirection;
    }

    public void initWorld() {
        int x = WINDOW_OFFSET;
        int y = WINDOW_OFFSET;
        String level = "";

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
                        key = new Key(x, y, ID.Object100);
                        objects.add(key);
                        x += OBJECT_SPACE;
                        break;
                        
                    case '2': // key object number 200
                        key = new Key(x, y, ID.Object200);
                        objects.add(key);
                        x += OBJECT_SPACE;
                        break;
                        
                    case '3': // key object number 300
                        key = new Key(x, y, ID.Object300);
                        objects.add(key);
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
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        if (getLevelWidth() > window.getWidth()) {
            if (getLevelHeight() > window.getHeight()) {
                window.setSize(getLevelWidth() + (WINDOW_OFFSET + 5), getLevelHeight() + (2 * WINDOW_OFFSET) + 4);
            }
        }
        window.setSize(getLevelWidth() + (WINDOW_OFFSET + 5), getLevelHeight() + (2 * WINDOW_OFFSET) + 4);
    }

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
                GameObject finish = object;
                if (player.standsOnObject(finish)) {
                    completed = true;
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
        }

        // draw if key is true or false and the object id of the key if true
        g.setColor(Color.BLACK);
        g.setFont(new Font(null, Font.PLAIN, 12));
        if (player.getKeyInBag() != null) {
            g.drawString("Key: " + player.isKeyObtained() + ", Id: " + player.getKeyInBag().getId(), getWidth() / 2 - 45, 15);
        } else {
            g.drawString("Key: " + player.isKeyObtained(), getWidth() / 2, 15);
        }

        // draw black rectangles arround each object in the game
        for (int k = WINDOW_OFFSET; k < this.getWidth() - WINDOW_OFFSET * 1; k += OBJECT_SPACE) {
            for (int j = WINDOW_OFFSET; j < this.getHeight() - WINDOW_OFFSET * 1; j += OBJECT_SPACE) {
                g.setColor(Color.BLACK);
                g.drawRect(k, j, OBJECT_SPACE, OBJECT_SPACE);
            }
        }

        if (completed) {
            initWorld();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildLevel(g);
        repaint();
    }

    class KeyInput extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            int input = e.getKeyCode();
            boolean justPickedUp = false;
            switch (input) {
                case KeyEvent.VK_UP:
                    if (!completed) {
                        currentFacingDirection = KeyEvent.VK_UP;
                        if (player.checkCollision(objects, currentFacingDirection)
                                || player.getY() <= OBJECT_SPACE) {
                            return;
                        }
                        player.move(0, -OBJECT_SPACE);
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (!completed) {
                        currentFacingDirection = KeyEvent.VK_DOWN;
                        if (player.checkCollision(objects, currentFacingDirection)
                                || player.getY() >= getLevelHeight() - OBJECT_SPACE) {
                            return;
                        }
                        player.move(0, OBJECT_SPACE);
                    }
                    break;

                case KeyEvent.VK_LEFT:
                    if (!completed) {
                        currentFacingDirection = KeyEvent.VK_LEFT;
                        if (player.checkCollision(objects, currentFacingDirection)
                                || player.getX() <= OBJECT_SPACE) {
                            return;
                        }
                        player.move(-OBJECT_SPACE, 0);
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (!completed) {
                        currentFacingDirection = KeyEvent.VK_RIGHT;
                        if (player.checkCollision(objects, currentFacingDirection)
                                || player.getX() >= getLevelWidth() - OBJECT_SPACE) {
                            return;
                        }
                        player.move(OBJECT_SPACE, 0);
                    }
                    break;

                case KeyEvent.VK_E:
                    if (!completed) {
                        for (int i = 0; i < objects.size(); i++) {
                            GameObject item = objects.get(i);
                            if (item instanceof Key) {
                                if (player.standsOnObject(item)) {
                                    player.setKeyInBag(new Key(player.getX(), player.getY(), item.getId()));
                                    objects.remove(item);
                                    justPickedUp = true;
                                    player.setKeyObtained(true);
                                }
                            }
                        }
                        if (player.isKeyObtained() && !justPickedUp) {
                            player.useKey(objects, GameBoard.this.getCurrentFacingDirection());
                        }
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
            System.out.println("X: " + player.getX() + " Y: " + player.getY());
            repaint();
        }
    }

    private void restartLevel() {
        objects.clear();
        player.setKeyInBag(null);
        player.setKeyObtained(false);
        completed = false;
        initWorld();
    }
}
