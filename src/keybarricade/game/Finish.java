package keybarricade.game;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * The finish class goes to the next level if the player stands on it,
 * if the player is on the last level ( level 3 atm) the game is finsihed.
 * @author René Uhliar, Miladin Jeremic, Len van Kampen
 */
public class Finish extends GameObject {

    /**
     * Sets the x and y coordinates of the finish and 
     * sets the finish to the image "finish.png"
     * @param x
     * @param y
     */
    public Finish(int x, int y) {
        super(x, y);
   
        URL imageUrl = this.getClass().getResource("../imgs/finish.png");
        ImageIcon icon = new ImageIcon(imageUrl);
        Image img = icon.getImage();
        setImage(img);
    }
}
