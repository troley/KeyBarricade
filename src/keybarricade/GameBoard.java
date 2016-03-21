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

    private final int WINDOW_OFFSET = 20;
    private final int OBJECT_SPACE = 35;

    private int w;
    private int h;
    private int lastKeyPressed; // to determine which barricade should be opened if surrounded by barricades
    private int levelNumber;
    private boolean completed;
    private boolean hasKey;
    private ArrayList<GameObject> objects;
    private Player player;
    private Key key;
    private Key keyInBag; // when key is picked up this var will be instantiated

    public GameBoard(int levelNumber) {
        objects = new ArrayList<>();
        this.levelNumber = levelNumber;
        w = 0;
        h = 0;
        lastKeyPressed = 0;
        hasKey = false;
        addKeyListener(new KeyInput());
        setFocusable(true);
        initWorld();
    }

    public int getLevelWidth() {
        return w;
    }

    public int getLevelHeight() {
        return h;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isLevelCompleted() {
        return completed;
    }

    private void initWorld() {
        int x = WINDOW_OFFSET;
        int y = WINDOW_OFFSET;
        String level = "";

        if (completed) {
            objects.removeAll(objects);
            setLevelNumber(getLevelNumber() + 1);
            keyInBag = null;
            completed = false;
        }

        try {
            Scanner readLevel = new Scanner(new File("src/keybarricade/level" + getLevelNumber() + ".txt").getAbsoluteFile());
            while (readLevel.hasNext()) {
                level += readLevel.next();
            }
            for (int i = 0; i < level.length(); i++) {
                char object = level.charAt(i);

                if (object == 'n') {
                    y += OBJECT_SPACE;

                    if (w < x) {
                        w = x;
                    }

                    x = WINDOW_OFFSET;
                } else if (object == 'f') {
                    objects.add(new Floor(x, y));
                    x += OBJECT_SPACE;
                } else if (object == 'w') {
                    objects.add(new Wall(x, y));
                    x += OBJECT_SPACE;
                } else if (object == 'u') { // barricade object number 100
                    objects.add(new Barricade(x, y, ID.Object100));
                    x += OBJECT_SPACE;
                } else if (object == 'i') { // barricade object number 200
                    objects.add(new Barricade(x, y, ID.Object200));
                    x += OBJECT_SPACE;
                } else if (object == 'o') { // barricade object number 300
                    objects.add(new Barricade(x, y, ID.Object300));
                    x += OBJECT_SPACE;
                } else if (object == '1') { // key object number 100
                    key = new Key(x, y, ID.Object100);
                    objects.add(key);
                    x += OBJECT_SPACE;
                } else if (object == '2') { // key object number 200
                    key = new Key(x, y, ID.Object200);
                    objects.add(key);
                    x += OBJECT_SPACE;
                } else if (object == '3') { // key object number 300
                    key = new Key(x, y, ID.Object300);
                    objects.add(key);
                    x += OBJECT_SPACE;
                } else if (object == 'p') {
                    player = new Player(x, y);
                    objects.add(new Floor(x, y));
                    objects.add(player);
                    x += OBJECT_SPACE;
                } else if (object == 'x') {
                    objects.add(new Finish(x, y));
                    x += OBJECT_SPACE;
                }
            }
            h = y;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void buildLevel(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);

        for (GameObject object : objects) {
            for (int k = WINDOW_OFFSET; k < this.getWidth() - WINDOW_OFFSET * 2; k += OBJECT_SPACE) {
                for (int j = WINDOW_OFFSET; j < this.getHeight() - WINDOW_OFFSET * 2; j += OBJECT_SPACE) {
                    g.setColor(Color.BLACK);
                    g.drawRect(k, j, OBJECT_SPACE, OBJECT_SPACE);
                }
            }

            g.drawImage(object.getImage(), object.getX(), object.getY(), this);

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
                        drawObjectString(object, "100", g);
                    } else if (object.getId() == ID.Object200) {
                        drawObjectString(object, "200", g);
                    } else if (object.getId() == ID.Object300) {
                        drawObjectString(object, "300", g);
                    }
                }
            }

            if (object instanceof Barricade) {
                if (!player.standsOnObject(object)) {
                    if (object.getId() == ID.Object100) {
                        drawObjectString(object, "100", g);
                    } else if (object.getId() == ID.Object200) {
                        drawObjectString(object, "200", g);
                    } else if (object.getId() == ID.Object300) {
                        drawObjectString(object, "300", g);
                    }
                }
            }
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font(null, Font.PLAIN, 12));
        if (keyInBag != null) {
            g.drawString("Key: " + hasKey + ", Id: " + keyInBag.getId(), getWidth() / 2 - 45, 15);
        } else {
            g.drawString("Key: " + hasKey, getWidth() / 2, 15);
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
                        lastKeyPressed = KeyEvent.VK_UP;
                        if (checkPlayerCollision(player, lastKeyPressed)
                                || player.getY() <= OBJECT_SPACE) {
                            return;
                        }
                        player.move(0, -OBJECT_SPACE);
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (!completed) {
                        lastKeyPressed = KeyEvent.VK_DOWN;
                        if (checkPlayerCollision(player, lastKeyPressed)
                                || player.getY() >= getLevelHeight() - OBJECT_SPACE) {
                            return;
                        }
                        player.move(0, OBJECT_SPACE);
                    }
                    break;

                case KeyEvent.VK_LEFT:
                    if (!completed) {
                        lastKeyPressed = KeyEvent.VK_LEFT;
                        if (checkPlayerCollision(player, lastKeyPressed)
                                || player.getX() <= OBJECT_SPACE) {
                            return;
                        }
                        player.move(-OBJECT_SPACE, 0);
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (!completed) {
                        lastKeyPressed = KeyEvent.VK_RIGHT;
                        if (checkPlayerCollision(player, lastKeyPressed)
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
                                    keyInBag = new Key(player.getX(), player.getY(), item.getId());
                                    objects.remove(item);
                                    justPickedUp = true;
                                    hasKey = true;
                                }
                            }
                        }
                        if (hasKey && !justPickedUp) {
                            useKey();
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

    private boolean checkPlayerCollision(Player player, int direction) {
        for (GameObject item : objects) {
            if (item instanceof Wall || item instanceof Barricade) {
                if (player.topCollision(item) && direction == KeyEvent.VK_UP) {
                    return true;
                } else if (player.bottomCollision(item) && direction == KeyEvent.VK_DOWN) {
                    return true;
                } else if (player.leftCollision(item) && direction == KeyEvent.VK_LEFT) {
                    return true;
                } else if (player.rightCollision(item) && direction == KeyEvent.VK_RIGHT) {
                    return true;
                }
            }
        }
        return false;
    }

    private void useKey() {
        if (hasKey) {
            for (int i = 0; i < objects.size(); i++) {
                GameObject object = objects.get(i);
                if (object instanceof Barricade) {

                    boolean keyFits = keyInBag.getId().equals(object.getId());

                    if (player.topCollision(object)
                            && lastKeyPressed == KeyEvent.VK_UP
                            && keyFits) {
                        objects.remove(object);
                    } else if (player.bottomCollision(object)
                            && lastKeyPressed == KeyEvent.VK_DOWN
                            && keyFits) {
                        objects.remove(object);
                    } else if (player.leftCollision(object)
                            && lastKeyPressed == KeyEvent.VK_LEFT
                            && keyFits) {
                        objects.remove(object);
                    } else if (player.rightCollision(object)
                            && lastKeyPressed == KeyEvent.VK_RIGHT
                            && keyFits) {
                        objects.remove(object);
                    }
                }
            }
        }
    }

    private void drawObjectString(GameObject object, String stringNumber, Graphics g) {
        if (object instanceof Key) {
            g.setColor(Color.RED);
            g.setFont(new Font(null, Font.PLAIN, 10));
            g.drawString(stringNumber, object.getX() + 4, object.getY() + 11);
        } else if (object instanceof Barricade) {
            g.setColor(Color.WHITE);
            g.setFont(new Font(null, Font.PLAIN, 10));
            g.drawString(stringNumber, object.getX() + 4, object.getY() + 11);
        }
    }

    private void restartLevel() {
        objects.clear();
        keyInBag = null;
        hasKey = false;
        completed = false;
        initWorld();
    }
}
