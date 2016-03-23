package keybarricade;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class Finish extends GameObject {

    public Finish(int x, int y) {
        super(x, y);
        
        URL loc = this.getClass().getResource("finish.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        setImage(image);
    }
}
