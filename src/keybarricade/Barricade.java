package keybarricade;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Barricade extends GameObject {

    private ID id;
    
    public Barricade(int x, int y, ID id) {
        super(x, y);
        setId(id);
        
        URL loc = this.getClass().getResource("barricade.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        setImage(image);
    }
}
