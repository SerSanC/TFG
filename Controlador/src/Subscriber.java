
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class Subscriber {

    public static final String BROKER_URL = "tcp://tambori.dsic.upv.es:8885";
    private MqttClient client;
    final String username = "sersanc8";
    final String password = "sersanc8";
    private int qos = 0;

    private String topic = "dgt.es/trafic/vehicle/+/info";
    private String clientId;
    /**
     * Metodo constructor donde se inicializa la conexión con el broker
     */
    Subscriber() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        try {
            clientId = UUID.randomUUID().toString();
            client = new MqttClient(BROKER_URL, clientId);
            client.connect(mqttConnectOptions);

        } catch (MqttException ex) {
            Logger.getLogger(Subscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void UnSubscriber() {
        try {
            client.unsubscribe(topic);
            client.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   /**
     * Metodo para realizar la suscripción al topic 
     */
    public void start() {
        try {
            client.setCallback(new SubscribeCallback());
            client.subscribe("dgt.es/trafic/vehicle/+/info");
        } catch (MqttException e) {
            System.exit(1);
        }
    }

    public boolean sendMessage(byte[] messagePayload) {
        boolean res = false;
        if (client != null) {
            try {
                MqttMessage message = new MqttMessage(messagePayload);
                message.setQos(qos);
                message.setRetained(false);
                client.publish(topic, message);
                res = true;
            } catch (MqttException e) {
                System.err.print("Failed to send outbound message to topic:  " + topic + " - unexpected issue: "
                        + new String(messagePayload));
            }
        }
        return res;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    MqttClient getCliente() {
        return client;
    }

}
