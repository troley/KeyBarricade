package keybarricade;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * The Key Class shows the Key with the given image on the board.
 * 
 * @author René Uhliar, Miladin Jeremić, Len van Kampen
 */
public class Key extends GameObject {

    /**
     * Key has x and y coordinates and and id, if the key id
     * is the same as the barricade id the barricade will be opened.
     * @param x the x coordinate of the Key
     * @param y the y coordinate of the Key
     * @param id the id of the Key
     */
    public Key(int x, int y, ID id) {
        super(x, y, id);
        
        URL imageUrl = this.getClass().getResource("key.png");
        ImageIcon icon = new ImageIcon(imageUrl);
        Image img = icon.getImage();
        setImage(img);
    }

}
