import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author sergisanz
 */
public class SubscribeCallbackTest {
    SubscribeCallback sub;
    public SubscribeCallbackTest() {
        sub = new SubscribeCallback();
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
     * Test of messageArrived method, of class SubscribeCallback.
     */
    @Test
    public void testMessageArrived() {        
        sub.messageArrived("owntracks/sersanc8/iPhoneSergi:", 
                new MqttMessage("{\"cog\":194,\"batt\":87,\"lon\":-0.39106511787385939,\"acc\":32,\"p\":101.22594451904297,\"vel\":0,\"vac\":3,\"lat\":39.20098876953125,\"t\":\"t\",\"conn\":\"w\",\"tst\":1559898844,\"alt\":14,\"_type\":\"location\",\"tid\":\"SE\"}".getBytes()));
        assertNotNull(sub);  
    }

    /**
     * Test of deliveryComplete method, of class SubscribeCallback.
      */
    @Test
    public void testDeliveryComplete() throws MqttException {
        System.out.println("deliveryComplete");
        Subscriber subscribe = new Subscriber();
        MqttClient cliente = subscribe.getCliente();
                IMqttDeliveryToken[] token;
        token = cliente.getPendingDeliveryTokens();

       // boolean com = token.getSessionPresent();
       assertNotNull(token);
    }
       /**
     * Test of connectionLost method, of class SubscribeCallback.
     */
    @Test
    public void testConnectionLost() {
        System.out.println("connectionLost");
        Throwable cause = new Throwable();
        sub.connectionLost(cause);
        assertNotNull(sub);
    }
}
