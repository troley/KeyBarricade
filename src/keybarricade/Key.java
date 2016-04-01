package keybarricade;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * The Key Class shows the key as "key.png" on the board.
 * @author René Uhliar, Miladin Jeremic, Len van Kampen
 */
public class Key extends GameObject {

    /**
     * Key has x and y coordinates and and id, if the key id
     * is the same as the barricade id the barricade will be opened.
     * @param x
     * @param y
     * @param id
     */
    public Key(int x, int y, ID id) {
        super(x, y, id);
    
        URL loc = this.getClass().getResource("key.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        setImage(image);
    }
}
