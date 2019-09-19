/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.eclipse.paho.client.mqttv3.MqttClient;
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
public class SubscriberTest {
    Subscriber subs;
    
    public SubscriberTest() {
        subs = new Subscriber();

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
     * Test of setSubscribe method, of class Subscriber.
     
    @Test
    public void testSetSubscribe() {
        System.out.println("setSubscribe");
        String dato = "";
        Subscriber instance = new Subscriber();
        instance.setSubscribe(dato);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of UnSubscriber method, of class Subscriber.
     */
    @Test
    public void testUnSubscriber() {
        subs.UnSubscriber();
        MqttClient cliente = subs.getCliente();
        System.out.println(cliente.isConnected());
        assertFalse(cliente.isConnected());
    }
 
    /**
     * Test of start method, of class Subscriber.
     */
    @Test
    public void testStart() {
        subs.start();
        MqttClient cliente = subs.getCliente();

        assertTrue(cliente.isConnected());
    }

    /**
     * Test of sendMessage method, of class Subscriber.
     */
    @Test
    public void testSendMessage() {
        System.out.println("sendMessage");
        
        byte[] messagePayload = "prueba".getBytes();
        subs.setTopic("owntracks/sergi/Prueba");
        boolean result = subs.sendMessage(messagePayload);
     
        assertTrue(result);
    }

    /**
     * Test of setTopic method, of class Subscriber.
     */
    @Test
    public void testSetTopic() {
        String topic = " ";
        subs.setTopic(topic);
        assertEquals(subs.getTopic()," ");
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getTopic method, of class Subscriber.
     */
    @Test
    public void testGetTopic() {
        assertEquals("dgt.es/trafic/vehicle/+/info",subs.getTopic());     
    }
    
    /**
     * Test of getTopic method, of class Subscriber.
     */
    @Test
    public void testGetCliente() {
        assertNotNull(subs.getCliente());     
    }
    
}
