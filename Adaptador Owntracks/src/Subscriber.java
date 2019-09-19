
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
    private String topic = "dgt.es/trafic/vehicle/ambulancia2/info";
    private String subscribe = "owntracks/#";
    String clientId;

    public void UnSubscriber() {
        try {
            client.unsubscribe(topic);
            client.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Constructor para inicializar la conexión al broker MQTT 
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

    /**
     * Metodo donde se inicia la conexión. Primero se conecta al BROKER_URL y
     * posteriormente se realiza la conexión con la configuración establecida.
     * Para finalmente subscribirse al topic deseado, en este caso owntracks/#
     */
    public void start() {

        try {

            client.setCallback(new SubscribeCallback());
            client.subscribe(subscribe);

        } catch (MqttException e) {
            System.exit(1);
        }

    }
    /**
     * Metodo para el envio de mensajes
     * @param messagePayload
     * @return boolean
     */
    public boolean sendMessage(byte[] messagePayload) {
        //System.out.println("llego a sendMessage");
        boolean res = false;
        if (client != null) {
            try {
                MqttMessage message = new MqttMessage(messagePayload);
                message.setQos(qos);
                message.setRetained(false);
                client.publish(topic, message);
                res = true;
            } catch (MqttException e) {
                System.err.print("Failed to send outbound message to topic:  "+ 
                    topic + "- unexpected issue: "+ new String(e.toString()));
            }
        } else {
            System.out.println("estoy vacio");
        }
        return res;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
    public MqttClient getCliente(){return client;}
    public void setSubscribe(String dato) {
        subscribe = dato;
    }

}
