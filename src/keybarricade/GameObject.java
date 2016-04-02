package keybarricade;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Abstract Class GameObject, every visual part of the game like: 
 * Wall, Key, Window, Floor, Barricade Player are GameObjects.
 * 
 * 
 * @author René Uhliar, Miladin Jeremic, Len van Kampen
 */
public abstract class GameObject {

    private int x;
    private int y;
    private ID id;
    private Image image;

    /**
     * Sets the GameObjects x and y coordinates.
     * @param x
     * @param y
     */
    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the GameObjects x and y and ID.
     * @param x
     * @param y
     * @param id
     */
    public GameObject(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    /**
     * Sets the x coordinate
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the x coordinate
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * sets the Y coordinate
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * gets the Y coordinate
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the object of the gameboard to the fitting image,
     * So a keyobject gets a keyImage.
     * @param image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Returns the image
     * @return  image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the id
     * @param id
     */
    public void setId(ID id) {
        this.id = id;
    }

    /**
     * Gets the id
     * @return
     */
    public ID getId() {
        return id;
    }

    /**
     * Checks for topCollision, if the user presses up arrow 
     * and there's a wall or barricade, the player won't move.
     * @param object
     * @return
     */
    public boolean topCollision(GameObject object) {
        return (((getY() - 35) == object.getY()) && (getX() == object.getX()));
    }

    /**
     * Checks for BottomCollision, if the user presses down arrow 
     * and there's a wall or barricade, the player won't move.
     * @param object
     * @return
     */
    public boolean bottomCollision(GameObject object) {
        return (((getY() + 35) == object.getY()) && (getX() == object.getX()));
    }

    /**
     * Checks for leftCollision, if the user presses left arrow 
     * and there's a wall or barricade, the player won't move.
     * @param object
     * @return
     */
    public boolean leftCollision(GameObject object) {
        return (((getX() - 35) == object.getX()) && (getY() == object.getY()));
    }

    /**
     * Checks for rightCollision, if the user presses right arrow 
     * and there's a wall or barricade, the player won't move.
     * @param object
     * @return
     */
    public boolean rightCollision(GameObject object) {
        return (((getX() + 35) == object.getX()) && (getY() == object.getY()));
    }

    /**
     * Get the coordinates where the player is 
     * @param object
     * @return
     */
    public boolean standsOnObject(GameObject object) {
        return (getX() == object.getX() && getY() == object.getY());
    }

    /**
     * Iterates through the given arraylist of gameobjects and checks if the
     * objects at the given direction are a barricade or a wall. If so, the
     * objects which is checking for collision will stand still and won't move
     * to that location.
     *
     * @param objects the arraylist of gameobjects which may contain wall or
     * barricade.
     * @param direction the direction at which a collision should be checked.
     * @return true/false
     */
    public boolean checkCollision(ArrayList<GameObject> objects, int direction) {
        for (GameObject item : objects) {
            if (item instanceof Wall || item instanceof Barricade) {
                if (this.topCollision(item) && direction == KeyEvent.VK_UP) {
                    return true;
                } else if (this.bottomCollision(item) && direction == KeyEvent.VK_DOWN) {
                    return true;
                } else if (this.leftCollision(item) && direction == KeyEvent.VK_LEFT) {
                    return true;
                } else if (this.rightCollision(item) && direction == KeyEvent.VK_RIGHT) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkCollision(GameObject object, int direction) {
        if (object instanceof Wall || object instanceof Barricade) {
            if (this.topCollision(object) && direction == KeyEvent.VK_UP) {
                return true;
            } else if (this.bottomCollision(object) && direction == KeyEvent.VK_DOWN) {
                return true;
            } else if (this.leftCollision(object) && direction == KeyEvent.VK_LEFT) {
                return true;
            } else if (this.rightCollision(object) && direction == KeyEvent.VK_RIGHT) {
                return true;
            }
        }
        return false;
    }

    // draws the strings of object IDs on barricades and keys
    public void drawObjectString(GameObject object, String stringNumber, Graphics g) {
        if (object instanceof Key) {
            g.setColor(Color.BLACK);
            g.setFont(new Font(null, Font.PLAIN, 10));
            g.drawString(stringNumber, object.getX() + 6, object.getY() + 9);
        } else if (object instanceof Barricade) {
            g.setColor(Color.WHITE);
            g.setFont(new Font(null, Font.PLAIN, 10));
            g.drawString(stringNumber, object.getX() + 6, object.getY() + 11);
        }
    }
}
