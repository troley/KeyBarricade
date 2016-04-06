package keybarricade;

import java.awt.Image;
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
    private int facingDirection;

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
        facingDirection = 0;

        this.setPlayerImage("playerDown.png");
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
     * The getfacingDirection is the direction the player is facing, this
     * is equal to the last (arrow)key that was pressed.
     *
     * Example: If the player of the game pressed the down arrow on the
     * keyboard, and there's a floor or key on the space under the player, the
     * player moves there and the current facing direction is downwards, then if
     * the player has a key and wants to open a barricade on the right of the
     * player, the player first needs to press the right arrow key on the
     * keyboard, else the barricade won't open even if the player has the right
     * key for the barricade.
     *
     * @return facingDirection - the current facing direction of the player.
     */
    public int getFacingDirection() {
        return facingDirection;
    }
    
    /**
     * Sets the facing direction of the player.
     * @param facingDirection the direction which the player is facing.
     */
    public void setFacingDirection(int facingDirection) {
        this.facingDirection = facingDirection;
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
    
    public void setPlayerImage(String imgFile) {
        URL imageUrl = this.getClass().getResource(imgFile);
        ImageIcon icon = new ImageIcon(imageUrl);
        Image img = icon.getImage();
        setImage(img);
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
