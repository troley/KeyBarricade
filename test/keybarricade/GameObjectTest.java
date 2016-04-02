/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keybarricade;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
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
public class GameObjectTest {
    
    public GameObjectTest() {
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
     * Test of setX method, of class GameObject.
     */
    @Test
    public void testSetX() {
        System.out.println("setX");
        int x = 0;
        GameObject instance = null;
        instance.setX(x);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getX method, of class GameObject.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        GameObject instance = null;
        int expResult = 0;
        int result = instance.getX();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setY method, of class GameObject.
     */
    @Test
    public void testSetY() {
        System.out.println("setY");
        int y = 0;
        GameObject instance = null;
        instance.setY(y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getY method, of class GameObject.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        GameObject instance = null;
        int expResult = 0;
        int result = instance.getY();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setImage method, of class GameObject.
     */
    @Test
    public void testSetImage() {
        System.out.println("setImage");
        Image image = null;
        GameObject instance = null;
        instance.setImage(image);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getImage method, of class GameObject.
     */
    @Test
    public void testGetImage() {
        System.out.println("getImage");
        GameObject instance = null;
        Image expResult = null;
        Image result = instance.getImage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class GameObject.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        ID id = null;
        GameObject instance = null;
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class GameObject.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        GameObject instance = null;
        ID expResult = null;
        ID result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of topCollision method, of class GameObject.
     */
    @Test
    public void testTopCollision() {
        System.out.println("topCollision");
        GameObject object = null;
        GameObject instance = null;
        boolean expResult = false;
        boolean result = instance.topCollision(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of bottomCollision method, of class GameObject.
     */
    @Test
    public void testBottomCollision() {
        System.out.println("bottomCollision");
        GameObject object = null;
        GameObject instance = null;
        boolean expResult = false;
        boolean result = instance.bottomCollision(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of leftCollision method, of class GameObject.
     */
    @Test
    public void testLeftCollision() {
        System.out.println("leftCollision");
        GameObject object = null;
        GameObject instance = null;
        boolean expResult = false;
        boolean result = instance.leftCollision(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rightCollision method, of class GameObject.
     */
    @Test
    public void testRightCollision() {
        System.out.println("rightCollision");
        GameObject object = null;
        GameObject instance = null;
        boolean expResult = false;
        boolean result = instance.rightCollision(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of standsOnObject method, of class GameObject.
     */
    @Test
    public void testStandsOnObject() {
        System.out.println("standsOnObject");
        GameObject object = null;
        GameObject instance = null;
        boolean expResult = false;
        boolean result = instance.standsOnObject(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkCollision method, of class GameObject.
     */
    @Test
    public void testCheckCollision() {
        System.out.println("checkCollision");
        ArrayList<GameObject> objects = null;
        int direction = 0;
        GameObject instance = null;
        boolean expResult = false;
        boolean result = instance.checkCollision(objects, direction);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawObjectString method, of class GameObject.
     */
    @Test
    public void testDrawObjectString() {
        System.out.println("drawObjectString");
        GameObject object = null;
        String stringNumber = "";
        Graphics g = null;
        GameObject instance = null;
        instance.drawObjectString(object, stringNumber, g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class GameObjectImpl extends GameObject {

        public GameObjectImpl() {
            super(0, 0);
        }
    }
    
}
