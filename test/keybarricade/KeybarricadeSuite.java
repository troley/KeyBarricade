/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keybarricade;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Troley
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({keybarricade.WallTest.class, keybarricade.BarricadeTest.class, keybarricade.IDTest.class, keybarricade.FinishTest.class, keybarricade.GameObjectTest.class, keybarricade.KeyTest.class, keybarricade.MainTest.class, keybarricade.FloorTest.class, keybarricade.GameBoardTest.class, keybarricade.WindowTest.class, keybarricade.PlayerTest.class})
public class KeybarricadeSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
