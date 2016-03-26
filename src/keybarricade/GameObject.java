package keybarricade;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class GameObject {

    private int x;
    private int y;
    private ID id;
    private Image image;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public GameObject(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public boolean topCollision(GameObject object) {
        return (((getY() - 35) == object.getY()) && (getX() == object.getX()));
    }

    public boolean bottomCollision(GameObject object) {
        return (((getY() + 35) == object.getY()) && (getX() == object.getX()));
    }

    public boolean leftCollision(GameObject object) {
        return (((getX() - 35) == object.getX()) && (getY() == object.getY()));
    }

    public boolean rightCollision(GameObject object) {
        return (((getX() + 35) == object.getX()) && (getY() == object.getY()));
    }

    public boolean standsOnObject(GameObject object) {
        return (getX() == object.getX() && getY() == object.getY());
    }
    
    /**
     * Iterates through the given arraylist of gameobjects and checks if the 
     * objects at the given direction are a barricade or a wall. 
     * If so, the objects which is checking for collision
     * will stand still and won't move to that location.
     * @param objects the arraylist of gameobjects which may contain wall or barricade.
     * @param direction the direction at which a collision should be checked.
     * @return 
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
