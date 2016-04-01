package keybarricade;

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
        
        URL loc = this.getClass().getResource("wall.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        setImage(image);
    }
}
