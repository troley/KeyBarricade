package keybarricade;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * The barricade class creates a barricade image on the set position. 
 * The player can't move here unless it's opened with the right key.
 * 
 * @author René Uhliar, Miladin Jeremic, Len van Kampen
 */
public class Barricade extends GameObject {

    /**
     * Checks the x and y coordinates of the Barricade and the 
     * Barricade ID. Sets the barricade to an image "barricade.png"
     * @param x
     * @param y
     * @param id
     */
    public Barricade(int x, int y, ID id) {
        super(x, y, id);
        
        URL imageUrl = this.getClass().getResource("barricade.png");
        ImageIcon icon = new ImageIcon(imageUrl);
        Image img = icon.getImage();
        setImage(img);
    }

}
