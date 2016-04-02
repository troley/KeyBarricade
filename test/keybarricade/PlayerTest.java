package keybarricade;

import java.awt.event.KeyEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    public PlayerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testMove() {
        System.out.println("move");
        int x = 35;
        int y = 0;
        Player p = new Player(0, 0);
        p.move(x, y);

        int verwachteUitkomstX = 35;
        int uitkomst = p.getX();

        assertEquals(verwachteUitkomstX, uitkomst);
    }

    @Test
    public void testWall() {
        System.out.println("move");
        int x = 35;
        int y = 0;
        Wall w = new Wall(35, 0);
        Player p = new Player(0, 0);
        if (!p.checkCollision(w, KeyEvent.VK_RIGHT)) {
            p.move(x, y);
        }

        int verwachteUitkomstX = 0;
        int uitkomst = p.getX();

        assertEquals(verwachteUitkomstX, uitkomst);
    }
}
