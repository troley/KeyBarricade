package keybarricade;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * The player Class creates the player on the gameboard and 
 * makes the player be able to move on the board.
 * 
 * @author René Uhliar, Miladin Jeremic, Len van Kampen
 */
public class Player extends GameObject {

    private boolean keyObtained;
    private Key keyInBag;

    /**
     * The player constructor sets the player on the board as 
     * an image "player.png". 
     * And sets the variables keyObtained and KeyInbag to false and null, this
     * means that when the level starts, the player has no key.
     * The x and y params are for the player location.
     * @param x
     * @param y
     */
    public Player(int x, int y) {
        super(x, y);
        keyObtained = false;
        keyInBag = null;

        URL loc = this.getClass().getResource("player.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        setImage(image);
    }

    /**
     * returns if a key is obtained;
     * @return
     */
    public boolean isKeyObtained() {
        return keyObtained;
    }

    /**
     * set keyObtained to true or false.
     * @param hasKey
     */
    public void setKeyObtained(boolean hasKey) {
        this.keyObtained = hasKey;
    }

    /**
     * Looks if a key is taken.
     * @return
     */
    public Key getKeyInBag() {
        return keyInBag;
    }

    /**
     * Takes a key.
     * @param keyInBag
     */
    public void setKeyInBag(Key keyInBag) {
        this.keyInBag = keyInBag;
    }

    /**
     * Iterates through the given arraylist and looks for a Barricade object.
     * Then it gets the current facing direction (Up, down, left, right) and
     * tries to open the barricade (if it meets the assignment conditions).
     *
     * @param objects the arraylist of game objects where a Key object can be
     * found.
     * @param currentFacingDirection the current facing direction of the player.
     */
    public void useKey(ArrayList<GameObject> objects, int currentFacingDirection) {
        if (isKeyObtained()) {
            for (int i = 0; i < objects.size(); i++) {
                GameObject object = objects.get(i);
                if (object instanceof Barricade) {

                    boolean keyFits = getKeyInBag().getId().equals(object.getId());

                    if (this.topCollision(object)
                            && currentFacingDirection == KeyEvent.VK_UP
                            && keyFits) {
                        objects.remove(object);
                    } else if (this.bottomCollision(object)
                            && currentFacingDirection == KeyEvent.VK_DOWN
                            && keyFits) {
                        objects.remove(object);
                    } else if (this.leftCollision(object)
                            && currentFacingDirection == KeyEvent.VK_LEFT
                            && keyFits) {
                        objects.remove(object);
                    } else if (this.rightCollision(object)
                            && currentFacingDirection == KeyEvent.VK_RIGHT
                            && keyFits) {
                        objects.remove(object);
                    }
                }
            }
        }
    }

    /**
     * sets the position, moves the player.
     * @param x
     * @param y
     */
    public void move(int x, int y) {
        this.setX(getX() + x);
        this.setY(getY() + y);
    }
}
