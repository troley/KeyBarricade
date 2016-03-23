package keybarricade;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class Wall extends GameObject {

    public Wall(int x, int y) {
        super(x, y);
        
        URL loc = this.getClass().getResource("wall.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        setImage(image);
    }
}
