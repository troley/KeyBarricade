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

    private final int UP_DIRECTION = 1;
    private final int DOWN_DIRECTION = 2;
    private final int LEFT_DIRECTION = 3;
    private final int RIGHT_DIRECTION = 4;

    private final int WINDOW_OFFSET = 20;
    private final int OBJECT_SPACE = 35;
    private int w;
    private int h;
    private int lastKeyPressed;
    private int levelNumber;
    private boolean completed;
    private boolean hasKey;
    private ArrayList<GameObject> objects;
    private Window window;
    private Player player;
    private Key key;

    public GameBoard(int levelNumber) {
        objects = new ArrayList<GameObject>();
        this.levelNumber = levelNumber;
        w = 0;
        h = 0;
        lastKeyPressed = 0;
        completed = false;
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

    private void initWorld() {
        int x = WINDOW_OFFSET;
        int y = WINDOW_OFFSET;
        String level = "";
        try {
            Scanner readLevel = new Scanner(new File("src/keybarricade/level" + levelNumber + ".txt").getAbsoluteFile());
            while (readLevel.hasNext()) {
                level += readLevel.next();
            }
            for (int i = 0; i < level.length(); i++) {
                char object = level.charAt(i);
                char nextObject = '`';
                if (i < level.length() - 1) {
                    nextObject = level.charAt(i + 1);
                }

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
                } else if (object == 'b' && nextObject == '1') {
                    objects.add(new Barricade(x, y, ID.Object100));
                    x += OBJECT_SPACE;
                } else if (object == 'b' && nextObject == '2') {
                    objects.add(new Barricade(x, y, ID.Object200));
                    x += OBJECT_SPACE;
                } else if (object == 'b' && nextObject == '3') {
                    objects.add(new Barricade(x, y, ID.Object300));
                    x += OBJECT_SPACE;
                } else if (object == 'k' && nextObject == '1') {
                    key = new Key(x, y, ID.Object100);
                    objects.add(key);
                    x += OBJECT_SPACE;
                } else if (object == 'k' && nextObject == '2') {
                    key = new Key(x, y, ID.Object200);
                    objects.add(key);
                    x += OBJECT_SPACE;
                } else if (object == 'k' && nextObject == '3') {
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
        }
    }

    private void buildLevel(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        for (int i = 0; i < objects.size(); i++) {
            GameObject object = objects.get(i);

            for (int k = WINDOW_OFFSET; k < this.getWidth() - 14; k += OBJECT_SPACE) {
                for (int j = WINDOW_OFFSET; j < this.getHeight() - 25; j += OBJECT_SPACE) {
                    g.setColor(Color.BLACK);
                    g.drawRect(k, j, 35, 35);
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
                    window.setupLevel(getLevelNumber() + 1);
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

        g.setFont(new Font(null, Font.PLAIN, 12));
        g.drawString("Key: " + hasKey, getWidth() / 2 - 15, 15);

        if (completed) {
            g.drawString("Completed!", 15, 15);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildLevel(g);
    }

    class KeyInput extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            int input = e.getKeyCode();
            int pressECounter = 0;

            switch (input) {
                case KeyEvent.VK_UP:
                    if (!completed) {
                        lastKeyPressed = KeyEvent.VK_UP;
                        if (checkCollision(player, UP_DIRECTION)
                                || player.getY() <= OBJECT_SPACE) {
                            return;
                        }
                        player.move(0, -OBJECT_SPACE);
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (!completed) {
                        lastKeyPressed = KeyEvent.VK_DOWN;
                        if (checkCollision(player, DOWN_DIRECTION)
                                || player.getY() >= getLevelHeight() - OBJECT_SPACE) {
                            return;
                        }
                        player.move(0, OBJECT_SPACE);
                    }
                    break;

                case KeyEvent.VK_LEFT:
                    if (!completed) {
                        lastKeyPressed = KeyEvent.VK_LEFT;
                        if (checkCollision(player, LEFT_DIRECTION)
                                || player.getX() <= OBJECT_SPACE) {
                            return;
                        }
                        player.move(-OBJECT_SPACE, 0);
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (!completed) {
                        lastKeyPressed = KeyEvent.VK_RIGHT;
                        if (checkCollision(player, RIGHT_DIRECTION)
                                || player.getX() >= getLevelWidth() - OBJECT_SPACE) {
                            return;
                        }
                        player.move(OBJECT_SPACE, 0);
                    }
                    break;

                case KeyEvent.VK_E:
                    if (!completed) {
                        if (!hasKey) {
                            for (int i = 0; i < objects.size(); i++) {
                                GameObject item = objects.get(i);
                                if (item instanceof Key) {
                                    GameObject key = item;
                                    if (player.standsOnObject(key)) {
                                        hasKey = true;
                                        objects.remove(key);
                                        pressECounter = 1;
                                    }
                                }
                            }
                        } else {
                            if (pressECounter == 0) {
                                useKey();
                            }
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
            pressECounter = 0;
            repaint();

        }
    }

    private boolean checkCollision(Player player, int direction) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject object = objects.get(i);

            if (object instanceof Wall || object instanceof Barricade) {
                if (player.topCollision(object) && direction == UP_DIRECTION) {
                    return true;
                } else if (player.bottomCollision(object) && direction == DOWN_DIRECTION) {
                    return true;
                } else if (player.leftCollision(object) && direction == LEFT_DIRECTION) {
                    return true;
                } else if (player.rightCollision(object) && direction == RIGHT_DIRECTION) {
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
                    if (player.topCollision(object)
                            && lastKeyPressed == KeyEvent.VK_UP) {
                        objects.remove(object);
                        hasKey = false;
                    } else if (player.bottomCollision(object)
                            && lastKeyPressed == KeyEvent.VK_DOWN) {
                        objects.remove(object);
                        hasKey = false;
                    } else if (player.leftCollision(object)
                            && lastKeyPressed == KeyEvent.VK_LEFT) {
                        objects.remove(object);
                        hasKey = false;
                    } else if (player.rightCollision(object)
                            && lastKeyPressed == KeyEvent.VK_RIGHT) {
                        objects.remove(object);
                        hasKey = false;
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
        hasKey = false;
        completed = false;
        initWorld();
    }
}
