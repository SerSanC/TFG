/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sergisanz
 */
public class MainTest {

    Main instance;

    public MainTest() {
        instance = new Main();
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
     * Test of setDistanciaVehiculo method, of class Main.
     */
    @Test
    public void testSetDistanciaVehiculo() {
        JSONObject localizacionTLC = new JSONObject();
        localizacionTLC.put("lat", 39.481106);
        localizacionTLC.put("lng", -0.33740);
        JSONObject localizacion = localizacionTLC;

        instance.setDistanciaVehiculo(localizacion);
        // TODO review the generated test code and remove the default call to fail.
        assertNotNull(instance.getUbicacionVehiculoMovimiento());
    }

    /**
     * Test of getUbicacionVehiculoMovimiento method, of class Main.
     */
    @Test
    public void testGetUbicacionVehiculoMovimiento() {
        assertNull(instance.getUbicacionVehiculoMovimiento());
    }

    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testMain() {

        instance.main(null);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of Calculo method, of class Main.
     */
    @Test
    public void testCalculo() {
        System.out.println("Calculo");
        
        JSONObject localizacionTLC = new JSONObject();
        localizacionTLC.put("lat", 39.481106);
        localizacionTLC.put("lng", -0.33740);
                JSONObject localizacion = localizacionTLC;

        instance.setDistanciaVehiculo(localizacion);
        instance.Calculo("http://tambori.dsic.upv.es:10050/tlc", localizacionTLC);
        
        assertNotNull(instance);
    }

    /**
     * Test of distanciaCoord method, of class Main.
     */
    @Test
    public void testDistanciaCoord() {
        System.out.println("distanciaCoord");
        double lat1 = 0.0;
        double lng1 = 0.0;
        double lat2 = 0.0;
        double lng2 = 0.0;
        double expResult = 0.0;
        double result = Main.distanciaCoord(lat1, lng1, lat2, lng2);
        assertEquals(instance.distanciaCoord(lat1, lng1, lat2, lng2),expResult, 0);
        // TODO review the generated test code and remove the default call to fail.
    }

}
