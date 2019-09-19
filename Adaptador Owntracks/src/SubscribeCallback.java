
import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class SubscribeCallback implements MqttCallback {

    private Subscriber cliente;

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Conexión interrumpida: " + cause.getMessage());
    }

    /**
     * Es llamada cuando se recibe un mensaje, al estar suscrito a un topic del
     * broker MQTT
     *
     * @param topic
     * @param message
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        cliente = new Subscriber();
        System.out.println("Mensaje recibido: " + topic + ":" + message.toString());
        JSONObject json = new JSONObject();
        JSONObject mensaje = new JSONObject(message.toString());
        JSONObject localizacion = new JSONObject();

        if (mensaje.get("tid").equals("SE")) {
            Object lat = mensaje.get("lat");
            Object lng = mensaje.get("lon");

            localizacion.put("lat", lat);
            localizacion.put("lng", lng);

            json.put("id", topic);
            json.put("location", localizacion);

            cliente.sendMessage(json.toString().getBytes());
        }
    }

    /**
     * Es llamada cuando se ha terminado de subir un mensaje al topic enviado
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            System.out.println("Enviado: " + token.getMessage());
        } catch (MqttException ex) {
            Logger.getLogger(SubscribeCallback.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
