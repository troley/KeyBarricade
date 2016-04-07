package keybarricade;

import java.awt.event.KeyEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * In this Test we test the move method from Player class and how it 
 * reacts while trying to move onto objects. For example moving onto a barricade
 * or a wall should not be allowed, but moving onto a key tile or the finish tile
 * should be allowed.
 * 
 * @author René Uhliar, Miladin Jeremić, Len van Kampen
 */
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
    public void testMove1() {
        System.out.println("move against a wall");
        int x = 35;
        int y = 0;
        Wall w = new Wall(35, 0);
        Player p = new Player(0, 0);
        if (!p.checkCollision(w, KeyEvent.VK_RIGHT)) { // should block the player from moving because of wall collision
            p.move(x, y);
        }

        int verwachteUitkomstX = 0; // 0 because player should not be allowed to move onto a wall
        int uitkomst = p.getX();

        assertEquals(verwachteUitkomstX, uitkomst);
    }
    
    @Test
    public void testMove2() {
        System.out.println("move against a barricade");
        int x = 35;
        int y = 0;
        Barricade b = new Barricade(35, 0, ID.Object100);
        Player p = new Player(0, 0);
        if (!p.checkCollision(b, KeyEvent.VK_RIGHT)) { // should block the player from moving because of barricade collision
            p.move(x, y);
        }

        int verwachteUitkomstX = 0; // 0 because player should not be allowed to move onto a barricade
        int uitkomst = p.getX();

        assertEquals(verwachteUitkomstX, uitkomst);
    }
    
    @Test
    public void testMove3() {
        System.out.println("move onto a floor tile");
        int x = 35;
        int y = 0;
        Floor f = new Floor(35, 0);
        Player p = new Player(0, 0);
        if (!p.checkCollision(f, KeyEvent.VK_RIGHT)) { // should block nothing, player should move onto a floor tile
            p.move(x, y);
        }

        int verwachteUitkomstX = 35;
        int uitkomst = p.getX();

        assertEquals(verwachteUitkomstX, uitkomst);
    }
    
    @Test
    public void testMove4() {
        System.out.println("move onto a key tile");
        int x = 35;
        int y = 0;
        Key k = new Key(35, 0, ID.Object100);
        Player p = new Player(0, 0);
        if (!p.checkCollision(k, KeyEvent.VK_RIGHT)) { // should block nothing, player should move onto a key tile
            p.move(x, y);
        }

        int verwachteUitkomstX = 35;
        int uitkomst = p.getX();

        assertEquals(verwachteUitkomstX, uitkomst);
    }
    
    @Test
    public void testMove5() {
        System.out.println("move onto a finish tile");
        int x = 35;
        int y = 0;
        Finish f = new Finish(35, 0);
        Player p = new Player(0, 0);
        if (!p.checkCollision(f, KeyEvent.VK_RIGHT)) { // should block nothing, player should move onto a finish tile
            p.move(x, y);
        }

        int verwachteUitkomstX = 35;
        int uitkomst = p.getX();

        assertEquals(verwachteUitkomstX, uitkomst);
    }
    
    /*
     * From testMove6 on, these are the same tests, but for the Y position.
     */
    
    @Test
    public void testMove6() {
        System.out.println("move");
        int x = 0;
        int y = 35;
        Player p = new Player(0, 0);
        p.move(x, y);

        int verwachteUitkomstY = 35;
        int uitkomst = p.getY();

        assertEquals(verwachteUitkomstY, uitkomst);
    }
    
    
    @Test
    public void testMove7() {
        System.out.println("move against a wall");
        int x = 0;
        int y = 35;
        Wall w = new Wall(0, 35);
        Player p = new Player(0, 0);
        if (!p.checkCollision(w, KeyEvent.VK_DOWN)) { // should block the player from moving because of wall collision
            p.move(x, y);
        }

        int verwachteUitkomstY = 0; // 0 because player should not be allowed to move onto a wall
        int uitkomst = p.getY();

        assertEquals(verwachteUitkomstY, uitkomst);
    }
    
    @Test
    public void testMove8() {
        System.out.println("move against a barricade");
        int x = 0;
        int y = 35;
        Barricade b = new Barricade(0, 35, ID.Object100);
        Player p = new Player(0, 0);
        if (!p.checkCollision(b, KeyEvent.VK_DOWN)) { // should block the player from moving because of barricade collision
            p.move(x, y);
        }

        int verwachteUitkomstY = 0; // 0 because player should not be allowed to move onto a barricade
        int uitkomst = p.getY();

        assertEquals(verwachteUitkomstY, uitkomst);
    }
    
    @Test
    public void testMove9() {
        System.out.println("move onto a floor tile");
        int x = 0;
        int y = 35;
        Floor f = new Floor(0, 35);
        Player p = new Player(0, 0);
        if (!p.checkCollision(f, KeyEvent.VK_DOWN)) { // should block nothing, player should move onto a floor tile
            p.move(x, y);
        }

        int verwachteUitkomstY = 35;
        int uitkomst = p.getY();

        assertEquals(verwachteUitkomstY, uitkomst);
    }
    
    @Test
    public void testMove10() {
        System.out.println("move onto a key tile");
        int x = 0;
        int y = 35;
        Key k = new Key(0, 35, ID.Object100);
        Player p = new Player(0, 0);
        if (!p.checkCollision(k, KeyEvent.VK_DOWN)) { // should block nothing, player should move onto a key tile
            p.move(x, y);
        }

        int verwachteUitkomstY = 35;
        int uitkomst = p.getY();

        assertEquals(verwachteUitkomstY, uitkomst);
    }
    
    @Test
    public void testMove11() {
        System.out.println("move onto a finish tile");
        int x = 0;
        int y = 35;
        Finish f = new Finish(0, 35);
        Player p = new Player(0, 0);
        if (!p.checkCollision(f, KeyEvent.VK_DOWN)) { // should block nothing, player should move onto a finish tile
            p.move(x, y);
        }

        int verwachteUitkomstY = 35;
        int uitkomst = p.getY();

        assertEquals(verwachteUitkomstY, uitkomst);
    }
}
