package keybarricade;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class Key extends GameObject {

    public Key(int x, int y, ID id) {
        super(x, y, id);
    
        URL loc = this.getClass().getResource("key.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        setImage(image);
    }
}