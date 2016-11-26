package keybarricade.game;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * The wall class, extends GameObject. Here the wall gets made 
 *
 * @author René Uhliar, Miladin Jeremic, Len van Kampen
 */
public class Wall extends GameObject {

    /**
     * Wall constructor takes the coordinates x and y and puts a wall there with the image: Wall.png
     * 
     * @param x
     * @param y
     */
    public Wall(int x, int y) {
        super(x, y);

        URL imageUrl = this.getClass().getResource("../imgs/wall.png");
        ImageIcon icon = new ImageIcon(imageUrl);
        Image img = icon.getImage();
        setImage(img);
    }
}
