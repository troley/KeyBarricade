/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keybarricade;

import java.awt.Graphics;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Troley
 */
public class GameBoardTest {
    
    public GameBoardTest() {
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

    /**
     * Test of getLevelWidth method, of class GameBoard.
     */
    @Test
    public void testGetLevelWidth() {
        System.out.println("getLevelWidth");
        GameBoard instance = new GameBoard();
        int expResult = 0;
        int result = instance.getLevelWidth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLevelHeight method, of class GameBoard.
     */
    @Test
    public void testGetLevelHeight() {
        System.out.println("getLevelHeight");
        GameBoard instance = new GameBoard();
        int expResult = 0;
        int result = instance.getLevelHeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLevelNumber method, of class GameBoard.
     */
    @Test
    public void testSetLevelNumber() {
        System.out.println("setLevelNumber");
        int levelNumber = 0;
        GameBoard instance = new GameBoard();
        instance.setLevelNumber(levelNumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLevelNumber method, of class GameBoard.
     */
    @Test
    public void testGetLevelNumber() {
        System.out.println("getLevelNumber");
        GameBoard instance = new GameBoard();
        int expResult = 0;
        int result = instance.getLevelNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentFacingDirection method, of class GameBoard.
     */
    @Test
    public void testGetCurrentFacingDirection() {
        System.out.println("getCurrentFacingDirection");
        GameBoard instance = new GameBoard();
        int expResult = 0;
        int result = instance.getCurrentFacingDirection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initWorld method, of class GameBoard.
     */
    @Test
    public void testInitWorld() {
        System.out.println("initWorld");
        GameBoard instance = new GameBoard();
        instance.initWorld();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of paint method, of class GameBoard.
     */
    @Test
    public void testPaint() {
        System.out.println("paint");
        Graphics g = null;
        GameBoard instance = new GameBoard();
        instance.paint(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
