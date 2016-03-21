package keybarricade;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class Player extends GameObject {

    public Player(int x, int y) {
        super(x, y);

        URL loc = this.getClass().getResource("player.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        setImage(image);
    }

    public void move(int x, int y) {
        this.setX(getX() + x);
        this.setY(getY() + y);
    }
}
