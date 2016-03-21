package keybarricade;

import java.awt.Image;
import java.awt.Point;

public abstract class GameObject {

    private int x;
    private int y;
    private ID id;
    private Image image;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
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
    
    public Point getPoint() {
        return new Point(getX(), getY());
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
}
