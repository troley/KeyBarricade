package keybarricade;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Abstract Class GameObject, every visual part of the game like: 
 * Wall, Key, Floor, Barricade etc. are GameObjects.
 * 
 * 
 * @author René Uhliar, Miladin Jeremić, Len van Kampen
 */
public abstract class GameObject {

    private int x;
    private int y;
    private ID id;
    private Image image;

    /**
     * Sets the GameObjects x and y coordinates.
     * 
     * @param x the x coordinate of the GameObject
     * @param y the y coordinate of the GameObject
     */
    public GameObject(int x, int y) {
        this(x, y, null);
    }

    /**
     * Sets the GameObjects x, y and ID.
     * 
     * @param x the x coordinate of the GameObject
     * @param y the x coordinate of the GameObject
     * @param id the id of the GameObject
     */
    public GameObject(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    /**
     * Sets the x coordinate
     * @param x the x coordinate to be set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the x coordinate
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * sets the Y coordinate
     * @param y the y coordinate to be set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * gets the Y coordinate
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the object of the GameBoard to the fitting image,
     * So for example, a Key object gets a Key Image.
     * @param image the image to be set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Returns the image
     * @return the image 
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
     * @return id
     */
    public ID getId() {
        return id;
    }

    /**
     * Checks for topCollision, if the user presses up arrow 
     * and the parameter object is there, then the player won't move.
     * 
     * @param object the object which should be checked for collision.
     * @return true if the checking object is (top)colliding with the given object parameter.
     */
    public boolean topCollision(GameObject object) {
        return (((getY() - 35) == object.getY()) && (getX() == object.getX()));
    }

    /**
     * Checks for bottomCollision, if the user presses down arrow 
     * and the parameter object is there, then the player won't move.
     * 
     * @param object the object which should be checked for collision.
     * @return true if the checking object is (bottom)colliding with the given object parameter.
     */
    public boolean bottomCollision(GameObject object) {
        return (((getY() + 35) == object.getY()) && (getX() == object.getX()));
    }

    /**
     * Checks for leftCollision, if the user presses left arrow 
     * and the parameter object is there, then the player won't move.
     * 
     * @param object the object which should be checked for collision.
     * @return true if the checking object is (left)colliding with the given object parameter.
     */
    public boolean leftCollision(GameObject object) {
        return (((getX() - 35) == object.getX()) && (getY() == object.getY()));
    }

    /**
     * Checks for rightCollision, if the user presses right arrow 
     * and the parameter object is there, then the player won't move.
     * 
     * @param object the object which should be checked for collision.
     * @return true if the checking object is (right)colliding with the given object parameter.
     */
    public boolean rightCollision(GameObject object) {
        return (((getX() + 35) == object.getX()) && (getY() == object.getY()));
    }

    /**
     * Checks if the checking object's (usually player) X and Y coordinates are the same as the 
     * parameter object's (usually key) coordinates which it is checking for.
     * 
     * @param object the object which should be checked for collision
     * @return true if the checking object is standing on the given object parameter.
     */
    public boolean standsOnObject(GameObject object) {
        return (getX() == object.getX() && getY() == object.getY());
    }

    /**
     * Iterates through the given ArrayList of GameObjects and checks if the given
     * object at the given direction is a barricade or a wall. If so, the
     * object (usually player) which is checking for collision will stand still and won't move
     * to that location, but this check can also be used for further operations (e.g. opening
     * a barricade).
     * 
     * @param objects the ArrayList of GameObjects which may contain wall or barricade.
     * @param direction the direction at which a collision should be checked.
     * @return true if one of the objects from the ArrayList is a Wall or a Barricade
     * and if the Wall or the Barricade are at the given direction.
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

    /**
     * checks if the given GameObject at the given direction is a barricade or a wall. If so, the
     * object (usually player) which is checking for collision will stand still and won't move
     * to that location, but this check can also be used for further operations (e.g. opening
     * a barricade).
     * 
     * @param objects the arraylist of gameobjects which may contain wall or barricade.
     * @param direction the direction at which a collision should be checked.
     * @return true if the object is an instance of Wall or Barricade and if the
     * Wall or the Barricade are at the given direction
     */
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

    /**
     * The drawObjectString draws the strings of object
     * ID's on barricades and keys.
     * For example: if the barricade is of the ID 300, the number 300
     * will be drawn on the barricade.
     * 
     * @param object the object (key or barricade).
     * @param stringNumber the String (number) that should be written on the object.
     * @param g the Graphics object which is responsible for the drawing.
     */
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
