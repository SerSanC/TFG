
import java.util.ArrayList;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
//import org.json.JSONObject;

public class SubscribeCallback implements MqttCallback {

    private ArrayList<String> listaId = new ArrayList<String>();
    private String lastMessage = null;

    private String messageComparador;

    @Override
    public void connectionLost(Throwable cause) {
    }

    /**
     * Es llamada cuando se recibe un mensaje, al estar suscrit a un topic del broker MQTT
     * @param topic
     * @param message
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        messageComparador = topic.substring(0, 14);
        if (messageComparador.equals("dgt.es/trafic/")) {
            System.out.println(topic + ": " + message.toString());
            JSONObject mensaje = new JSONObject(message.toString());
            JSONObject localizacion = new JSONObject(mensaje.get("location").toString());

            System.out.println(mensaje);
            System.out.println(localizacion);

            Main m = new Main();
            m.setDistanciaVehiculo(localizacion);
        }
    }

    /*Es llamada cuando se ha terminado de subir un mensaje al topic enviado*/
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            System.out.println("Enviado: " + token.getMessage());
        } catch (MqttException ex) {
            Logger.getLogger(SubscribeCallback.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String getMessage(){return lastMessage;}

}
