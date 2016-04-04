package keybarricade;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * The player class creates the player on the GameBoard with the corresponding
 * image and makes the player be able to move on the board.
 *
 * @author René Uhliar, Miladin Jeremić, Len van Kampen
 */
public class Player extends GameObject {

    private boolean keyObtained;
    private Key keyInBag;

    /**
     * Constructs a new player at the X and Y position with no key on him.
     *
     * @param x the x position
     * @param y the y position
     */
    public Player(int x, int y) {
        super(x, y);
        keyObtained = false;
        keyInBag = null;

        URL imageUrl = this.getClass().getResource("player.png");
        ImageIcon icon = new ImageIcon(imageUrl);
        Image img = icon.getImage();
        setImage(img);
    }

    /**
     * Checks if a key has been obtained
     *
     * @return true if keyObtained is true
     */
    public boolean isKeyObtained() {
        return keyObtained;
    }

    /**
     * Set the keyObtained boolean
     *
     * @param hasKey set to true if a key has been obtained
     */
    public void setKeyObtained(boolean hasKey) {
        this.keyObtained = hasKey;
    }

    /**
     * Gets the current key which is in the bag
     *
     * @return the Key which is at that moment in the bag
     */
    public Key getKeyInBag() {
        return keyInBag;
    }

    /**
     * Sets a Key in bag.
     *
     * @param keyInBag the Key which should be set into the bag
     */
    public void setKeyInBag(Key keyInBag) {
        this.keyInBag = keyInBag;
    }

    /**
     * Iterates through the given ArrayList parameter and looks for a Barricade
     * object. Then it gets the current facing direction (Up, down, left, right)
     * and opens the barricade at the facing direction if the key id equals the
     * barricade id.
     *
     * @param objects the ArrayList of game objects.
     * @param currentFacingDirection the current facing direction of the player.
     */
    public void useKey(ArrayList<GameObject> objects, int currentFacingDirection) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject object = objects.get(i);
            
            if (object instanceof Barricade) {
                
                boolean keyFits = getKeyInBag().getId().equals(object.getId());

                if(this.checkCollision(object, currentFacingDirection) // if key doesn't fit the barricade
                        && !keyFits) {
                    JOptionPane.showMessageDialog(null, "Key doesn't fit.", "Oops!", JOptionPane.WARNING_MESSAGE);
                } else if (this.checkCollision(object, currentFacingDirection) // if key fits the barricade
                        && keyFits) {
                    objects.remove(object);
                }
            }
        }
    }
    
    /**
     * Moves the player to the given X and Y coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void move(int x, int y) {
        this.setX(getX() + x);
        this.setY(getY() + y);
    }
}
